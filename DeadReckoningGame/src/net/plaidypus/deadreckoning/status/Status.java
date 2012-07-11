package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Entity;

import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;

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
	public String description, identifier, name;

	/** The stacks=# stacks of a given buff. */
	protected Integer sourceID;
	private int duration, maxDuration;

	protected int stacks;
	
	public Status(){}
	
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
		if (s.getDuration() > this.getDuration()) {
			p = s;
		}
		p.stacks = this.stacks + s.stacks;
		return p;
	}

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
	public ArrayList<Action> advanceTurnEffects(LivingEntity target){
		this.setDuration(this.getDuration() - 1);
		return new ArrayList<Action>(0);
	}
	
	public abstract void onActionProduce(Action a);
	
	public abstract void onActionReceive(Action a);

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
	public boolean isFinished() {
		return this.getDuration()<=0;
	}
	/**
	 * Gets the name of the status.
	 * 
	 * @return the name
	 */
	public String getName(){
		return this.name;
	}

	public int getDuration() {
		return duration;
	}

	public abstract void alterStatMaster(StatMaster statMaster);
	
	public abstract String saveToString();
	
	public abstract Status loadFromString(String[] args);
	
	/**
	 * class name:sourceID:stacks:duration;
	 * @return
	 */
	public String getGenericSave(){
		return	this.getClass().getCanonicalName()+"-"
				+this.sourceID+"-"
				+this.stacks+"-"
				+this.getDuration();
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
	
	public Entity getSource(){
		return GameBoard.getEntity(sourceID);
	}

	public void setDuration(int duration) {
		this.duration = duration;
		if(duration>this.getMaxDuration()){
			this.setMaxDuration(duration);
		}
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}
	
}
