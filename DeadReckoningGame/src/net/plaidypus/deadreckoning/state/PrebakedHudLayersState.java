package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class PrebakedHudLayersState extends HudLayersState{

	public PrebakedHudLayersState(int stateID, ArrayList<HudElement> background) throws SlickException{
		super(stateID, new ArrayList<HudElement>(0));
		contents.addAll(background);
		contents.addAll(makeContents());
	}
	
	protected abstract ArrayList<HudElement> makeContents() throws SlickException;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		for(int i=0; i<this.contents.size(); i++){
			this.contents.get(i).onEnter(container, game);
		}
	}
	
	
}
