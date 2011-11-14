package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.particles.DamageParticle;
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
		
		try{ return applyToEntity((LivingEntity)target.getEntity()); }
		catch(Exception e){return applyToEntity(target.getEntity()); }
	}
	
	protected abstract boolean applyToEntity(Entity entity);
	
	protected abstract boolean applyToEntity(LivingEntity e);

}
