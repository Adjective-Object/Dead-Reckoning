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

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new statue.
	 */
	public Statue() {
	}

	/**
	 * Instantiates a new statue.
	 *
	 * @param t the t
	 * @param layer the layer
	 */
	public Statue(Tile t, int layer) {
		super(t, layer, i);
		this.description = "the placeholder object that loads when something glitches during load";
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	public void init() throws SlickException {
		i = new Image("res/statue.png");
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#chooseAction(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return new WaitAction(this);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.StaticImageEntity#forceRender(org.newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(i, x, y);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus.deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		System.out.println(" " + attributes[0] + " " + attributes[1] + " "
				+ attributes[2]);
		return new Statue(target.getTileAt(Integer.parseInt(attributes[1]),
				Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]));
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return getGenericSave();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		// TODO nothing doing
		return null;
	}

}
