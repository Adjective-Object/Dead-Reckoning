package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public class ExclusiveHudLayersState extends HudLayersState{
	
	int focus = 0;
	
	public ExclusiveHudLayersState(int stateID, ArrayList<HudElement> elements){
		super(stateID, elements);
	}
	
	public ExclusiveHudLayersState(int stateID, HudElement[] elements){
		super(stateID, elements);
	}
	
	public void makeFrom(Object[] objects){
		super.makeFrom(objects);
		focus=0;
		advanceFocus();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		if(container.getInput().isKeyPressed(Input.KEY_TAB)){
			advanceFocus();
		}
		for(int i=0; i<HudElements.size();i++){
			if(!HudElements.get(i).needsFocus || focus==i){
				HudElements.get(i).update(container,game,delta,true);
			}
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		super.render(container, game, g);
		g.setColor(Color.white);
		HudElement h = HudElements.get(focus);
		g.drawRect(h.getX()-1,h.getY()-1,h.getWidth()+1,h.getHeight()+1);
	}
	
	private void advanceFocus(){
		clearFoci();
		for(int i=0; i<this.HudElements.size(); i++){
			focus = (focus+1)%HudElements.size();
			if(HudElements.get(focus).needsFocus){
				HudElements.get(focus).setFocus(true);
				break;
			}
		}
	}
	
	private void clearFoci(){
		for(int i=0; i<this.HudElements.size(); i++){
			this.HudElements.get(i).setFocus(false);
		}
	}
	
}
