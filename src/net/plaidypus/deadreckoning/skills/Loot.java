package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Loot extends Skill{
	
	public Loot(){super();}
	
	public Loot(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		for(int i=0; i<Tile.numLayers; i++){
			if(!target.isOpen(i)){
				 return new LootAction(source,target,i);//TODO fix that shit. make it so I can loot corpses on the ground;
			}
		}
		return new WaitAction(source);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		if(t!=source.getLocation()){
			for(int i=0; i<Tile.numLayers; i++){
				if(!t.isOpen(i)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 1);
	}

}
