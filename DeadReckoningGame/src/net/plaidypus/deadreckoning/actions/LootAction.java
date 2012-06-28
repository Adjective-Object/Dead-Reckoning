package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.state.substates.LootState;

// TODO: Auto-generated Javadoc
/**
 * The Class LootAction.
 */
public class LootAction extends EntityTypeAction { // TODO make it so you can
													// loot entities on other
													// layers?

	/**
	 * Instantiates a new loot action.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param targetLayer
	 *            the target layer
	 */
	public LootAction(Entity source, Tile target, int targetLayer) {
		super(source, target, targetLayer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.Entity)
	 */
	protected boolean applyToEntity(Entity entity) {
		DeadReckoningGame.instance.getMessageElement().addMessage(
				"That has nothing to loot.");
		this.takesTurn = false;
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
		DeadReckoningGame.instance.getMessageElement().addMessage(
				source.getName() + " looted "
						+ target.getEntity(this.layer).getName());
		return gotoLootScreen(((InteractiveEntity) source), e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	protected boolean applyToEntity(LivingEntity e) {
		if (e.isAlive()) {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					"It's Still Alive.");
			this.takesTurn = false;
			return true;
		} else {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					source.getName() + " looted "
							+ target.getEntity(this.layer).getName());
			return gotoLootScreen(((InteractiveEntity) source), e);
		}
	}

	/**
	 * Goto loot screen.
	 * 
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return true, if successful
	 */
	private boolean gotoLootScreen(InteractiveEntity a, InteractiveEntity b) {
		LootState l = (LootState) DeadReckoningGame.instance.getState(DeadReckoningGame.LOOTSTATE);
		l.gridA.makeFrom(a);
		l.gridB.makeFrom(b);
		DeadReckoningGame.instance.enterState(DeadReckoningGame.LOOTSTATE);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return true;
	}

}
