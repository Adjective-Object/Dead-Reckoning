package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public abstract class Entity extends DeadReckoningComponent {

	/** The location. */
	private Tile location;

	/** The layer. */
	private int layer;

	/** The name. */
	private String name = "NAMELESS ENTITY";

	/** The description. */
	protected String description = "Too lazy to describe";

	/** The facing. */
	private boolean facing;

	/** The visible. */
	private boolean transparent, visible;

	protected boolean stealthed;

	/** The is interactive. */
	protected boolean isTerrain, makesActions;

	/** The to kill. */
	public boolean toKill;

	/** The allignmnet. */
	public int allignmnet;
	
	public int entityID=-1;

	/** The Constant ALLIGN_FRIENDLY. */
	public static final int ALLIGN_NEUTRAL = 0, ALLIGN_HOSTILE = 1,
			ALLIGN_FRIENDLY = 2;

	/**
	 * Instantiates a new entity.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 */
	public Entity() {
		this.visible = true;
		this.transparent = true;
		this.allignmnet = 0;
	}

	/**
	 * updates the entity. Used primarily for animation.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 */
	public abstract void update(GameContainer gc, int delta);

	/**
	 * chooses the next action. returns either the action or null for undecided.
	 * if undecided, the GameState should continue calling chooseAction until it
	 * returns an action
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the elapsed milliseconds since the last call of update
	 */

	public abstract void updateBoardEffects(GameContainer gc, int delta);

	/**
	 * Choose action.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @return the action
	 */
	public abstract Action chooseAction(GameContainer gc, int delta);

	/**
	 * renders to Graphics only if this.visible calls this.forceRender()
	 * 
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void render(Graphics g, float x, float y) {
		if (this.visible) {
			if( this.stealthed && (this.allignmnet==Entity.ALLIGN_FRIENDLY||DeadReckoningGame.debugMode) ){
				g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
			}
			else if(!this.stealthed){
				forceRender(g, x, y);
			}
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
	}

	/**
	 * renders the entity to a Graphics at a certain X and Y.
	 * 
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public abstract void forceRender(Graphics g, float x, float y);

	/**
	 * Gets the use.
	 * 
	 * @return the use
	 */
	public Action getUse() {
		DeadReckoningGame.instance.getMessageElement().addMessage(
				"That's Not Allowed");
		return new WaitAction(this.getID());
	}

	/**
	 * returns the tile this entity is standing on / is located in.
	 * 
	 * @return the tile it is standing on
	 */
	public Tile getLocation() {
		return location;
	}

	/**
	 * gets the Gameboard that this entity is a member of.
	 * 
	 * @return the gameboard this is a member of
	 */
	public GameBoard getParent() {
		return getLocation().getParent();
	}

	/**
	 * moves this entity to another tile. use with extreme caution, because it
	 * does not remove this entity from the tile it is currently on
	 * 
	 * if the idea is to move something from tile A to tile B, it would be
	 * better to use Gameboard.moveEntity()
	 * 
	 * @param t
	 *            the new location
	 */
	public void placeAt(Tile t, int layer) {
		t.getParent().placeEntity(t, this, layer);
	}

	/**
	 * returns the X position of the entity (in the XY coordinate plane of the
	 * tilesystem).
	 * 
	 * @return the X position
	 */
	public int getX() {
		return getLocation().getX();
	}

	/**
	 * returns the Y position of the entity (in the XY coordinate plane of the
	 * tilesystem).
	 * 
	 * @return the Y position
	 */
	public int getY() {
		return getLocation().getY();
	}

	/**
	 * returns the X position of the entity's display (in the XY coordinate
	 * plane of the screen. 1=1pixel.)
	 * 
	 * @return the Absolute X position
	 */
	public int getAbsoluteX() {
		return (int) (getLocation().getX() * DeadReckoningGame.tileSize);
	}

	/**
	 * returns the Y position of the entity's display (in the XY coordinate
	 * plane of the screen. 1=1pixel.)
	 * 
	 * @return the Absolute Y position
	 */
	public int getAbsoluteY() {
		return (int) (getLocation().getY() * DeadReckoningGame.tileSize);
	}

	/**
	 * old method, hold over from previous model of entity system. serves no
	 * purpose. it should be abstract, if it will continue to exist at all
	 * 
	 * as of now, it provides a result for an interaction
	 * 
	 * @param e
	 *            the entity with which to interact
	 * @return the action
	 */
	public abstract Action onInteract(Entity e);

	/**
	 * returns a string description for the observe action.
	 * 
	 * @param e
	 *            the e
	 * @return the description
	 */
	public String getDescription(Entity e) {
		return this.description;
	}

	/**
	 * sets the facing direction of the entity (if true, flip the sprite).
	 * 
	 * @param facing
	 *            facing left or right (true = right, left = false)
	 */
	public void setFacing(boolean facing) {
		this.facing = facing;
	}

	/**
	 * gets the facing direction of the entity (if true, flip the sprite).
	 * 
	 * @return facing facing left or right (true = right, left = false)
	 */
	public boolean getFacing() {
		return facing;
	}

	/**
	 * sets if the entity is "see through" (light passes through it).
	 * 
	 * @param transparent
	 *            the new transparent
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	/**
	 * gets if the entity is "see through" (light passes through it).
	 * 
	 * @return true, if is transparent
	 */
	public boolean isTransparent() {
		return transparent;
	}

	/**
	 * Gets the allignment.
	 * 
	 * @return the allignment
	 */
	public int getAllignment() {
		return this.allignmnet;
	}

	/**
	 * Sets the allignment.
	 * 
	 * @param allign
	 *            the new allignment
	 */
	public void setAllignment(int allign) {
		this.allignmnet = allign;
	}

	/**
	 * Checks if is interactive.
	 * 
	 * @return true, if is interactive
	 */
	public boolean makesActions() {
		return makesActions;
	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return visible && (!stealthed || allignmnet==ALLIGN_NEUTRAL);
	}

	/**
	 * Sets the visible.
	 * 
	 * @param visible
	 *            the new visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Kill.
	 */
	public void kill() {
		this.toKill = true;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param n
	 *            the new name
	 */
	public void setName(String n) {
		this.name = n;
	}

	/**
	 * Advance turn.
	 * 
	 * @return the array list
	 */
	public abstract ArrayList<Action> advanceTurn();

	/**
	 * Checks if is idle.
	 * 
	 * @return true, if is idle
	 */
	public boolean isIdle() {
		return true;
	}

	/**
	 * Make from string.
	 * 
	 * @param target
	 *            the target
	 * @param attributes
	 *            the attributes
	 * @return the entity
	 * @throws SlickException 
	 */
	public abstract Entity makeFromString(GameBoard target, String[] attributes) throws SlickException;

	/**
	 * Save to string.
	 * 
	 * @return the string
	 */
	public abstract String saveToString();

	/**
	 * Gets the generic save.
	 * 
	 * @return the generic save
	 */
	protected String getGenericSave() {
		return this.getClass().getCanonicalName() + ":" + this.getX() + ":"
				+ this.getY() + ":" + this.getLayer();
	}

	/**
	 * Sets the layer.
	 * 
	 * @param layer
	 *            the new layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * Gets the layer.
	 * 
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * On death.
	 */
	public abstract void onDeath();

	/**
	 * Checks if is terrain.
	 * 
	 * @return true, if is terrain
	 */
	public boolean isTerrain() {
		return isTerrain;
	}
	
	public int getID() {
		return this.entityID;
	}
	
	public void setID(int ID) {
		this.entityID=ID;
	}

	public void setLocation(Tile tile, int layer) {
		this.location=tile;
		this.layer=layer;
	}

	public boolean isStealthed() {
		return this.stealthed;
	}

}