package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Skill {
	
	LivingEntity source;
	
	public Skill (LivingEntity source){
		this.source=source;
	}
	
	public abstract Action makeAction( Tile target);
	
	public abstract boolean canTargetTile(Tile t);
	
	public abstract void highlightRange (GameBoard board);
	
	public void highlightRange(GameBoard board, int range) {
		board.clearHighlightedSquares();
		for(int vy=0;vy<board.getHeight();vy++){
			for(int vx=0;vx<board.getWidth();vx++){
				if( Math.sqrt( Math.pow(source.getX()-vx,2) + Math.pow(source.getY()-vy,2) ) <= range && canTargetTile(board.getTileAt(vx,vy) )){
					board.getTileAt(vx, vy).setHighlighted(true);
				}
			}
		}
	}
}
