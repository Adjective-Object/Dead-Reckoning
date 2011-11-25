package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Items.Item;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
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
		gotoLootScreen(((InteractiveEntity)source.getEntity()).getInventory(),((InteractiveEntity)target.getEntity()).getInventory());
		return true;
	}
	
	protected boolean applyToEntity(LivingEntity e){
		if(e.isAlive()){
			//TODO damage the entityofShutup
		}
		else{
			gotoLootScreen(((InteractiveEntity)source.getEntity()).getInventory(),((InteractiveEntity)target.getEntity()).getInventory());
		}
		return true;
	}
	
	private void gotoLootScreen(ArrayList<Item> inventoryA, ArrayList<Item> inventoryB){
		System.out.println("DEM LOOTACTION");
		LootState.makeFrom(GameplayState.getImage(),inventoryA, inventoryB);
	}

}
