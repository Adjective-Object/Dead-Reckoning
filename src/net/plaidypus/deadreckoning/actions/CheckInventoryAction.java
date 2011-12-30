package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.state.CheckInventoryState;
import net.plaidypus.deadreckoning.state.GameplayState;

public class CheckInventoryAction extends Action{

	public CheckInventoryAction(Tile source, Tile target) {
		super(source, target);
	}

	@Override
	protected boolean apply(int delta) {
		InteractiveEntity po = (InteractiveEntity)(source.getEntity());
		CheckInventoryState.makeFrom(GameplayState.getImage(),po.getInventory());
		DeadReckoningGame.instance.enterState(DeadReckoningGame.INVENTORYSTATE);
		return true;
	}

}
