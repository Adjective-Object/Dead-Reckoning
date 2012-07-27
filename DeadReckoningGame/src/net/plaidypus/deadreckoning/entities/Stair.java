package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeBoardAction;
import net.plaidypus.deadreckoning.board.GameBoard;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Stair.
 */
public class Stair extends StaticImageEntity {

	/** The Constant NULL. */
	public static final int UP = 0, DOWN = 1, NULL = 2;

	/** The target floor. */
	public String targetFloor;

	/** The stair images. */
	static ArrayList<Image> stairImages;

	/** The updown null. */
	public int updownNull;

	/**
	 * Instantiates a new stair.
	 * 
	 * @param target
	 *            the target
	 * @param layer
	 *            the layer
	 * @param targetFloor
	 *            the target floor
	 * @param updownNull
	 *            the updown null
	 */
	public Stair( String targetFloor, int updownNull) {
		super( stairImages.get(updownNull));
		this.targetFloor = targetFloor;
		this.updownNull = updownNull;
		this.isTerrain = true;
	}

	/**
	 * Instantiates a new stair.
	 */
	public Stair() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		stairImages = new ArrayList<Image>(0);
		stairImages.add(new Image("res/stairs.png"));
		stairImages.add(new Image("res/stairsDown.png"));
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
	public void updateBoardEffects(GameContainer gc) {
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
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
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
		Stair s = new Stair( attributes[4], Integer.parseInt(attributes[5]));
		s.setLocation(
				target.getTileAt(
						Integer.parseInt(attributes[1]),
						Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]) );
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave() + ":" + targetFloor + ":" + updownNull;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
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
		return new ChangeBoardAction(this.getParent().getGame().player.getID(), targetFloor);
	}

}
