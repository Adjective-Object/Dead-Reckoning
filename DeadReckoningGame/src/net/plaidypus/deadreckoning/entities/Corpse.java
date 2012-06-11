package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.modloader.ModLoader;

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
	 * I hate you, java
	 * 
	 * NEVER USE THIS SHIT
	 */
	public Corpse() {
	}

	/**
	 * Instantiates a new corpse.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 * @param e
	 *            the e
	 */
	public Corpse(Tile t, int layer, LivingEntity e) {
		super(t, layer);
		this.entity = e;
		this.inventory = entity.getInventory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#update(org.newdawn
	 * .slick.GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects
	 * (org.newdawn.slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#chooseAction(org
	 * .newdawn.slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#forceRender(org
	 * .newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(
				entity.getAnimation(LivingEntity.ANIMATION_DEATH).getImage(
						entity.getAnimation(LivingEntity.ANIMATION_DEATH)
								.getFrameCount() - 1), x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
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
		LivingEntity e = null;
		try {
			e = (LivingEntity) ModLoader.loadClass(attributes[4]).newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String[] subatt = new String[attributes.length - 3];
		for (int i = 4; i < attributes.length; i++) {
			subatt[i - 4] = attributes[i];
		}
		return new Corpse(target.getTileAt(Integer.parseInt(attributes[1]),
				Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]),
				(LivingEntity) e.makeFromString(target, subatt));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave() + ":" + this.entity.saveToString();
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
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
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
		return new LootAction(e, this.getLocation(), this.getLayer());
	}

}
