package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.SaveGameAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.SlickException;

public class Saver extends Skill{
	
	public Saver(LivingEntity source){
		super(source);
	}
	
	@Override
	public Action makeAction(Tile target) {
		return new SaveGameAction(source, target);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isInstant() {
		return true;
	}
	
	@Override
	public void init() throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
}
