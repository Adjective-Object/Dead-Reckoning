package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.professions.StatMaster;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Statuses to be applied to entities.
 * 
 * (abstract) used for persistent effects on entities, like buffs, debuffs, DOT,
 * etc.
 */
public abstract class Status {

	/** The image tile used by the Status, displayed by . */
	public Image tileImage;

	/** The name and description of the Status. */
	public String description, identifier;

	/** The stacks=# stacks of a given buff. */
	int statusID, duration, stacks;

	/** The source. */
	public InteractiveEntity source;

	/** The Constant STATUS_ONFIRE - identifier # of onFire */
	static final int STATUS_ONFIRE = 1;

	/**
	 * Instantiates a new status object.
	 * 
	 * @param source
	 *            the source
	 * @param tileImage
	 *            the tile image
	 * @param description
	 *            the description
	 * @param identifier
	 *            the identifier
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
	 * Collapses with a status that has the same identifier string.
	 * 
	 * @param s
	 *            the s
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
	 * Applies the effects of the status to an entity. It's called once per turn
	 * advance (from one entity to the next)
	 * 
	 * @param target
	 *            the target
	 */
	public abstract void applyToEntity(LivingEntity target);

	/**
	 * Updates the status.
	 * 
	 * Called once per frame iteration, used mostly for animation handling.
	 * 
	 * @param target
	 *            the target
	 * @param delta
	 *            the delta
	 */
	public abstract void update(LivingEntity target, int delta);

	/**
	 * Applies the effects of the status to an entity. It's called once per turn
	 * advance (once per full round)
	 * 
	 * @param target
	 *            the target
	 * @return the array list
	 */
	public abstract ArrayList<Action> advanceTurnEffects(LivingEntity target);

	/**
	 * Removes the from entity.
	 * 
	 * @param target
	 *            the target
	 */
	public abstract void removeFromEntity(LivingEntity target);

	/**
	 * Render.
	 * 
	 * @param g
	 *            the Graphics upon which to render
	 * @param x
	 *            the screen X at which to render
	 * @param y
	 *            the screen y at which to render
	 */
	public abstract void render(Graphics g, int x, int y);

	/**
	 * Checks if is finished (usually if the duration is carried out to
	 * completion).
	 * 
	 * @return true, if is finished
	 */
	public abstract boolean isFinished();

	/**
	 * Gets the name of the status.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	public abstract void alterStatMaster(StatMaster statMaster);

}
