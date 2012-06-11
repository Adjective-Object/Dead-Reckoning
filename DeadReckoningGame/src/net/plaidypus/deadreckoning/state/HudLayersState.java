package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Class HudLayersState.
 * 
 * generic, used to assemble HudElements into a BasicGameState useable by Slick
 */
public class HudLayersState extends BasicGameState {

	/** The state id used for referencing this file */
	int stateID;

	/** The parent DeadReckoningGame. */
	DeadReckoningGame parent;

	/** The Hud elements contained in this HudLayersState */
	ArrayList<HudElement> HudElements;

	/**
	 * Instantiates a new hud layers state.
	 * 
	 * @param stateID
	 *            the state id
	 * @param elements
	 *            the elements
	 */
	public HudLayersState(int stateID, ArrayList<HudElement> elements) {
		this.stateID = stateID;
		this.HudElements = elements;
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).setParent(this);
		}
	}

	/**
	 * Instantiates a new hud layers state.
	 * 
	 * @param stateID
	 *            the state id
	 * @param elementsarr
	 *            the elementsarr
	 */
	public HudLayersState(int stateID, HudElement[] elementsarr) {
		this.stateID = stateID;
		HudElements = new ArrayList<HudElement>(0);
		for (int i = 0; i < elementsarr.length; i++) {
			HudElements.add(elementsarr[i]);
			HudElements.get(i).setParent(this);
		}
	}

	/**
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame) calls the initialization
	 *      function of each of the elements in the HudLayersState
	 * 
	 *      the Initialization function is used (mostly) to load common
	 *      resources that are used across the board by all members of a given
	 *      class, although that need not be the case
	 */
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		for (int i = 0; i < this.HudElements.size(); i++) {
			HudElements.get(i).init(container, game);
		}
	}

	/**
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 * 
	 *      updates the elements of the state
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).update(container, game, delta);
		}
	}

	/**
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		if (gc.getInput().isKeyDown(Input.KEY_0)) {
			g.setColor(Color.green);
		}
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).render(gc, game, g);
		}
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).renderMouseOver(gc, game, g);
		}
	}

	/**
	 * this function passes each element of the object[] to the corresponding
	 * HudElement, calling the makeFrom(Object) function on each of them.
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(Object)
	 * 
	 * @param objects
	 *            the objects
	 */
	public void makeFrom(Object[] objects) {
		System.out.println("HudLayersState " + stateID);
		for (int i = 0; i < objects.length; i++) {
			this.HudElements.get(i % HudElements.size()).makeFrom(objects[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return this.stateID;
	}

	/**
	 * Gets the elements in the state.
	 * 
	 * @return the elements
	 */
	public ArrayList<HudElement> getElements() {
		return HudElements;
	}

	/**
	 * Gets a specific ekement in the list.
	 * 
	 * @param index
	 *            the index
	 * @return the element
	 */
	public HudElement getElement(int index) {
		return HudElements.get(index);
	}

	/**
	 * Gets the parent DeadReckoningGame.
	 * 
	 * @return the parent
	 */
	public DeadReckoningGame getParent() {
		return this.parent;
	}

}
