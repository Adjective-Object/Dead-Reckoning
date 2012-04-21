package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class ChangeBoardAction extends Action{
	
	String tileFloor;
	
	public ChangeBoardAction(Entity source, String tileFloor) {
		super(source, source.getLocation());
		this.tileFloor=tileFloor;
	}

	@Override
	protected boolean isNoticed() {
		return false;
	}

	@Override
	protected boolean apply(int delta) {
		source.getParent().getGame().setBoard(Save.loadGame(source.getParent().getGame(),source.getParent().getSaveID(),tileFloor) );
		return true;
	}

}
