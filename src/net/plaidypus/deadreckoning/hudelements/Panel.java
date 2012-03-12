package net.plaidypus.deadreckoning.hudelements;

import java.util.Collection;
import java.util.Iterator;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Panel extends HudElement{

	int borderX, borderY, width, height;
	HudElement[] contents;
	
	public Panel(HudElement[] contents) {
		this(HudElement.TOP_LEFT, true, contents, 10, 10);
	}
	
	public Panel(int bindMethod, boolean needFoc, HudElement[] contents, int borderX, int borderY) {
		super(contents[0].getX(), contents[0].getY() , bindMethod, needFoc);
		this.borderX=borderX;
		this.borderY=borderY;
		this.contents=contents;
		this.width = 0;
		this.height = 0;
		
		bakeBorders();
	}

	private void bakeBorders() {
		for(int i=1; i<contents.length ; i++){
			int nw = contents[i].getX()+contents[i].getWidth();
			int nh = contents[i].getY()+contents[i].getHeight();
			int nx = contents[i].getX();
			int ny = contents[i].getY();
			
			if(nw>width){ width = nw; }
			if(nh>height){ height = nh; }
			if(nx<xoff){ xoff = nx; }
			if(ny<yoff){ yoff = ny; }
		}
		
		width = width-xoff;
		height= height-yoff;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(DeadReckoningGame.menuColor);
		g.fillRect(getX()-borderX, getY()-borderY, getWidth()+borderX*2, getHeight()+borderY*2);
		g.setColor(Color.white);
		g.drawRect(getX()-borderX, getY()-borderY, getWidth()+borderX*2, getHeight()+borderY*2);
		
		for(int i=0; i<contents.length ; i++){
			contents[i].render(gc, sbg, g);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for(int i=0; i<contents.length; i++){
			this.contents[i].update(gc,sbg,delta);
		}
	}

	@Override
	public void makeFrom(Object o) {
		Collection<HudElement> c  = (Collection<HudElement>) o;
		Iterator<HudElement> i = c.iterator();
		int p = 0;
		while(i.hasNext() && p<contents.length){
			this.contents[p].makeFrom(i.next());
			p++;
		}
		
		bakeBorders();
	}
	
	public void makeFrom(Object[] o) {
		for(int i=0; i<contents.length; i++){
			this.contents[i].makeFrom(o[i]);
		}
		bakeBorders();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
}
