package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.grideffects.GridEffect;

// TODO: Auto-generated Javadoc
/**
 * The Class AttackAction.
 */
public class AttackAction extends EntityTypeAction {

	/** The damage. */
	int damage;

	/** The animate source. */
	boolean animateSource;

	/** The physical. */
	boolean physical;

	/** The source effect bottom. */
	GridEffect targetEffectTop, targetEffectBottom, sourceEffectTop,
			sourceEffectBottom;

	/**
	 * Instantiates a new attack action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param damage
	 *            the damage
	 * @param physical
	 *            the physical
	 */
	public AttackAction(int sourceID, Tile target, int damage, boolean physical) {
		this(sourceID, target, damage, physical, true, null, null, null, null);
	}

	/**
	 * Instantiates a new attack action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param damage
	 *            the damage
	 * @param physical
	 *            the physical
	 * @param animateSource
	 *            the animate source
	 */
	public AttackAction(int sourceID, Tile target, int damage,
			boolean physical, boolean animateSource) {
		this(sourceID, target, damage, physical, animateSource, null, null, null,
				null);
	}

	/**
	 * Instantiates a new attack action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param damage
	 *            the damage
	 * @param physical
	 *            the physical
	 * @param animateSource
	 *            the animate source
	 * @param sourceTopEffect
	 *            the source top effect
	 * @param sourceBottomEffect
	 *            the source bottom effect
	 * @param targetTopEffect
	 *            the target top effect
	 * @param targetBottomEffect
	 *            the target bottom effect
	 */
	public AttackAction(int sourceID, Tile target, int damage,
			boolean physical, boolean animateSource,
			GridEffect sourceTopEffect, GridEffect sourceBottomEffect,
			GridEffect targetTopEffect, GridEffect targetBottomEffect) {
		super(sourceID, target, Tile.LAYER_ACTIVE);
		this.damage = damage;
		this.animateSource = animateSource;
		this.physical = physical;
		this.sourceEffectTop = sourceTopEffect;
		this.sourceEffectBottom = sourceBottomEffect;
		this.targetEffectTop = targetTopEffect;
		this.targetEffectBottom = targetBottomEffect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.Entity)
	 */
	protected boolean applyToEntity(Entity entity) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.InteractiveEntity)
	 */
	protected boolean applyToEntity(InteractiveEntity e) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	protected boolean applyToEntity(LivingEntity e) {

		LivingEntity s = (LivingEntity) GameBoard.getEntity(this.sourceID);
		if (animateSource && GameBoard.getEntity(this.sourceID).getLocation().canBeSeen()) {
			s.setCurrentAnimation(LivingEntity.ANIMATION_ATTACK);
		}
		if (physical) {
			damage = e.damagePhysical(damage);
		} else {
			damage = e.damageMagical(damage);
		}
		if (e.HP <= 0) {
			try {
				Player p = (Player) (s);
				p.addExp(e.calculateEXPValue());
			} catch (ClassCastException cce) {
			}

		}

		int xdiff = s.getX() - target.getX();
		int ydiff = s.getY() - target.getY();

		if (e.getLocation().canBeSeen()) {
			if ((xdiff < 0 ^ e.getFacing()) || (xdiff == 0 && ydiff > 0)) {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
			} else {
				e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
			}

			e.getParent().addEffectOver(s.getLocation(),
					this.sourceEffectTop);
			e.getParent().addEffectUnder(s.getLocation(),
					this.sourceEffectBottom);
			e.getParent().addEffectOver(target, this.targetEffectTop);
			e.getParent().addEffectUnder(target, this.targetEffectBottom);

			e.getParent().addEffectOver(
					new DamageEffect(target, Integer.toString(damage)));
		}

		if (damage > 0) {
			sendMessage(s.getName() + " attacked "
					+ target.getEntity(Tile.LAYER_ACTIVE).getName() + " for "
					+ damage + " damage");
		}

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return GameBoard.getEntity(this.sourceID).getLocation().canBeSeen() || target.canBeSeen();
	}
}
