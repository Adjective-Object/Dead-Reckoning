package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.grideffects.GridEffect;

public class AttackAction extends EntityTypeAction {

	int damage;
	boolean attacking;
	boolean physical;
	GridEffect targetEffectTop, targetEffectBottom, sourceEffectTop,
			sourceEffectBottom;

	public AttackAction( Tile source, Tile target, int damage, boolean physical) {
		this(source,target,damage,physical,null,null,null,null);
	}

	public AttackAction( Tile source, Tile target, int damage, boolean physical,
			GridEffect sourceTopEffect, GridEffect sourceBottomEffect ,
			GridEffect targetTopEffect, GridEffect targetBottomEffect) {
		super(source, target);
		this.damage = damage;
		attacking=false;
		this.physical=physical;
		this.sourceEffectTop=sourceTopEffect;
		this.sourceEffectBottom=sourceBottomEffect;
		this.targetEffectTop=targetTopEffect;
		this.targetEffectBottom=targetBottomEffect;
	}

	protected boolean applyToEntity(Entity entity) {
		return true;
	}

	protected boolean applyToEntity(InteractiveEntity e) {
		return true;
	}

	protected boolean applyToEntity(LivingEntity e) {

		if (!attacking) {
			LivingEntity s = (LivingEntity) source.getEntity();
			s.setCurrentAnimation(LivingEntity.ANIMATION_ATTACK);
			e.damagePhysical(damage);

			int xdiff = source.getX() - target.getX();
			int ydiff = source.getY() - target.getY();

			if ((xdiff < 0 ^ e.getFacing()) || (xdiff == 0 && ydiff > 0)) {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
			} else {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
			}
			
			e.getParent().addEffectOver(source, this.sourceEffectTop);
			e.getParent().addEffectUnder(source, this.sourceEffectBottom);
			e.getParent().addEffectOver(target, this.targetEffectTop);
			e.getParent().addEffectUnder(target, this.targetEffectBottom);
			
			e.getParent().addEffectOver(
					new DamageEffect(target, Integer.toString(damage)));
			
			e.animating = true;
			s.animating = true;
			attacking = true;
		}

		return true;

	}
}
