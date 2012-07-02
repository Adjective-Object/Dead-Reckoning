package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class InteractiveEntity.
 */
public abstract class InteractiveEntity extends Entity {

	/** The inventory. */
	protected ArrayList<Item> inventory = new ArrayList<Item>(0) ;

	/** The inventory size. */
	int inventorySize = 5;

	/**
	 * Instantiates a new interactive entity.
	 */
	public InteractiveEntity() {
	}

	/**
	 * Instantiates a new interactive entity.
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 */
	public InteractiveEntity(Tile targetTile, int layer) {
		this(targetTile, layer, new ArrayList<Item>(0));
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
	public InteractiveEntity(Tile targetTile, int layer, ArrayList<Item> items) {
		super(targetTile, layer);
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
	public void updateBoardEffects(GameContainer gc, int delta) {
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
	 * Collapse item array.
	 * 
	 * @param in
	 *            the in
	 * @return the array list
	 */
	public static ArrayList<Item> collapseItemArray(ArrayList<Item> in) {
		for (int i = 1; i < in.size(); i++) {
			for (int x = 0; x < i; x++) {
				if (in.get(i).stacksWith(in.get(x))) {
					in.set(i, in.get(i).combineWith(in.get(x)));
					in.remove(x);
				}
			}
		}
		return in;
	}

	/**
	 * Collapse inventory.
	 */
	public void collapseInventory() {// TODO this doesn't work at all.
		collapseItemArray(this.inventory);
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
	
	protected void loadItems(LivingEntity e, String[] split){
		for(int i=0; i<split.length; i++){
			if(!split[i].equals("null")){
				e.inventory.add(Item.loadFromString(split[i].split(",")));
			}
		}
	}

	protected String getGenericSave(){
		String toRet = super.getGenericSave()+":";
		for(int i=0; i<this.inventorySize; i++){
			if(i!=0){toRet+=",";}
			if(this.inventory.size()>i){
				System.out.println(this.inventory.get(i));
				toRet+=this.inventory.get(i).toItemString();
			}
			else{
				toRet+="null";
			}
		}
		return toRet;
	}
	
	
}
