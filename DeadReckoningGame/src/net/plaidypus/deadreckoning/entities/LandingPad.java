package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class LandingPad.
 */
public class LandingPad extends Entity {

	/** The from floor. */
	public String fromFloor;

	/**
	 * Instantiates a new landing pad.
	 */
	public LandingPad() {
	}

	/**
	 * Instantiates a new landing pad.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 * @param fromFloor
	 *            the from floor
	 */
	public LandingPad(Tile t, int layer, String fromFloor) {
		super(t, layer);
		this.fromFloor = fromFloor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.
	 * GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn
	 * .slick.GameContainer, int)
	 */
	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#chooseAction(org.newdawn.
	 * slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick
	 * .Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean makesActions() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus
	 * .deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		return new LandingPad(target.getTileAt(Integer.parseInt(attributes[1]),
				Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]), attributes[4]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave() + ":" + fromFloor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
		// TODO empty
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.
	 * deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		return null;
	}

}
