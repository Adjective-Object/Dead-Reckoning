package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.state.GameplayState;
import net.plaidypus.deadreckoning.state.LootState;

public class LootAction extends EntityTypeAction{

	public LootAction(Tile source, Tile target) {
		super(source, target);
	}
	
	protected boolean applyToEntity(Entity entity){
		return true;
	}
	
	protected boolean applyToEntity(InteractiveEntity e){
		System.out.println(e.getInventory());
		return gotoLootScreen(((InteractiveEntity)source.getEntity()).getInventory(),e.getInventory());
	}
	
	protected boolean applyToEntity(LivingEntity e){
		if(e.isAlive()){
			return true;
		}
		else{
			return gotoLootScreen(((InteractiveEntity)source.getEntity()).getInventory(),e.getInventory());
		}
	}
	
	private boolean gotoLootScreen(ArrayList<Item> inventoryA, ArrayList<Item> inventoryB){
		LootState.makeFrom(GameplayState.getImage(),inventoryA, inventoryB);
		System.out.println(inventoryA+"  "+inventoryB);
		DeadReckoningGame.instance.enterState(DeadReckoningGame.LOOTSTATE);
		return true;
	}

}
