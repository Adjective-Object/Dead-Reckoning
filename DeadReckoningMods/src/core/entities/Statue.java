package core.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.StaticImageEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Statue.
 */
public class Statue extends StaticImageEntity {

	/** The i. */
	static Image i;

	/**
	 * Instantiates a new statue.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 */
	public Statue() {
		super(i);
		this.description = "the placeholder object that loads when something glitches during load";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	public void init() throws SlickException {
		i = new Image("res/statue.png");
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
		return new WaitAction(this.getID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.StaticImageEntity#forceRender(org
	 * .newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(i, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean makesActions() {
		return false;
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
		Statue s = new Statue();
		s.setLocation(
				target.getTileAt(
						Integer.parseInt(attributes[1]),
						Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]));
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return getGenericSave();
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
		// TODO nothing doing
		return null;
	}

}
