package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Torch extends Entity{
	
	int light;
	static SpriteSheet img;
	Animation ani;
	
	//Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	public Torch(){} 
	
	public Torch(Tile t, int layer, int areaofLight){
		super(t, layer);
		this.light = areaofLight;
		ani = new Animation(img , new int []{0,0,1,0,2,0,3,0}, new int[] {60,60,60,60});
	}
	
	public void init() throws SlickException{
		img = new SpriteSheet("res/torch.png", 32,32);
	}
	
	public void update(GameContainer gc, int delta) {
		this.getParent().lightInRadius(getLocation(), this.light+Utilities.randFloat()/5);
		this.ani.update(delta);
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
		this.getParent().lightInRadius(getLocation(), this.light+Utilities.randFloat()/5);
	}
	
	public Action chooseAction(GameContainer gc, int delta) {return null;}

	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(ani.getCurrentFrame(),x,y);
	}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return new Torch(g.getTileAt(Integer.parseInt(toload[1]),Integer.parseInt(toload[2])),Integer.parseInt(toload[3]),Integer.parseInt(toload[4]));
	}
	
	@Override
	public String saveToString() {
		return this.getGenericSave()+":"+this.light;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

	@Override
	public void onDeath() {}
}
