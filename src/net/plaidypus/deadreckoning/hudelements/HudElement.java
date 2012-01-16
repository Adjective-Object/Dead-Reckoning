package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.state.HudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class HudElement {
	
	int xoff, yoff, bindMethod;
	boolean hasFocus;
	public boolean needsFocus;
	
	HudLayersState parentState;
	
	public static final int TOP_LEFT = 0, TOP_CENTER = 1, TOP_RIGHT = 2,
		CENTER_LEFT = 3, CENTER_CENTER = 4, CENTER_RIGHT = 5,
		BOTTOM_LEFT = 6, BOTTOM_CENTER = 7, BOTTOM_RIGHT = 8;
	
	static int[][] offsets;
	
	public HudElement(int x, int y, int bindMethod, boolean needFoc){
		this.xoff=x;
		this.yoff=y;
		this.bindMethod=bindMethod;
		this.needsFocus=needFoc;
	}
	
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException;
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta, boolean hasFocus) throws SlickException{
		this.setFocus(hasFocus);
		this.update(gc, sbg, delta);
	}
	
	public void setParent(HudLayersState parentState){
		this.parentState=parentState;
	}
	
	public abstract void makeFrom(Object o);
	
	public void setFocus(boolean b){
		this.hasFocus=b;
	}
	
	public static void calculateOffsets(GameContainer gc){
		offsets = new int[9][2];
		for(int i=0; i<3; i++){
			for(int p=0; p<3; p++){
				offsets[p+i*3][0]=(int) (gc.getWidth()/3.0*p);
				offsets[p+i*3][1]=(int) (gc.getHeight()/3.0*i);
			}
		}
	}
	
	public abstract void init(GameContainer gc, StateBasedGame sbg) throws SlickException;
	
	public abstract int getWidth();
	public abstract int getHeight();
	
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g);

	public int getX() {
		return xoff+offsets[bindMethod][0];
	}
	
	public int getY() {
		return yoff+offsets[bindMethod][1];
	}
	
	public HudLayersState getParent(){
		return this.parentState;
	}
	
}
