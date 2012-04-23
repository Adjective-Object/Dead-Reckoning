package net.plaidypus.deadreckoning.hudelements.game;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.skills.Fireball;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class GameplayElement.
 */
public class GameplayElement extends HudElement {

	/** The state id. */
	int stateID;
	
	/** The current action. */
	int currentEntity, currentAction;
	
	/** The save number. */
	int saveNumber;
	
	/** The time on. */
	int timeOn;

	/** The width. */
	private int width;
	
	/** The height. */
	private int height;

	/** The camera y. */
	public static float cameraX, cameraY;
	
	/** The camera dest y. */
	public static float cameraDestX, cameraDestY;

	/** The Constant cameraRate. */
	static final float cameraRate = (float) 0.2;

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

	/**
	 * Instantiates a new gameplay element.
	 *
	 * @param saveNumber the save number
	 * @throws SlickException the slick exception
	 */
	public GameplayElement(int saveNumber) throws SlickException {
		super(0, 0, HudElement.TOP_LEFT, true);
		currentEntity = 0;
		currentAction = 0;
	}

	/**
	 * initializes the gameplay state.
	 *
	 * @param gc the gc
	 * @param sbg the sbg
	 * @throws SlickException the slick exception
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		this.width = gc.getWidth();
		this.height = gc.getHeight();

		System.out.println("Initializing GameplayState");

		input = gc.getInput();

		lastMap = "";

		Fireball.init();
		DamageEffect.init();
		Tile.init("res\\wallTiles.png");

		backgroundScreen = new Image(gc.getWidth(), gc.getHeight());

		gc.setTargetFrameRate(60);
		gc.setVSync(true);

		this.gc = gc;
		System.out.println(gc);

	}

	/**
	 * Sets the player.
	 *
	 * @param p the new player
	 */
	public void setPlayer(Player p) {
		player = p;
		p.setInput(this.input);
	}

	/**
	 * Sets the board.
	 *
	 * @param b the new board
	 */
	public void setBoard(GameBoard b) {
		if (this.gb != null) {
			lastMap = this.gb.getMapID();
		}

		System.out.println(lastMap + " -> " + b.getMapID());

		b.setGame(this);
		b.renderDistX = this.getWidth() / DeadReckoningGame.tileSize + 2;
		b.renderDistY = this.getHeight() / DeadReckoningGame.tileSize + 2;

		this.gb = b;

		Tile target = null;
		while (target == null) {
			Tile t = gb.getTileAt(Utilities.randInt(0, gb.getWidth()),
					Utilities.randInt(0, gb.getHeight()));
			if (t.getTileFace() != Tile.TILE_NULL) {
				target = t;
			}
		}

		if (lastMap != "") {
			for (int x = 0; x < b.getWidth(); x++) {
				for (int y = 0; y < b.getHeight(); y++) {
					if (!this.gb.getTileAt(x, y).isOpen(Tile.LAYER_PASSIVE_MAP)) {
						try {
							LandingPad pad = LandingPad.class.cast(b.getTileAt(
									x, y).getEntity(Tile.LAYER_PASSIVE_MAP));
							if (pad.fromFloor.equals(lastMap)) {
								target = this.gb.getTileAt(x, y);
							}
						} catch (ClassCastException e) {
						}
					}
				}
			}
		}

		gb.insertEntity(0, target, player, player.getLayer());

		cameraDestX = player.getAbsoluteX() - gc.getWidth() / 2
				+ DeadReckoningGame.tileSize / 2;
		cameraDestY = player.getAbsoluteY() - gc.getHeight() / 2
				+ DeadReckoningGame.tileSize / 2;
		cameraX = cameraDestX;
		cameraY = cameraDestY;

		timeOn = 0;
		actions.clear();

		updateBoardEffects(gc, 0);
		this.currentAction = 0;
		this.currentEntity = 0;
	}

