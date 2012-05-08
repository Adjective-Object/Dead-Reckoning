package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Corpse.
 */
public class Corpse extends InteractiveEntity {

	/** The entity. */
	LivingEntity entity;

	/**
	 * Instantiates a new corpse.
	 *
	 * @param t the t
	 * @param layer the layer
	 * @param e the e
	 */
	public Corpse(Tile t, int layer, LivingEntity e) {
		super(t, layer);
		this.entity = e;
		this.inventory = entity.getInventory();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects(org.newdawn.slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#chooseAction(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#forceRender(org.newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(
				entity.getAnimation(LivingEntity.ANIMATION_DEATH).getImage(
						entity.getAnimation(LivingEntity.ANIMATION_DEATH)
								.getFrameCount() - 1), x, y);
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
		return new ArrayList<Action>(0);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus.deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
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
		return new LootAction(e, this.getLocation(), this.getLayer());
	}

}
