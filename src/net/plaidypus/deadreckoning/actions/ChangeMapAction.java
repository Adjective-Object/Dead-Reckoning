package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class ChangeMapAction extends Action{
	
	Entity toWrite;
	
	public ChangeMapAction(Tile source, Tile target, Entity toOverWrite) {
		super(source, target);
		toWrite = toOverWrite;
	}

	protected boolean apply(int delta) {
		source.getParent().placeEntity(target, toWrite);
		return true;
	}
}
