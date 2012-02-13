package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Stair extends Entity{

	public Stair(Tile target,int layer, String toMap){
		super(target,layer);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInteractive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
