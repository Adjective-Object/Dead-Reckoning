package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.grideffects.MoveEntityEffect;

public class MoveAction extends Action{
	
	GridEffect moveEffect;
	
	public MoveAction(Tile source, Tile destination){
		super(source,destination);
		moveEffect = new MoveEntityEffect(source, destination);
		source.getParent().addEffectOver(source,moveEffect);
	}
	
	public boolean apply(int delta) {
		if(moveEffect.isComplete()){
			return true;
		}
		return false;
	}
	
}
