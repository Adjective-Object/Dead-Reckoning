package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

public class CheckInventoryAction extends Action{
	
	public CheckInventoryAction(Entity source, Tile target) {
		super(source, target);
		takesTurn= false;
	}

	@Override
	protected boolean apply(int delta) {
		InteractiveEntity po = (InteractiveEntity)(source);
		DeadReckoningGame.instance.getHudState(DeadReckoningGame.INVENTORYSTATE).makeFrom(new Object[] {GameplayElement.getImage(),null,po});
		DeadReckoningGame.instance.enterState(DeadReckoningGame.INVENTORYSTATE);
		DeadReckoningGame.instance.messages.addMessage(source.getName()+" is digging through their shit");
		return true;
	}

}
