package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;

public class WaitAction extends Action{

	public WaitAction(Tile source) {
		super(source, source);
		// TODO Auto-generated constructor stub
	}


	protected boolean apply(int delta) {
		return true;
	}


	public String getMessage() {
		return source.getEntity().getName()+" is useless";
	}

}
