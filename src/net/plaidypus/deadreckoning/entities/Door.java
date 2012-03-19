package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Door extends Entity{
	
	boolean open;
	
	static Image openImg,closeImg;
	
	public Door(){super();}
	
	public Door(Tile t, int layer) {
		super(t,layer);
		open=false;
	}
	
	@Override
	public void init() throws SlickException {
		openImg = new Image("res/doorOpen.png");
		closeImg = new Image("res/doorClosed.png");
	}

	@Override
	public void update(GameContainer gc, int delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		if(open){
			g.drawImage(openImg, x, y);
		}
		else{
			g.drawImage(closeImg, x, y);
		}
	}
	
	@Override
	public boolean isTransparent(){
		return this.open;
	}
	
	@Override
	public boolean isInteractive() {return false;}

	@Override
	public ArrayList<Action> advanceTurn() {return null;}

	@Override
	public Entity makeFromString(GameBoard target, String[] toload) {
		return new Door(target.getTileAt(Integer.parseInt(toload[1]),Integer.parseInt(toload[2])),Integer.parseInt(toload[3]));
	}

	@Override
	public String saveToString() {
		return this.getGenericSave();
	}

	@Override
	public void onDeath() {}

	@Override
	public Action onInteract(Entity e) {
		this.open = !open;
		Tile t = this.getLocation();
		t.getParent().removeEntity(this);
		if(open){
			t.getParent().placeEntity(t, this, Tile.LAYER_PASSIVE_MAP);
		}
		else{
			t.getParent().placeEntity(t, this, Tile.LAYER_ACTIVE);
		}
		return null;
	}

}
