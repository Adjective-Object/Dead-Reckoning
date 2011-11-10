package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Torch extends Entity{
	
	int light;
	static SpriteSheet img;
	Animation ani;
	
	public Torch(Tile t, int areaofLight){
		super(t);
		this.light = areaofLight;
		this.interactive=false;
		ani = new Animation(img , new int []{0,0,1,0,2,0,3,0}, new int[] {60,60,60,60});
	}
	
	public static void init() throws SlickException{
		img = new SpriteSheet("res/torch.png", 32,32);
	}
	
	public void update(GameContainer gc, int delta) {
		this.getParent().revealInRadius(getLocation(), this.light);
		this.ani.update(delta);
	}
	
	public Action chooseAction(GameContainer gc, int delta) {return null;}

	public void render(Graphics g, float x, float y) {
		g.drawImage(ani.getCurrentFrame(),x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize);
	}
}
