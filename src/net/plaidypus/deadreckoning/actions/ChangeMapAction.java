package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class ChangeMapAction extends Action{
	
	Entity toWrite;
	int layer;
	
	public ChangeMapAction(Entity source, Tile target, int layer, Entity toOverWrite) {
		super(source, target);
		toWrite = toOverWrite;
		this.layer = layer;
		takesTurn=false;
	}

	protected boolean apply(int delta) {
		source.getParent().placeEntity(target, toWrite, layer);
		return true;
	}

	@Override
	public String getMessage() {
		return null;
	}
}
