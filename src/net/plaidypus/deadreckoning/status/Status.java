package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class Status.
 */
public abstract class Status {

	/** The tile image. */
	public Image tileImage;
	
	/** The identifier. */
	public String description, identifier;
	
	/** The stacks. */
	int statusID, duration, stacks;
	
	/** The source. */
	public InteractiveEntity source;

	/** The Constant STATUS_ONFIRE. */
	static final int STATUS_ONFIRE = 1;

	/**
	 * Instantiates a new status.
	 *
	 * @param source the source
	 * @param tileImage the tile image
	 * @param description the description
	 * @param identifier the identifier
	 */
	public Status(InteractiveEntity source, Image tileImage,
			String description, String identifier) {
		this.description = description;
		this.identifier = identifier;
		this.tileImage = tileImage;
		this.source = source;
		this.stacks = 1;
	}

	// Only called if they have the same identifier string
	/**
	 * Collapse with status.
	 *
	 * @param s the s
	 * @return the status
	 */
	public Status collapseWithStatus(Status s) {
		Status p = this;
		if (s.duration > this.duration) {
			p = s;
		}
		p.stacks = this.stacks + s.stacks;
		return p;
	}

	/**
	 * Apply to entity.
	 *
	 * @param target the target
	 */
	public abstract void applyToEntity(LivingEntity target);

	/**
	 * Update.
	 *
	 * @param target the target
	 * @param delta the delta
	 */
	public abstract void update(LivingEntity target, int delta);

	/**
	 * Advance turn effects.
	 *
	 * @param target the target
	 * @return the array list
	 */
	public abstract ArrayList<Action> advanceTurnEffects(LivingEntity target);

	/**
	 * Removes the from entity.
	 *
	 * @param target the target
	 */
	public abstract void removeFromEntity(LivingEntity target);

	/**
	 * Render.
	 *
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 */
	public abstract void render(Graphics g, int x, int y);

	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	public abstract boolean isFinished();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public abstract String getName();
}
