package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class LootAction extends EntityTypeAction{

	public LootAction(Tile source, Tile target) {
		super(source, target);
	}

	protected boolean apply(int delta) {
		if(target.getX()>source.getX()){
			source.getEntity().setFacing(true);
		}
		else if(target.getX()<source.getX()){
			source.getEntity().setFacing(false);
		}
		
		try{ return applyToEntity((LivingEntity)target.getEntity()); }
		catch(Exception e){return applyToEntity(target.getEntity()); }
	}
	
	protected boolean applyToEntity(Entity entity){return true;}
	
	protected boolean applyToEntity(LivingEntity e){
		if(e.isAlive()){
			
		}
		else{
			
		}
		return true;
	}

}
