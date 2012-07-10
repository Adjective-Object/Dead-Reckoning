package core.statuses;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.status.AnimatedStatus;
import net.plaidypus.deadreckoning.status.Status;

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
	static String details;

	/** The sprite. */
	static SpriteSheet sprite;

	public OnFire(){
		super(null,null,null,null,null);
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
	 * net.plaidypus.deadreckoning.status.Status#advanceTurnEffects(net.plaidypus
	 * .deadreckoning.entities.LivingEntity)
	 */
	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target) {
		ArrayList<Action> actions = new ArrayList<Action>(0);
		actions.add(new AttackAction(sourceID, target.getLocation(), power
				* this.stacks, false, false, 0, null, null, null, null));
		duration--;
		System.out.println(actions);
		return actions;
	}
	
	@Override
	public void onActionProduce(Action a) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onActionReceive(Action a) {
		// TODO Auto-generated method stub
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
