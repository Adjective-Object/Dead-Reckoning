package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.professions.StatMaster;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class OnFire.
 */
public class OnFire extends AnimatedStatus {

	/** The power. */
	int duration, power;

	/** The image. */
	static Image image;

	/** The details. */
	static String details, statusID;

	/** The sprite. */
	static SpriteSheet sprite;

	public OnFire(){
		super(null,null,null,null,null);
	}
	
	/**
	 * Instantiates a new on fire status.
	 * 
	 * @param source
	 *            the source
	 * @param duration
	 *            the duration
	 * @param power
	 *            the power
	 */
	public OnFire(InteractiveEntity source, int duration, int power) {
		super(source, image, details, "OnFire", new Animation(sprite, 60));
		this.duration = duration;
		this.power = power;
		this.statusID = "core/status/OnFire";
	}
	
	public OnFire(int sourceID, int duration, int power) {
		super(sourceID, image, details, "OnFire", new Animation(sprite, 60));
		this.duration = duration;
		this.power = power;
	}

	/**
	 * initializes the status.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public void init() throws SlickException {
		image = ModLoader.loadImage("core/res/onFireIcon.png");
		details = "OH SHIT, YOU'RE ON FIRE";
		sprite = new SpriteSheet( ModLoader.loadImage("core/res/fireAnimation.png"),
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.status.Status#applyToEntity(net.plaidypus
	 * .deadreckoning.entities.LivingEntity)
	 */
	@Override
	public void applyToEntity(LivingEntity target) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.status.Status#advanceTurnEffects(net.plaidypus
	 * .deadreckoning.entities.LivingEntity)
	 */
	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target) {
		ArrayList<Action> actions = new ArrayList<Action>(0);
		actions.add(new AttackAction(source, target.getLocation(), power
				* this.stacks, false, false, null, null, null, null));
		duration--;
		System.out.println(actions);
		return actions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.status.Status#removeFromEntity(net.plaidypus
	 * .deadreckoning.entities.LivingEntity)
	 */
	@Override
	public void removeFromEntity(LivingEntity target) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.status.Status#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return duration <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.status.Status#getName()
	 */
	@Override
	public String getName() {
		return "On Fire!";
	}

	@Override
	public void alterStatMaster(StatMaster statMaster) {
		statMaster.editDEX(-1);
	}

	@Override
	public String saveToString() {
		return super.getGenericSave()+"-"+this.power;
	}
	
	public Status loadFromString(String[] args){
		return new OnFire(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
	}

}
