package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public class AnimationEffect extends GridEffect{
	public Animation animation;
	
	public AnimationEffect(Tile location, Animation a){
		super(location);
		this.animation=a;
		a.start();
	}
	
	public void update(int delta){
		this.animation.update(delta);
		if(animation.isStopped()){
			this.kill=true;
		}
	}
	
	public void render(Graphics g, float xoff, float yoff){
		g.drawImage(this.animation.getCurrentFrame(),location.getX()*DeadReckoningGame.tileSize+xoff, location.getY()*DeadReckoningGame.tileSize+yoff);
	}
}