package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stair extends StaticImageEntity{
	
	public static final int UP = 0, DOWN = 1, NULL = 2;
	public String targetFloor;
	static ArrayList<Image> stairImages;
	public int updownNull;
	
	public Stair(Tile target,int layer, String targetFloor, int updownNull){
		super(target,layer,stairImages.get(updownNull));
		this.targetFloor = targetFloor;
		this.updownNull=updownNull;
	}
	
	public Stair() {}

	@Override
	public void init() throws SlickException {
		stairImages = new ArrayList<Image>(0);
		stairImages.add(new Image("res/stairs.png"));
		stairImages.add(new Image("res/stairsDown.png"));
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {
		if(!this.getLocation().isOpen(Tile.LAYER_ACTIVE) && this.getLocation().getEntity(Tile.LAYER_ACTIVE).getClass()==Player.class){
			this.getParent().getGame().setBoard(Save.loadGame(this.getParent().getGame(),this.getParent().getSaveID(),targetFloor) );
		}
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
				Integer.parseInt(attributes[3]),attributes[4],Integer.parseInt(attributes[5]));
	}

	@Override
	public String saveToString() {
		return this.getGenericSave()+":"+targetFloor+":"+updownNull;
	}

	@Override
	public void onDeath() {}

}
