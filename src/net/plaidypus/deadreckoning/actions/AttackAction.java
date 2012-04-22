package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.grideffects.GridEffect;

public class AttackAction extends EntityTypeAction {

	int damage;
	boolean animateSource;
	boolean physical;
	GridEffect targetEffectTop, targetEffectBottom, sourceEffectTop,
			sourceEffectBottom;

	public AttackAction(Entity source, Tile target, int damage, boolean physical) {
		this(source, target, damage, physical, true, null, null, null, null);
	}

	public AttackAction(Entity source, Tile target, int damage,
			boolean physical, boolean animateSource) {
		this(source, target, damage, physical, animateSource, null, null, null,
				null);
	}

	public AttackAction(Entity source, Tile target, int damage,
			boolean physical, boolean animateSource,
			GridEffect sourceTopEffect, GridEffect sourceBottomEffect,
			GridEffect targetTopEffect, GridEffect targetBottomEffect) {
		super(source, target, Tile.LAYER_ACTIVE);
		this.damage = damage;
		this.animateSource = animateSource;
		this.physical = physical;
		this.sourceEffectTop = sourceTopEffect;
		this.sourceEffectBottom = sourceBottomEffect;
		this.targetEffectTop = targetTopEffect;
		this.targetEffectBottom = targetBottomEffect;
	}

	protected boolean applyToEntity(Entity entity) {
		return true;
	}

	protected boolean applyToEntity(InteractiveEntity e) {
		return true;
	}

	protected boolean applyToEntity(LivingEntity e) {

		LivingEntity s = (LivingEntity) source;
		if (animateSource && source.getLocation().canBeSeen()) {
			s.setCurrentAnimation(LivingEntity.ANIMATION_ATTACK);
		}
		if (physical) {
			e.damagePhysical(damage);
		} else {
			e.damageMagical(damage);
		}
		if (e.HP <= 0) {
			try {
				Player p = (Player) (s);
				p.addExp(e.calculateEXPValue());
			} catch (ClassCastException cce) {
			}

		}

		int xdiff = source.getX() - target.getX();
		int ydiff = source.getY() - target.getY();

		if (e.getLocation().canBeSeen()) {
			if ((xdiff < 0 ^ e.getFacing()) || (xdiff == 0 && ydiff > 0)) {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
			} else {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
			}

			e.getParent().addEffectOver(source.getLocation(),
					this.sourceEffectTop);
			e.getParent().addEffectUnder(source.getLocation(),
					this.sourceEffectBottom);
			e.getParent().addEffectOver(target, this.targetEffectTop);
			e.getParent().addEffectUnder(target, this.targetEffectBottom);

			e.getParent().addEffectOver(
					new DamageEffect(target, Integer.toString(damage)));
		}

		if (damage > 0) {
			sendMessage(source.getName() + " attacked "
					+ target.getEntity(Tile.LAYER_ACTIVE).getName() + " for "
					+ damage + " damage");
		}

		return true;

	}

	@Override
	protected boolean isNoticed() {
		return source.getLocation().canBeSeen() || target.canBeSeen();
	}
}
