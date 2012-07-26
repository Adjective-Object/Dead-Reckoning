package net.plaidypus.deadreckoning.actions;

import org.newdawn.slick.util.Log;

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
	int damage, damageFrame, elapsedFrames;

	/** should the target and source be animated? */
	boolean animate;
	
	protected boolean effectsApplied = false;

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
	public AttackAction(int sourceID, LivingEntity target, int damage, boolean physical) {
		this(sourceID, target, damage, physical, true, 0);
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
	public AttackAction(int sourceID, LivingEntity target, int damage,
			boolean physical, boolean animate) {
		this(sourceID, target, damage, physical, animate, 0);
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
	public AttackAction(int sourceID, LivingEntity target, int damage,
			boolean physical, boolean animate, int damageFrame) {
		super(sourceID, target);
		this.damage = damage;
		this.animate = animate;
		this.physical = physical;
		this.damageFrame=damageFrame;
	}
	
	public void setGridEffects(GridEffect sourceTopEffect, GridEffect sourceBottomEffect,
			GridEffect targetTopEffect, GridEffect targetBottomEffect){
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
	protected boolean applyToEntity(Entity entity, int delta) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.InteractiveEntity)
	 */
	protected boolean applyToEntity(InteractiveEntity e, int delta) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	protected boolean applyToEntity(LivingEntity e, int delta) {
		elapsedFrames+=delta;
		
		if(elapsedFrames>=damageFrame){
			LivingEntity s = (LivingEntity) GameBoard.getEntity(this.sourceID);
			if(!effectsApplied){
				if (animate && GameBoard.getEntity(this.sourceID).getLocation().canBeSeen()) {
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
		
				int xdiff = s.getX() - getTargetEntity().getX();
				int ydiff = s.getY() - getTargetEntity().getY();
		
				if (e.getLocation().canBeSeen() && animate) {
					if ((xdiff < 0 ^ e.getFacing()) || (xdiff == 0 && ydiff > 0)) {
						e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_FRONT);
					} else {
						e.setCurrentAnimation(LivingEntity.ANIMATION_FLINCH_BACK);
					}
		
					e.getParent().addEffectOver(s.getLocation(),
							this.sourceEffectTop);
					e.getParent().addEffectUnder(s.getLocation(),
							this.sourceEffectBottom);
					e.getParent().addEffectOver(getTargetTile(), this.targetEffectTop);
					e.getParent().addEffectUnder(getTargetTile(), this.targetEffectBottom);
		
					
					if (damage > 0) {
						Log.info(s.getName() + " attacked "
								+ getTargetEntity().getName() + " for "
								+ damage + " damage");
						e.getParent().addEffectOver(
								new DamageEffect(getTargetTile(), Integer.toString(damage)));
					}
					else{
						Log.info(getTargetEntity().getName() +
								" dodged " + s.getName() +  "'s attack ");
						e.getParent().addEffectOver(
								new DamageEffect(getTargetTile(),"dodged"));
					}
				}
		
				effectsApplied=true;
			}
			else{
				if( (s.getCurrentAnimationID() == LivingEntity.ANIMATION_STAND  &&
						e.getCurrentAnimationID() == LivingEntity.ANIMATION_STAND) || !animate){
					return true;
				}
			}
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return GameBoard.getEntity(this.sourceID).getLocation().canBeSeen() || getTargetTile().canBeSeen();
	}
}
