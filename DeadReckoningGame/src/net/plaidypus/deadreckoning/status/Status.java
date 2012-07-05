package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.professions.StatMaster;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Statuses to be applied to entities.
 * 
 * (abstract) used for persistent effects on entities, like buffs, debuffs, DOT,
 * etc.
 */
public abstract class Status extends DeadReckoningComponent{

	/** The image tile used by the Status, displayed by . */
	public Image tileImage;

	/** The name and description of the Status. */
	public String description, identifier;

	/** The stacks=# stacks of a given buff. */
	protected Integer sourceID;
	protected int duration, stacks;
	
	public Status(Integer sourceID, Image tileImage,
			String description, String identifier) {
		this.description = description;
		this.identifier = identifier;
		this.tileImage = tileImage;
		this.sourceID = sourceID;
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
	
	public abstract String saveToString();
	
	public abstract Status loadFromString(String[] args);
	
	public String getGenericSave(){
		return	this.getClass().getCanonicalName()+"-"
				+this.sourceID+"-"
				+this.stacks+"-"
				+this.duration;
	}
	
	public static Status loadStatusFromString(String stringstatus){
		String[] split = stringstatus.split("-");
		try {
			Class<? extends Status> c  = ModLoader.loadClass(split[0]);
			return c.newInstance().loadFromString(split);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
