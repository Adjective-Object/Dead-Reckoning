package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
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
	public LootAction(int sourceID, InteractiveEntity target) {
		super(sourceID, target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.Entity)
	 */
	protected boolean applyToEntity(Entity entity, int delta) {
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
	protected boolean applyToEntity(InteractiveEntity e, int delta) {
		DeadReckoningGame.instance.getMessageElement().addMessage(
				GameBoard.getEntity(this.sourceID).getName() + " looted "
						+ getTargetEntity().getName());
		return gotoLootScreen(((InteractiveEntity) GameBoard.getEntity(this.sourceID)), e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.actions.EntityTypeAction#applyToEntity(net
	 * .plaidypus.deadreckoning.entities.LivingEntity)
	 */
	protected boolean applyToEntity(LivingEntity e, int delta) {
		if (e.isAlive()) {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					"It's Still Alive.");
			this.takesTurn = false;
			return true;
		} else {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					GameBoard.getEntity(this.sourceID).getName() + " looted "
							+ getTargetEntity().getName());
			return gotoLootScreen(((InteractiveEntity) GameBoard.getEntity(this.sourceID)), e);
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
