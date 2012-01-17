package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.entities.*;

public class PlaceWall extends Skill{

	public PlaceWall(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		target.setTileFace(Tile.TILE_WALL);
		return new ChangeMapAction(source.getLocation(),target,new Wall(target));
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen();
	}

	@Override
	public void highlightRange(GameBoard board) {
		for(int y=0; y<board.getWidth(); y++){
			for(int x=0; x<board.getWidth(); x++){
				board.highlightSquare(x, y);
			}
		}
	}
	
}
