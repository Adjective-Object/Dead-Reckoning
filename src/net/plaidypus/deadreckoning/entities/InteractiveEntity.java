package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Items.Item;
import net.plaidypus.deadreckoning.actions.Action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class InteractiveEntity extends Entity{
	
	ArrayList<Item> inventory;
	int inventorySize = 5;
	
	public InteractiveEntity(Tile targetTile){
		this(targetTile,new ArrayList<Item>(0));
	}
	
	public InteractiveEntity(Tile targetTile, ArrayList<Item> items){
		super(targetTile);
		this.inventory=items;
		items.add(new Item(0));
	}
	
	@Override
	public abstract void update(GameContainer gc, int delta);

	@Override
	public abstract Action chooseAction(GameContainer gc, int delta);

	@Override
	public abstract void render(Graphics g, float x, float y);
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	/**
	 * @return if the item was successfully added to the inventory
	 */
	public boolean addItem(Item i){
		if(inventory.size()<=inventorySize){
			inventory.add(i);
			return true;
		}
		return false;
	}
}
