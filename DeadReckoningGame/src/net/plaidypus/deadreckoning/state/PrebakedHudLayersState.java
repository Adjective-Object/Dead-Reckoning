package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public abstract class PrebakedHudLayersState extends HudLayersState{

	public PrebakedHudLayersState(int stateID, ArrayList<HudElement> elements) throws SlickException{
		super(stateID, elements);
		elements.addAll(makeContents());
	}
	
	public abstract void makeFrom(Object O);
	
	protected abstract ArrayList<HudElement> makeContents() throws SlickException;

}
