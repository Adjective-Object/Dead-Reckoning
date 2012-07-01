package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public abstract class PrebakedHudLayersState extends HudLayersState{

	public PrebakedHudLayersState(int stateID, ArrayList<HudElement> background) throws SlickException{
		super(stateID, new ArrayList<HudElement>(0));
		contents.addAll(background);
		contents.addAll(makeContents());
	}
	
	protected abstract ArrayList<HudElement> makeContents() throws SlickException;
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
	}
	
	
}
