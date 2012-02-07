package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

public class CorpseObject extends InteractiveEntity{
	Entity containedEntity;
	
	public CorpseObject(Tile targetTile, int layer){
		super(targetTile, layer);
	}
	
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	public Action chooseAction(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void forceRender(Graphics g, float x, float y) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateBoardEffects(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isInteractive() {
		return false;
	}
	
	public ArrayList<Action> advanceTurn() {
		return null;
	}
	
	public Entity makeFromString(GameBoard target, String[] attributes) {
		String[] makeEntity = {attributes[0], attributes[1], attributes[]};
		return new CorpseObject();
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDeath() {
		
	}
}
