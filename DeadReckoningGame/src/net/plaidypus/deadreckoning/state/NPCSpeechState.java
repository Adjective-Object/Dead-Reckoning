package net.plaidypus.deadreckoning.state;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.NPC;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.hudelements.simple.YNSelector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class NPCSpeechState extends PrebakedHudLayersState{

	public NPC controllingNPC;
	public TextElement NPCText;
	public YNSelector selector;
	
	public NPCSpeechState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}
	
	public void makeFrom(NPC n){
		this.controllingNPC = n;
		this.controllingNPC.advanceChat(this);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(		container.getInput().isKeyPressed(KeyEvent.VK_ENTER) ||
				container.getInput().isKeyPressed(KeyEvent.VK_SPACE) ||
				container.getInput().isKeyPressed(KeyEvent.VK_Z)	){
			this.controllingNPC.advanceChat(this);
		}
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> newCont = new ArrayList<HudElement>(0);
		
		NPCText = new TextElement(50,-200,HudElement.BOTTOM_LEFT,"",DeadReckoningGame.menuTextColor,DeadReckoningGame.menuDispFont);
		newCont.add(NPCText);
		
		selector = new YNSelector();
		selector.isVisible=false;
		newCont.add(selector);
		
		Panel p = new Panel(newCont);
		ArrayList<HudElement> newCont2 = new ArrayList<HudElement>(0);newCont2.add(p);
		
		return newCont2;
	}
	

}
