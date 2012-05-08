package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Wall.
 */
public class Wall extends Entity {

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new wall.
	 */
	public Wall() {
	}

	/**
	 * Instantiates a new wall.
	 *
	 * @param targetTile the target tile
	 * @param layer the layer
	 */
	public Wall(Tile targetTile, int layer) {
		super(targetTile, layer);
		this.setTransparent(false);
		this.description = "the walls are cracked and worn.";
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn.slick.GameContainer, int)
	 */
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
	 * @see net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick.Graphics, float, float)
	 */
	public void forceRender(Graphics g, float x, float y) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus.deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return new Wall(g.getTileAt(Integer.parseInt(toload[1]),
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
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		// TODO Auto-generated method stub
		return null;
	}

}
