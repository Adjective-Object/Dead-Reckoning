package net.plaidypus.deadreckoning.hudelements;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StringPutter extends HudElement{
	
	private ArrayList<Double> alphas;
	private ArrayList<String> messages;
	private int hookElement, fadeoutRate;
	
	public StringPutter(int x, int y, int bindMethod, int hookElement, int fadeoutRate) {
		super(x, y, bindMethod, false);
		this.hookElement=hookElement;
		this.fadeoutRate=fadeoutRate;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		alphas = new ArrayList<Double>(0);
		messages = new ArrayList<String>(0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for(int i=0; i<alphas.size(); i++){
			alphas.set(i,alphas.get(i)-fadeoutRate*(delta/1000.0));
			if(alphas.get(i)<=0){
				alphas.remove(i);
				messages.remove(i);
				i--;
			}
		}
	}
	
	public void addMessage(String string) {
		messages.add(string);
		alphas.add(255.0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for(int i=0; i< messages.size(); i++){
			g.setColor(new Color(255,255,255,alphas.get(i).intValue()));
			g.drawString(messages.get(i),this.getX() ,this.getY()-( (messages.size()-i)*25 ));
		}
	}
	
	@Override
	public void makeFrom(Object o) {}

	@Override
	public int getWidth() {return 0;}

	@Override
	public int getHeight() {return 0;}
	
	
}
