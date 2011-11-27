package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.state.GameplayState;

public abstract class EntityTypeAction extends Action{

	public EntityTypeAction(Tile source, Tile target) {
		super(source, target);
	}

	protected boolean apply(int delta) {
		if(target.getX()>source.getX()){
			source.getEntity().setFacing(true);
		}
		else if(target.getX()<source.getX()){
			source.getEntity().setFacing(false);
		}
		
		try{ 
			return applyToEntity((LivingEntity)target.getEntity());
		}
		catch(ClassCastException e){
			try{ 
				return applyToEntity((InteractiveEntity)target.getEntity());
			}
			catch(ClassCastException y){
				return applyToEntity(target.getEntity());
			}
		}
	}
	
	protected abstract boolean applyToEntity(Entity entity);
	
	protected abstract boolean applyToEntity(LivingEntity e);
	
	protected abstract boolean applyToEntity(InteractiveEntity e);

}
