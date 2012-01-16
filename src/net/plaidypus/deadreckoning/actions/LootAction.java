package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;


import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;
import net.plaidypus.deadreckoning.items.Item;

public class LootAction extends EntityTypeAction{

	public LootAction(Tile source, Tile target) {
		super(source, target);
	}
	
	protected boolean applyToEntity(Entity entity){
		return true;
	}
	
	protected boolean applyToEntity(InteractiveEntity e){
		System.out.println(e.getInventory());
		return gotoLootScreen(((InteractiveEntity)source.getEntity()),e);
	}
	
	protected boolean applyToEntity(LivingEntity e){
		if(e.isAlive()){
			return true;
		}
		else{
			return gotoLootScreen(((InteractiveEntity)source.getEntity()),e);
		}
	}
	
	private boolean gotoLootScreen(InteractiveEntity a, InteractiveEntity b){
		DeadReckoningGame.instance.getHudState(DeadReckoningGame.LOOTSTATE).makeFrom(new Object[] {GameplayElement.getImage(),a,b,null,null} );
		DeadReckoningGame.instance.enterState(DeadReckoningGame.LOOTSTATE);
		return true;
	}

}
