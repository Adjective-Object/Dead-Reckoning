package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/*

 Griffin Brodman & Jeffrey Tao
 Created: 11/4/2011

 */

public abstract class Entity {
	private Tile location;
	
	private String name = "NAMELESS ENTITY";
	private boolean facing;
	private boolean transparent, visible;
	public boolean toKill;
	public int allignmnet;
	public static final int ALLIGN_NEUTRAL = 0, ALLIGN_HOSTILE = 1, ALLIGN_FRIENDLY = 2;
	
	String stringCode;
	
	public Entity(String stringCode){
		this.stringCode=stringCode;
	}
	
	public Entity(Tile t) {
		this.visible=true;
		this.transparent=true;
		this.allignmnet=0;
		t.getParent().placeEntity(t, this);
	}
	
	/**
	 * updates the entity. Used primarily for animation.
	 * 
	 * @param gc
	 * @param delta
	 */
	public abstract void update(GameContainer gc, int delta);

	/**
	 * chooses the next action. returns either the action or null for undecided.
	 * if undecided, the GameState should continue calling chooseAction until it
	 * returns an action
	 * 
	 * @param gc
	 * @param delta
	 *            the elapsed milliseconds since the last call of update
	 * @return
	 */
	
	public abstract void updateBoardEffects(GameContainer gc, int delta);
	public abstract Action chooseAction(GameContainer gc, int delta);
	
	/**
	 * renders to Graphics only if this.visible
	 * calls this.forceRender()
	 **/
	public void render(Graphics g, float x, float y){
		if(this.visible){forceRender(g,x,y);}
	}
	
	/**
	 * renders the entity to a Graphics at a certain X and Y
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public abstract void forceRender(Graphics g, float x, float y);

	/**
	 * returns the tile this entity is standing on / is located in
	 * 
	 * @return the tile it is standing on
	 */
	public Tile getLocation() {
		return location;
	}

	/**
	 * gets the Gameboard that this entity is a member of
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
	 */
	public void setLocation(Tile t) {
		location = t;
	}
	
	/**
	 * returns the X position of the entity (in the XY coordinate plane of the
	 * tilesystem)
	 * 
	 * @return the X position
	 */
	public int getX() {
		return getLocation().getX();
	}

	/**
	 * returns the Y position of the entity (in the XY coordinate plane of the
	 * tilesystem)
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
	 * @param e
	 *            the entity with which to interact
	 */
	public void interact(Entity e) {

	}

	/**
	 * sets the facing direction of the entity (if true, flip the sprite)
	 * 
	 * @param facing
	 *            facing left or right (true = right, left = false)
	 */
	public void setFacing(boolean facing) {
		this.facing = facing;
	}

	/**
	 * gets the facing direction of the entity (if true, flip the sprite)
	 * 
	 * @return facing
	 *            facing left or right (true = right, left = false)
	 */
	public boolean getFacing() {
		return facing;
	}
	
	/**
	 * sets if the entity is "see through" (light passes through it)
	 * @param transparent
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	/**
	 *gets if the entity is "see through" (light passes through it)
	 * @param transparent
	 */
	public boolean isTransparent() {
		return transparent;
	}
		
	public int getAllignment(){
		return this.allignmnet;
	}
	
	public void setAllignment(int allign){
		this.allignmnet=allign;
	}
	
	public abstract boolean isInteractive();

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void kill(){
		this.toKill=true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String n){
		this.name=n;
	}

	public abstract ArrayList<Action> advanceTurn();

	public boolean isIdle() {
		return true;
	}
	
	public abstract Entity makeFromString(GameBoard target, String[] attributes);
	public abstract String saveToString();

}