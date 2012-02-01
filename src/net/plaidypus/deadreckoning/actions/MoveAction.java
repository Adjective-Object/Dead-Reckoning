package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.grideffects.MoveEntityEffect;

public class MoveAction extends Action{
	
	GridEffect moveEffect;
	boolean animate;
	
	public MoveAction(Entity source, Tile destination, int layerOfInterest){
		super(source,destination);
		moveEffect = new MoveEntityEffect(source.getLocation(), layerOfInterest, destination);
		animate=false;
		if(destination.isVisible() || source.isVisible()){
			source.getParent().addEffectOver(source.getLocation(),moveEffect);
			animate=true;
		}
	}
	
	public boolean apply(int delta) {
		if( moveEffect.isComplete() || !animate){
			return true;
		}
		return false;
	}

	public String getMessage() {
		return null;
	}
	
}
