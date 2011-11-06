package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class AttackAction extends Action{
	
	int damage;
	boolean attacking;
	
	public AttackAction( Tile source, Tile target, int damage) {
		super(source, target);
		this.damage = damage;
		attacking=false;
	}

	protected boolean apply(int delta) {
		try{ return applyToEntity((LivingEntity)target.getEntity()); }
		catch(Exception e){return applyToEntity(target.getEntity()); }
	}
	
	private boolean applyToEntity(Entity entity){return true;}
	
	private boolean applyToEntity(LivingEntity e){
		
		if(!attacking){
			e.setCurrentAnimation(LivingEntity.ANIMATION_ATTACK);
			e.HP-=damage;
			
			int xdiff = source.getX()-target.getX();
			int ydiff = source.getY()-target.getY();
			
			if(ydiff<xdiff*(Utilities.booleanPlusMin(e.getFacing()))){
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
			}
			else{
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
			}
			attacking=true;
		}

		if(e.currentAnimation.isStopped()){
			return true;
		}
		return false;

	}
	
	public int calculateRange(LivingEntity source) {
		return 1;
	}

}
