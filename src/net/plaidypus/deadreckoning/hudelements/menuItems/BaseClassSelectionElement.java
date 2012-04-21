package net.plaidypus.deadreckoning.hudelements.menuItems;

import java.io.File;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BaseClassSelectionElement extends HudElement{
	
	int currentClass=0;
	int numClasses;
	
	Image iconImage;
	
	public BaseClassSelectionElement(int x, int y, int bindMethod) throws SlickException{
		super(x,y,bindMethod,true);
		numClasses = enumerateClasses();
		iconImage = new Image("res/professions/"+currentClass+"/Portrait.png");
	}

	private int enumerateClasses() {
		int numClass = 0;
		File f = new File("res/professions/"+numClass);
		while(f.exists()){
			numClass++;
			f = new File("res/professions/"+numClass);
		}
		return numClass;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(this.hasFocus){
			if(gc.getInput().isKeyPressed(Input.KEY_LEFT)){
				currentClass = (currentClass+numClasses-1)%numClasses;
				iconImage = new Image("res/professions/"+currentClass+"/Portrait.png");
			}
			if(gc.getInput().isKeyPressed(Input.KEY_RIGHT)){
				currentClass = (currentClass+1)%numClasses;
				iconImage = new Image("res/professions/"+currentClass+"/Portrait.png");
			}
		}
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}
	
	public int getWidth() {
		return iconImage.getWidth();
	}

	@Override
	public int getHeight() {
		return iconImage.getHeight();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(iconImage,getX(),getY());
	}
	
}
