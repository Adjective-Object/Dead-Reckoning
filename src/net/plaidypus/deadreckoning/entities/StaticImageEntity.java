package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class StaticImageEntity extends Entity{

	Image draw;
	
	public StaticImageEntity(){}
	
	public StaticImageEntity(Tile t, int layer,Image drawImage){
		super(t,layer);
		draw=drawImage;
	}
	
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(draw,x,y);
	}

}
