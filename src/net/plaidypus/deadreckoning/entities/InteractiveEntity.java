package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class InteractiveEntity extends Entity{
	
	ArrayList<Item> inventory;
	int inventorySize = 5;
	
	public InteractiveEntity(Tile targetTile, int layer){
		this(targetTile, layer, new ArrayList<Item>(0));
	}
	
	public InteractiveEntity(Tile targetTile, int layer, ArrayList<Item> items){
		super(targetTile,layer);
		this.inventory=items;
	}
	
	public InteractiveEntity(String stringCode){super(stringCode);}

	@Override
	public abstract void update(GameContainer gc, int delta);
	
	public void updateBoardEffects(GameContainer gc, int delta){
		this.collapseInventory();
	}
	
	@Override
	public abstract Action chooseAction(GameContainer gc, int delta);

	@Override
	public abstract void forceRender(Graphics g, float x, float y);
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	public static ArrayList<Item> collapseItemArray(ArrayList<Item> in){
		for(int i=1; i<in.size(); i++){
			for(int x=0; x<i; x++){
				if(in.get(i).stacksWith(in.get(x))){
					in.set(i,in.get(i).combineWith(in.get(x)));
					in.remove(x);
				}
			}
		}
		return in;
	}
	
	public void collapseInventory(){//TODO this doesn't work at all.
		collapseItemArray(this.inventory);
	}
	
	/**
	 * @return if the item was successfully added to the inventory
	 */
	public boolean addItem(Item i){
		if(inventory.size()<=inventorySize){
			inventory.add(i);
			this.collapseInventory();
			return true;
		}
		return false;
	}
	
}
