package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.particles.DamageParticle;
import net.plaidypus.deadreckoning.state.GameplayState;

public class AttackAction extends Action{
	
	int damage;
	boolean attacking;
	
	public AttackAction( Tile source, Tile target, int damage) {
		super(source, target);
		this.damage = damage;
		attacking=false;
	}
	
	protected boolean apply(int delta) {
		if(target.getX()>source.getX()){
			source.getEntity().setFacing(true);
		}
		else if(target.getX()<source.getX()){
			source.getEntity().setFacing(false);
		}
		
		GameplayState.spawnParticle(new DamageParticle(target,Integer.toString(damage)));
		
		try{ return applyToEntity((LivingEntity)target.getEntity()); }
		catch(Exception e){return applyToEntity(target.getEntity()); }
	}
	
	private boolean applyToEntity(Entity entity){return true;}
	
	private boolean applyToEntity(LivingEntity e){
		
		if(!attacking){
			LivingEntity s = (LivingEntity) source.getEntity();
			s.setCurrentAnimation(LivingEntity.ANIMATION_ATTACK);
			e.damagePhysical(damage);
			
			int xdiff = source.getX()-target.getX();
			int ydiff = source.getY()-target.getY();
			
			if((xdiff<0 ^ e.getFacing()) || (xdiff==0 && ydiff>0)){
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
			}
			else{
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
			}
			e.animating=true;
			s.animating=true;
			attacking=true;
		}

		return true;

	}

}
