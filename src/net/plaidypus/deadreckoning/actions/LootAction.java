package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;

public class LootAction extends EntityTypeAction { // TODO make it so you can
													// loot entities on other
													// layers?

	public LootAction(Entity source, Tile target, int targetLayer) {
		super(source, target, targetLayer);
	}

	protected boolean applyToEntity(Entity entity) {
		DeadReckoningGame.instance.getMessageElement().addMessage(
				"That's Not Allowed");
		this.takesTurn = false;
		return true;
	}

	protected boolean applyToEntity(InteractiveEntity e) {
		DeadReckoningGame.instance.getMessageElement().addMessage(
				source.getName() + " looted "
						+ target.getEntity(this.layer).getName());
		return gotoLootScreen(((InteractiveEntity) source), e);
	}

	protected boolean applyToEntity(LivingEntity e) {
		if (e.isAlive()) {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					"That's Not Allowed");
			this.takesTurn = false;
			return true;
		} else {
			DeadReckoningGame.instance.getMessageElement().addMessage(
					source.getName() + " looted "
							+ target.getEntity(this.layer).getName());
			return gotoLootScreen(((InteractiveEntity) source), e);
		}
	}

	private boolean gotoLootScreen(InteractiveEntity a, InteractiveEntity b) {
		DeadReckoningGame.instance.getHudState(DeadReckoningGame.LOOTSTATE)
				.makeFrom(
						new Object[] { GameplayElement.getImage(), null, a, b,
								null, null });
		DeadReckoningGame.instance.enterState(DeadReckoningGame.LOOTSTATE);
		return true;
	}

	@Override
	protected boolean isNoticed() {
		return true;
	}

}
