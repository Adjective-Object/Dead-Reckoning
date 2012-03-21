package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.grideffects.MoveEntityEffect;

public class MoveAction extends Action{
	
	boolean animate;
	int layerOfInterest;
	
	public MoveAction(Entity source, Tile destination, int layerOfInterest){
		super(source,destination);
		animate=false;
		if(destination.isVisible() || source.isVisible()){
			animate=true;
		}
		this.layerOfInterest=layerOfInterest;
	}
	
	public boolean apply(int delta) {
		this.source.getParent().removeEntity(source);
		this.source.getParent().placeEntity(target, source, layerOfInterest);
		return true;
	}

	public String getMessage() {
		return null;
	}

	@Override
	protected boolean isNoticed() {return true;}
	
}
