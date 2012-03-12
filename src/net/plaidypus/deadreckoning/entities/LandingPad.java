package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class LandingPad extends Entity{
	
	public String fromFloor;
	
	public LandingPad(){}
	
	public LandingPad(Tile t, int layer, String fromFloor){
		super(t,layer);
		this.fromFloor=fromFloor;
	}
	
	@Override
	public void init() throws SlickException {
		// TODO Auto-generated method stub
		
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
		return new LandingPad(target.getTileAt(Integer.parseInt(attributes[1]),Integer.parseInt(attributes[2])), Integer.parseInt(attributes[3]),
				attributes[4]);
	}

	@Override
	public String saveToString() {
		return this.getGenericSave()+":"+fromFloor;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
