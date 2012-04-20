package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.grideffects.MoveEntityEffect;

public class MoveAction extends Action{
	
	boolean animate;
	int destLayer;
	
	public MoveAction(Entity source, Tile destination, int destLayer){
		super(source,destination);
		animate=false;
		if(destination.isVisible() || source.isVisible()){
			animate=true;
		}
		this.destLayer=destLayer;
	}
	
	public boolean apply(int delta) {
		source.getParent().moveEntity(source,target,destLayer);
		return true;
	}

	public String getMessage() {
		return null;
	}

	@Override
	protected boolean isNoticed() {return true;}
	
}
