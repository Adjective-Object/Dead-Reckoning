package net.plaidypus.deadreckoning.particles;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class DamageParticle extends Particle{
	
	static UnicodeFont font;
	static float gravity= (float) 0.2;
	static float fadeout= (float) 0.5;
	float x,y,xMove,yMove,visibility;
	String damage;
	
	public DamageParticle(int x, int y, String damage){
		super();
		this.x=x;
		this.y=y;
		this.xMove=0;
		this.yMove=-1;
		this.visibility=(float) 1.0;
		this.damage=damage;
	}
	
	public DamageParticle(Tile t, String damage){
		this (t.getX()*DeadReckoningGame.tileSize+Utilities.randInt(0, DeadReckoningGame.tileSize),t.getY()*DeadReckoningGame.tileSize+Utilities.randInt(0, DeadReckoningGame.tileSize),damage);
	}
	
	public static void init() throws SlickException{
		 font = new UnicodeFont( "res\\visitor.ttf" , 7 ,false, false);	 
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		visibility=visibility*fadeout;
		yMove+=gravity;
		x+=xMove;
		y+=yMove;
		
		if(this.visibility<=0.00001){
			this.toKill=true;
		}
		
	}

	public void render(Graphics g, float xOff, float yOff) {
		g.setColor(new Color(255,255,255,255*visibility));
		g.setFont(font);
		g.drawString(damage, x+xOff, y+yOff);
		g.fillRect(x+xOff, y+yOff,10,7);
		
	}
}
	
