package net.plaidypus.deadreckoning.hudelements.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.HudElementContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class Panel.
 */
public class Panel extends SimplePanel implements HudElementContainer{

	/** The contents. */
	protected ArrayList<HudElement> contents;

	/**
	 * Instantiates a new panel.
	 * 
	 * @param contents
	 *            the contents
	 */
	public Panel(ArrayList<HudElement> contents) {
		super(calcX(contents),calcY(contents), 0,0,HudElement.TOP_LEFT);
		this.borderX = 15;
		this.borderY = 15;
		this.contents = contents;
		
		for(int i=0; i<contents.size(); i++){
			contents.get(i).xoff-=this.getAbsoluteX();
			contents.get(i).yoff-=this.getAbsoluteY();
			
			contents.get(i).setContainer(this);
		}
		
		bakeBorders();
		
	}

	private static int calcX(ArrayList<HudElement> contents) {
		int x = DeadReckoningGame.instance.getContainer().getWidth();//TODO containers
		for(int i=0; i<contents.size(); i++){
			if(x>contents.get(i).getAbsoluteX()){x=contents.get(i).getAbsoluteX();}
		}
		return x;
	}

	private static int calcY(ArrayList<HudElement> contents) {
		int y = DeadReckoningGame.instance.getContainer().getHeight();
		for(int i=0; i<contents.size(); i++){
			if(y>contents.get(i).getAbsoluteY()){y=contents.get(i).getAbsoluteY();}
		}
		return y;
	}

	/**
	 * Instantiates a new panel.
	 * 
	 * @param bindMethod
	 *            the bind method
	 * @param needFoc
	 *            the need foc
	 * @param contents
	 *            the contents
	 * @param borderX
	 *            the border x
	 * @param borderY
	 *            the border y
	 */
	public Panel(int x, int y, int bindMethod,
			ArrayList<HudElement> contents, int borderX, int borderY) {
		super(x,y, 0,0,bindMethod);
		this.borderX = borderX;
		this.borderY = borderY;
		this.contents = contents;
		
		for (int i=0; i<contents.size() ; i++){
			contents.get(i).setContainer(this);
		}
		
		bakeBorders();
	}

	/**
	 * Bake borders.
	 */
	public void bakeBorders() {
		for (int i = 0; i < contents.size(); i++) {
			int nw = contents.get(i).getAbsoluteX() + contents.get(i).getWidth();
			int nh = contents.get(i).getAbsoluteY() + contents.get(i).getHeight();

			if (nw > this.getAbsoluteX()+width) {
				width = nw-this.getAbsoluteX();
			}
			if (nh > this.getAbsoluteY()+height) {
				height = nh-this.getAbsoluteY();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame,
	 * org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		
		for (int i = 0; i < contents.size(); i++) {
			contents.get(i).render(gc, sbg, g);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#renderMouseOver(org
	 * .newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame,
	 * org.newdawn.slick.Graphics)
	 */
	public void renderMouseOver(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for (int i = 0; i < contents.size(); i++) {
			contents.get(i).renderMouseOver(gc, sbg, g);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for (int i = 0; i < contents.size(); i++) {
			this.contents.get(i).update(gc, sbg, delta);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void makeFrom(Object o) {
		Collection<HudElement> c = (Collection<HudElement>) o;
		Iterator<HudElement> i = c.iterator();
		int p = 0;
		while (i.hasNext() && p < contents.size()) {
			this.contents.get(p).makeFrom(i.next());
			p++;
		}
		bakeBorders();
	}

	/**
	 * Make from.
	 * 
	 * @param o
	 *            the o
	 */
	public void makeFrom(Object[] o) {
		for (int i = 0; i < contents.size(); i++) {
			this.contents.get(i).makeFrom(o[i]);
		}
		bakeBorders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		for (int i = 0; i < contents.size(); i++) {
			contents.get(i).init(gc, sbg);
		}
	}

	/**
	 * Gets the contents.
	 * 
	 * @return the contents
	 */
	public ArrayList<HudElement> getContents() {
		return this.contents;
	}
	
	public void addElement(HudElement e){
		this.contents.add(e);
		e.setContainer(this);
		this.bakeBorders();
	}
	
	public void addAllElements(ArrayList<? extends HudElement> e){
		for(int i=0; i<e.size(); i++){
			this.contents.add(e.get(i));
			e.get(i).setContainer(this);
		}
		this.bakeBorders();
	}
	
	public void clearElements(){
		for(int i=0; i<this.contents.size(); i++){
			this.contents.get(i).setContainer(HudElementContainer.defaultContainer);
		}
		this.contents.clear();
	}
}
