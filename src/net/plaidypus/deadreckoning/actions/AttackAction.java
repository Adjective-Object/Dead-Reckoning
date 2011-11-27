package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.state.GameplayState;

public class AttackAction extends EntityTypeAction{
	
	int damage;
	boolean attacking;
	
	public AttackAction( Tile source, Tile target, int damage) {
		super(source, target);
		this.damage = damage;
		attacking=false;
	}
	
	protected boolean applyToEntity(Entity entity){return true;}
	protected boolean applyToEntity(InteractiveEntity e) {return true;}
	
	protected boolean applyToEntity(LivingEntity e){
		
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
			
			e.getParent().addEffectOver(new DamageEffect(target,Integer.toString(damage)));
			
			e.animating=true;
			s.animating=true;
			attacking=true;
		}

		return true;

	}
}
