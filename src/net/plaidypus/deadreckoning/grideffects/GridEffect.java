package net.plaidypus.deadreckoning.grideffects;

import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.board.Tile;

public abstract class GridEffect {
	private boolean kill;
	public Tile location;
	
	public GridEffect(Tile location){
		this.location=location;
		this.kill=false;
	}
	
	public void setLocation(Tile l){
		this.location=l;
	}
	
	public boolean isComplete(){
		return this.kill;
	}
	
	public void setComplete(boolean comp){
		this.kill=comp;
	}
	
	public abstract void update(int delta);
	
	public abstract void render(Graphics g, float xoff, float yoff);
}
