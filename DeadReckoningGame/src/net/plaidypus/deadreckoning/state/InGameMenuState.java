package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.hudelements.HudElement;

public class InGameMenuState extends PrebakedHudLayersState{

	public InGameMenuState(int stateID, ArrayList<HudElement> elements) throws SlickException {
		super(stateID, elements);
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		
		
		
		return elements;
	}
	
	@Override
	public void makeFrom(Object O) {	}

}
