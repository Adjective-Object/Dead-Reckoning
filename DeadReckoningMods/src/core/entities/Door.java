package core.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Door.
 */
public class Door extends Entity {

	/** The open. */
	boolean open;

	/** The close img. */
	static Image openImg, closeImg;

	/**
	 * Instantiates a new door.
	 */
	public Door() {
		super();
	}

	/**
	 * Instantiates a new door.
	 *
	 * @param t the t
	 * @param layer the layer
	 */
	public Door(Tile t, int layer) {
		super(t, layer);
		open = false;
		this.isTerrain = true;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		openImg = new Image("res/doorOpen.png");
		closeImg = new Image("res/doorClosed.png");
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
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
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		if (open) {
			g.drawImage(openImg, x, y);
		} else {
			g.drawImage(closeImg, x, y);
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#isTransparent()
	 */
	@Override
	public boolean isTransparent() {
		return this.open;
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
	public Entity makeFromString(GameBoard target, String[] toload) {
		return new Door(target.getTileAt(Integer.parseInt(toload[1]),
				Integer.parseInt(toload[2])), Integer.parseInt(toload[3]));
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave();
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
		this.open = !open;
		Tile t = this.getLocation();
		t.getParent().removeEntity(this);
		if (open) {
			t.getParent().placeEntity(t, this, Tile.LAYER_PASSIVE_MAP);
		} else {
			t.getParent().placeEntity(t, this, Tile.LAYER_ACTIVE);
		}
		return null;
	}

}
