package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.*;

public class PlaceTorch extends Skill{
	
	public PlaceTorch(){super();}
	
	public PlaceTorch(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new ChangeMapAction(source,target,Tile.LAYER_PASSIVE_MAP,new Torch(target,Tile.LAYER_PASSIVE_MAP,Utilities.randInt(2, 5)));
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen(Tile.LAYER_ACTIVE);
	}

	@Override
	public void highlightRange(GameBoard board) {
		for(int y=0; y<board.getHeight(); y++){
			for(int x=0; x<board.getWidth(); x++){
				board.highlightSquare(x, y);
			}
		}
	}
	
}
