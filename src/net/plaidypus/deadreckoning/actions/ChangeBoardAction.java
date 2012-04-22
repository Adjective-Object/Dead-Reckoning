package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class ChangeBoardAction extends Action{
	
	String targetFloor;
	
	public ChangeBoardAction(Entity source, String targetFloor) {
		super(source, source.getLocation());
		this.targetFloor=targetFloor;
	}

	@Override
	protected boolean isNoticed() {
		return false;
	}

	@Override
	protected boolean apply(int delta) {
		source.getParent().getGame().setBoard(Save.loadGame(source.getParent().getGame(),source.getParent().getSaveID(),targetFloor) );
		return true;
	}

}