	/**
	 * updates the gamestate (called automagically by slick).
	 *
	 * @param gc the gc
	 * @param sbg the sbg
	 * @param delta the delta
	 * @throws SlickException the slick exception
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		cameraX = cameraX + (cameraDestX - cameraX) * cameraRate;
		cameraY = cameraY + (cameraDestY - cameraY) * cameraRate;

		if (gc.getInput().isKeyPressed(Input.KEY_Y)) {
			cameraDestX = getBoard().ingameEntities.get(currentEntity)
					.getAbsoluteX()
					- gc.getWidth()
					/ 2
					+ DeadReckoningGame.tileSize / 2;
			cameraDestY = getBoard().ingameEntities.get(currentEntity)
					.getAbsoluteY()
					- gc.getHeight()
					/ 2
					+ DeadReckoningGame.tileSize / 2;
		}

		getBoard().updateSelctor(input, -cameraX, -cameraY);
		getBoard().updateAllTiles(gc, delta);
		updateActions(gc, delta);
	}

	/**
	 * Update board effects.
	 *
	 * @param gc the gc
	 * @param delta the delta
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
		getBoard().HideAll();
		getBoard().updateBoardEffects(gc, delta);
		getBoard().revealFromEntity(player, 6);// TODO replace with from player
												// alligned units?
	}

	/**
	 * Update actions.
	 *
	 * @param gc the gc
	 * @param delta the delta
	 */
	private void updateActions(GameContainer gc, int delta) {
		if (actions.size() == 0) {// if the current entity is in the middle of
									// being parsed..
			while (!getBoard().ingameEntities.get(this.currentEntity)
					.getLocation().canBeSeen()
					|| !getBoard().ingameEntities.get(this.currentEntity)
							.isInteractive()) {// iterate through all invisible
												// entities
				if (getBoard().ingameEntities.get(this.currentEntity)
						.isInteractive()) {
					getActions(delta);// get their actions
					while (!actionsComplete()) {// apply all the actions until
												// completion
						applyAllActions(delta);
					}
				}
				advanceEntity();
			}

			// when it hits an entity that is on screen and is interactive
			// (takes multiple updateActions calls)
			getActions(delta);
			alertCameraTo(this.getBoard().ingameEntities.get(currentEntity));
		} else if (cameraIsFocused()) {
			applyAllActions(delta);
			if (actionsComplete()) {
				if (actionsTakeTurn()) {
					finalizeTurn(delta);
				} else {
					currentAction = 0;
					actions.clear();
				}
			}
		}
	}

	/**
	 * Finalize turn.
	 *
	 * @param delta the delta
	 */
	private void finalizeTurn(int delta) {
		advanceEntity();
		updateBoardEffects(gc, delta);
		getBoard().clearHighlightedSquares();
		getBoard().clearPrimaryHighlight();
		input.clearKeyPressedRecord();
	}

	/**
	 * Apply all actions.
	 *
	 * @param delta the delta
	 */
	private void applyAllActions(int delta) {
		actions.get(currentAction).applyAction(delta);
		if (actions.size() > 0 && actions.get(currentAction).completed) {
			currentAction++;
		}
	}

	/**
	 * Actions take turn.
	 *
	 * @return true, if successful
	 */
	private boolean actionsTakeTurn() {
		for (int i = 0; i < this.actions.size(); i++) {
			if (this.actions.get(i).takesTurn) {
				return true;
			}
		}
		return actions.size() == 0;
	}

	/**
	 * Gets the actions.
	 *
	 * @param delta the delta
	 * @return the actions
	 */
	private void getActions(int delta) {
		Action a = this.getBoard().ingameEntities.get(currentEntity)
				.chooseAction(gc, delta);
		if (a != null) {
			if (a.takesTurn) {
				actions.addAll(this.getBoard().ingameEntities
						.get(currentEntity).advanceTurn());
			}
			actions.add(a);
		}
	}

	/**
	 * Camera is focused.
	 *
	 * @return true, if successful
	 */
	private boolean cameraIsFocused() {
		return (Math.abs(cameraDestX - cameraX) < 0.5 && Math.abs(cameraDestY
				- cameraY) < 0.5)
				|| !this.getBoard().ingameEntities.get(currentEntity)
						.getLocation().canBeSeen();
	}

	/**
	 * Alert camera to.
	 *
	 * @param e the e
	 */
	private void alertCameraTo(Entity e) {
		int nx = e.getAbsoluteX() - gc.getWidth() / 2
				+ DeadReckoningGame.tileSize / 2;
		int ny = e.getAbsoluteY() - gc.getHeight() / 2
				+ DeadReckoningGame.tileSize / 2;
		if (Math.abs(cameraDestX - nx) > gc.getWidth() / 3) {
			cameraDestX = nx;
			timeOn = 0;
		}
		if (Math.abs(cameraDestY - ny) > gc.getHeight() / 4) {
			cameraDestY = ny;
			timeOn = 0;
		}
	}

	/**
	 * Advance entity.
	 */
	private void advanceEntity() {
		currentEntity = (currentEntity + 1) % getBoard().ingameEntities.size();
		while (!getBoard().ingameEntities.get(currentEntity).isInteractive()) {
			currentEntity = (currentEntity + 1)
					% getBoard().ingameEntities.size();
		}
		actions.clear();
		currentAction = 0;
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
	 * @param gc the gc
	 * @param sbg the sbg
	 * @param g the g
	 * @throws SlickException the slick exception
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		getBoard().render(g, -cameraX, -cameraY);

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
	 * @param a the a
	 */
	public void addAction(Action a) {
		if (a != null) {
			this.actions.add(a);
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang.Object)
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
