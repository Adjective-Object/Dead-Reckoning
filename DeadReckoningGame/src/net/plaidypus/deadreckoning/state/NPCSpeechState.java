package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.NPC;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.SlickException;

public class NPCSpeechState extends PrebakedHudLayersState{

	NPC controllingNPC;
	Text NPCText= 0;
	Selector = new YNSelector();
	
	public NPCSpeechState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}
	
	public void makeFrom(NPC n){
		this.controllingNPC = n;
		
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		
	}
	

}
