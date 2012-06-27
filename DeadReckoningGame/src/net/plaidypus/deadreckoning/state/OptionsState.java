package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.SlickException;

public class OptionsState extends PrebakedHudLayersState{

	public OptionsState(int stateID, ArrayList<HudElement> elements)
			throws SlickException {
		super(stateID, elements);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeFrom(Object O) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		return new ArrayList<HudElement>(0);
	}

}
