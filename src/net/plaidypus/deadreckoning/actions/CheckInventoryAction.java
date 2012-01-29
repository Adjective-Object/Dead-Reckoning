package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

public class CheckInventoryAction extends Action{

	public CheckInventoryAction(Tile source, Tile target) {
		super(source, target);
	}

	@Override
	protected boolean apply(int delta) {
		InteractiveEntity po = (InteractiveEntity)(source.getEntity());
		DeadReckoningGame.instance.getHudState(DeadReckoningGame.INVENTORYSTATE).makeFrom(new Object[] {GameplayElement.getImage(),po});
		DeadReckoningGame.instance.enterState(DeadReckoningGame.INVENTORYSTATE);
		return true;
	}

	@Override
	public String getMessage() {
		return source.getEntity().getName()+" is digging through his shit";
	}

}
