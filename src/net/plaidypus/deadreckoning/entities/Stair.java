package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stair extends StaticImageEntity{
	
	String targetFloor;
	static Image stairImage;
	
	public Stair(Tile target,int layer, String targetFloor){
		super(target,layer,stairImage);
		this.targetFloor = targetFloor;
	}
	
	public Stair() {super();}

	@Override
	public void init() throws SlickException {
		this.stairImage=new Image("res/statue.png");
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
	public boolean isInteractive() {return false;}

	@Override
	public ArrayList<Action> advanceTurn() {return null;}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		return new Stair(target.getTileAt(Integer.parseInt(attributes[1]),Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]),attributes[4] );
	}

	@Override
	public String saveToString() {
		return this.getGenericSave()+":"+targetFloor;
	}

	@Override
	public void onDeath() {}

}
