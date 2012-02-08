package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class WaitAction extends Action{

	public WaitAction(Entity source) {
		super(source, source.getLocation());
		// TODO Auto-generated constructor stub
	}


	protected boolean apply(int delta) {
		return true;
	}


	public String getMessage() {
		return source.getName()+" is useless";
	}


	@Override
	protected boolean isNoticed() {return false;}

}
