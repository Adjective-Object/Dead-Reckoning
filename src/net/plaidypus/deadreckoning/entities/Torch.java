package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Torch extends Entity{
	
	int light;
	static Image img;
	
	public Torch(Tile t, int areaofLight){
		super(t);
		this.light = areaofLight;
		this.interactive=false;
	}
	
	public static void init() throws SlickException{
		img = new Image("res/torch.png");
	}
	
	public void update(GameContainer gc, int delta) {
		this.getParent().revealInRadius(getLocation(), this.light);
	}
	
	public Action chooseAction(GameContainer gc, int delta) {return null;}

	public void render(Graphics g, float x, float y) {
		g.drawImage(img,x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize);
	}
}
