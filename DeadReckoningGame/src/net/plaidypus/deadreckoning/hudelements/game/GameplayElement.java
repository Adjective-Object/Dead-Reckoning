package net.plaidypus.deadreckoning.hudelements.game;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.save.Save;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class GameplayElement.
 */
public class GameplayElement extends HudElement {

	/** The state id. */
	int stateID;

	/** The current Entity (for deciding). */
	int currentEntity;
	
	/** The save number. */
	public String saveLocation;

	/** The width. */
	private int width, height;

	private boolean entitiesLooped;

	/** The camera positioning. */
	public static float cameraX, cameraY;

	/** The camera dest y. */
	public static float cameraDestX, cameraDestY;

	/** The Constant cameraRate. */
	static final float cameraRate = 1000F, cameraSensitivityFrac = 1.0F;

	/** The last map. */
	public String lastMap;

	/** The input. */
	Input input;

	/** The player. */
	public Player player;

	/** The background screen. */
	static Image backgroundScreen;

	/** The gb. */
	private GameBoard gb;

	/** The gc. */
	GameContainer gc;

	/** The actions. */
	ArrayList<Action> actions = new ArrayList<Action>(0);
	
	Action instantAction = null;

	/**
	 * Instantiates a new gameplay element.
	 * 
	 * @param saveNumber
	 *            the save number
	 * @throws SlickException
	 *             the slick exception
	 */
	public GameplayElement(int saveNumber) throws SlickException {
		super(0, 0, HudElement.TOP_LEFT, true);
		currentEntity = 0;
	}

	/**
	 * initializes the gameplay state.
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @throws SlickException
	 *             the slick exception
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		this.width = gc.getWidth();
		this.height = gc.getHeight();

		Log.debug("Initializing GameplayState");

		input = gc.getInput();

		lastMap = "";

		DamageEffect.init();

		backgroundScreen = new Image(gc.getWidth(), gc.getHeight());

		gc.setTargetFrameRate(60);
		gc.setVSync(true);

		this.gc = gc;

	}

	/**
	 * Sets the player.
	 * 
	 * @param p
	 *            the new player
	 */
	public void setPlayer(Player p) {
		player = p;
		p.setInput(this.input);
	}

	public void resetBoard(){
		this.gb=null;
		this.lastMap="";
		this.entitiesLooped=false;
	}
	
