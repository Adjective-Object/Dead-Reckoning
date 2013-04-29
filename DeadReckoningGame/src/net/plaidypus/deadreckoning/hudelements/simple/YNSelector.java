package net.plaidypus.deadreckoning.hudelements.simple;

import java.awt.event.KeyEvent;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



public class YNSelector extends HudElement{
	
	boolean selected = true;
	public YNSelector(){
		this(-200, -200);
	}
	
	public YNSelector(int x, int y){
		super(x,y,HudElement.BOTTOM_RIGHT,false);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(	gc.getInput().isKeyPressed(KeyEvent.VK_DOWN) || gc.getInput().isKeyPressed(KeyEvent.VK_UP)){
			System.out.println("god damn it");
			selected = !selected;
		}
	}

	@Override
	public void makeFrom(Object o) {
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
	}

	@Override
	public int getWidth() {
		return 150;
	}

	@Override
	public int getHeight() {
		return 150;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setFont(DeadReckoningGame.menuLargeFont);
		g.drawString("Yes",this.getAbsoluteX()+14,this.getAbsoluteY()+DeadReckoningGame.menuLargeFont.getLineHeight());
		g.drawString("No",this.getAbsoluteX()+14,this.getAbsoluteY()+DeadReckoningGame.menuLargeFont.getLineHeight()+100);
		if(selected){
			g.fillRect(this.getAbsoluteX()+2,this.getAbsoluteY()+DeadReckoningGame.menuLargeFont.getLineHeight(),10,DeadReckoningGame.menuLargeFont.getLineHeight());
		} else{
			g.fillRect(this.getAbsoluteX()+2,this.getAbsoluteY()+DeadReckoningGame.menuLargeFont.getLineHeight(),10,DeadReckoningGame.menuLargeFont.getLineHeight()+100);
		}
	}
		
}
