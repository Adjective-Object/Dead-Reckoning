package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class InteractiveEntity.
 */
public abstract class InteractiveEntity extends Entity {

	/** The inventory. */
	protected ArrayList<Item> inventory = new ArrayList<Item>(0);

	/** The inventory size. */
	public int inventorySize = 5;

	/**
	 * Instantiates a new interactive entity.
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 */
	public InteractiveEntity() {
		this(new ArrayList<Item>(0));
	}

	/**
	 * Instantiates a new interactive entity.
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 * @param items
	 *            the items
	 */
	public InteractiveEntity(ArrayList<Item> items) {
		super();
		this.inventory.addAll(items);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.
	 * GameContainer, int)
	 */
	@Override
	public abstract void update(GameContainer gc, int delta);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn
	 * .slick.GameContainer, int)
	 */
	@Override
	public void updateBoardEffects(GameContainer gc) {
		this.collapseInventory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#chooseAction(org.newdawn.
	 * slick.GameContainer, int)
	 */
	@Override
	public abstract Action chooseAction(GameContainer gc, int delta);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick
	 * .Graphics, float, float)
	 */
	@Override
	public abstract void forceRender(Graphics g, float x, float y);

	/**
	 * Gets the inventory.
	 * 
	 * @return the inventory
	 */
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	/**
	 * Collapse inventory.
	 */
	public void collapseInventory() {// TODO this doesn't work at all.
		Item.collapseItemArray(this.inventory);
	}

	/**
	 * Adds the item.
	 * 
	 * @param i
	 *            the i
	 * @return if the item was successfully added to the inventory
	 */
	public boolean addItem(Item i) {
		if (inventory.size() <= inventorySize) {
			inventory.add(i);
			this.collapseInventory();
			return true;
		}
		return false;
	}

	public static ArrayList<Item> loadItems(String[] split)
			throws SlickException {
		ArrayList<Item> e = new ArrayList<Item>(0);
		for (int i = 0; i < split.length; i++) {
			if (!split[i].equals("null")) {
				e.add(Item.generateItemFromString(split[i].split("-")));
			}
		}
		return e;
	}

	@Override
	protected String getGenericSave() {
		return super.getGenericSave() + ":"
				+ getInventoryAsString(this.inventory, this.inventorySize);
	}

	public void loadGenericSave(GameBoard board, String[] args,
			InteractiveEntity e) throws SlickException {
		super.loadGenericSave(board, args, e);
		e.inventory.addAll(loadItems(args[4].split(",")));
	}

	public static String getInventoryAsString(ArrayList<? extends Item> inv,
			int maxLen) {
		String toRet = "";
		if (maxLen == -1) {
			maxLen = inv.size();
		}
		for (int i = 0; i < maxLen; i++) {
			if (i != 0) {
				toRet += ",";
			}
			if (inv.size() > i && inv.get(i) != null) {
				toRet += inv.get(i).toItemString();
			} else {
				toRet += "null";
			}
		}
		return toRet;
	}

}
