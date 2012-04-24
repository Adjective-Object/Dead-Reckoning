package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

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

	/**
	 * Instantiates a new on fire status.
	 *
	 * @param source the source
	 * @param duration the duration
	 * @param power the power
	 */
	public OnFire(InteractiveEntity source, int duration, int power) {
		super(source, image, details, "OnFire", new Animation(sprite, 60));
		this.duration = duration;
		this.power = power;
		this.statusID = Status.STATUS_ONFIRE;
	}

	/**
	 * initializes the status.
	 *
	 * @throws SlickException the slick exception
	 */
	public static void init() throws SlickException {
		image = new Image("res/onFireIcon.png");
		details = "OH SHIT, YOU'RE ON FIRE";
		sprite = new SpriteSheet("res/fireAnimation.png",
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#applyToEntity(net.plaidypus.deadreckoning.entities.LivingEntity)
	 */
	@Override
	public void applyToEntity(LivingEntity target) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#advanceTurnEffects(net.plaidypus.deadreckoning.entities.LivingEntity)
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

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#removeFromEntity(net.plaidypus.deadreckoning.entities.LivingEntity)
	 */
	@Override
	public void removeFromEntity(LivingEntity target) {

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return duration <= 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.status.Status#getName()
	 */
	@Override
	public String getName() {
		return "On Fire!";
	}

}
