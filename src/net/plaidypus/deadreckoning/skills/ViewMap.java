package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeStateAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class ViewMap extends Skill{
	
	public ViewMap(){super();}
	
	public ViewMap(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new ChangeStateAction(source,target,DeadReckoningGame.MAPSTATE);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return t==source.getLocation();
	}
	
	public boolean isInstant(){
		return true;
	}
	
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 0);
	}

}