	/**
	 * Sets the board.
	 * 
	 * @param b
	 *            the new board
	 */
	public void setBoard(GameBoard b) {
		if (this.gb != null) {
			lastMap = this.gb.getMapID();
			try {
				Save.updateSave(saveLocation, player, gb);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		System.out.println(lastMap + " -> " + b);

		b.setGame(this);
		b.renderDistX = this.getWidth() / DeadReckoningGame.tileSize + 2;
		b.renderDistY = this.getHeight() / DeadReckoningGame.tileSize + 2;

		this.gb = b;
		
		cameraDestX = player.getAbsoluteX() - gc.getWidth() / 2
				+ DeadReckoningGame.tileSize / 2;
		cameraDestY = player.getAbsoluteY() - gc.getHeight() / 2
				+ DeadReckoningGame.tileSize / 2;

		actions.clear();

		updateBoardEffects(gc);
		this.currentEntity = 0;
	}

	public void updateSave() throws IOException {
		Save.updateSave(saveLocation, player, gb);
	}

	/**
	 * updates the gamestate (called automagically by slick).
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @param delta
	 *            the delta
	 * @throws SlickException
	 *             the slick exception
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		cameraX = cameraX + (cameraDestX - cameraX) * (delta/cameraRate);
		cameraY = cameraY + (cameraDestY - cameraY) * (delta/cameraRate);

		if (gc.getInput().isKeyPressed(Input.KEY_Y)) {
			cameraDestX = getBoard().getIngameEntities().get(currentEntity)
					.getAbsoluteX()
					- gc.getWidth()
					/ 2
					+ DeadReckoningGame.tileSize / 2;
			cameraDestY = getBoard().getIngameEntities().get(currentEntity)
					.getAbsoluteY()
					- gc.getHeight()
					/ 2
					+ DeadReckoningGame.tileSize / 2;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.INGAMEMENUSTATE);
		}
		if (gc.getInput().isKeyPressed(Input.KEY_BACKSLASH)) {
			DeadReckoningGame.debugMode=!DeadReckoningGame.debugMode;
		}

		getBoard().updateSelctor(input, -cameraX, -cameraY);
		getBoard().update(gc, delta);
		updateActions(gc, delta);
	}

	/**
	 * Update board effects.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 */
	public void updateBoardEffects(GameContainer gc) {
		getBoard().HideAll();
		getBoard().updateBoardEffects(gc);
		for(int i=0; i<getBoard().getIngameEntities().size(); i++){
			Entity e = getBoard().getIngameEntities().get(i);
			if(e.allignmnet==Entity.ALLIGN_FRIENDLY){
				getBoard().revealFromEntity(e , 10);
			}
		}
	}

	/**
	 * Update actions. can be considered the real main loop of degame
	 * 
	 * 3 seperate ifs, and not linked in an if/then statement because it means actions can be resolved in fewer frames.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @throws SlickException 
	 */
	private void updateActions(GameContainer gc, int delta) throws SlickException {
		// if the game is still trying to determine all the actions, try and resolve them all, and there is no instant action to drop
		if(!entitiesLooped && instantAction==null){
			Action a = this.getBoard().getIngameEntities().get(currentEntity).chooseAction(gc, delta);
			while (a!=null && a.takesTurn && !entitiesLooped){//iterate through all entities, until the point where an entitiy cannot decide (player input)
				actions.add(a);
				actions.addAll(this.getBoard().getIngameEntities().get(currentEntity).advanceTurn());
				advanceEntity();
				if(!this.entitiesLooped){// this happens when all the entities have their actions decided.
					a = this.getBoard().getIngameEntities().get(currentEntity).chooseAction(gc, delta);
				}
			}
			if(!entitiesLooped && a!=null){ // if it was terminated because an instant-drop action was selected
				instantAction=a;
			}
		}
		
		//applying instant actions
		if(instantAction!=null){
			instantAction.applyAction(delta);
			if(instantAction.completed){
				instantAction=null;
			}
		}
		
		//applying entity actions
		if(entitiesLooped && instantAction==null){
			applyAllActions(delta);
			if(actionsComplete()){// if they have been applied
				finalizeTurn();
				entitiesLooped=false;//go back to deciding the next round of actions
			}
		}
	}
	
	/**
	 * Finalize turn.
	 * 
	 * @param delta
	 *            the delta
	 */
	private void finalizeTurn() {
		updateBoardEffects(gc);
		getBoard().clearHighlightedSquares();
		input.clearKeyPressedRecord();
		alertCameraTo(player);
	}

	/**
	 * Apply all actions.
	 * 
	 * @param delta
	 *            the delta
	 * @throws SlickException 
	 */
	private void applyAllActions(int delta) throws SlickException {
		for(int i=0; i<actions.size(); i++){
			actions.get(i).applyAction(delta);
		}
	}
	
	private void alertCameraTo(Entity e) {
		cameraDestX = e.getAbsoluteX() - gc.getWidth() / 2
				+ DeadReckoningGame.tileSize / 2;
		cameraDestY = e.getAbsoluteY() - gc.getHeight() / 2
				+ DeadReckoningGame.tileSize / 2;
	}

	/**
	 * Advance entity.
	 */
	private void advanceEntity() {
		boolean f = true;
		while(!gb.getIngameEntities().get(currentEntity).makesActions() || f){
			f=false;
			currentEntity = (currentEntity+1);
			if(currentEntity>=gb.getIngameEntities().size()){
				currentEntity -= gb.getIngameEntities().size();
				entitiesLooped=true;
			}
		}
	}

	/**
	 * Actions complete.
	 * 
	 * @return true, if successful
	 */
	private boolean actionsComplete() {
		for (int i = 0; i < actions.size(); i++) {
			if (!actions.get(i).completed) {
				return false;
			}
		}
		return true;
	}

	/**
	 * renders the gamestate (gameboard and particless).
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @param g
	 *            the g
	 * @throws SlickException
	 *             the slick exception
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		getBoard().render(g, (int)-cameraX, (int)-cameraY);

		g.copyArea(backgroundScreen, 0, 0);

	}

	/**
	 * Gets the iD.
	 * 
	 * @return the iD
	 */
	public int getID() {
		return stateID;
	}

	/**
	 * adds a particle to the particle list in game.
	 * 
	 * @return the image
	 */

	public static Image getImage() {
		return backgroundScreen;
	}

	/**
	 * Adds the action.
	 * 
	 * @param a
	 *            the a
	 */
	public void addAction(Action a) {
		if (a != null) {
			this.actions.add(a);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	@Override
	public void makeFrom(Object o) {
	}

	/**
	 * Gets the board.
	 * 
	 * @return the board
	 */
	public GameBoard getBoard() {
		return gb;
	}

}
