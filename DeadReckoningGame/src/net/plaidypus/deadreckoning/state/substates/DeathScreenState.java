package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

public class DeathScreenState extends PrebakedHudLayersState{
	
	String saveName;
	StillImageElement imagePanel;
	float alpha=255F;
	
	public DeathScreenState(int stateID, ArrayList<HudElement> background) throws SlickException {
		super(stateID, background );
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if(alpha>0.5F){
			alpha=alpha*0.1F/delta;
		}
		
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.imagePanel.makeFrom(DeadReckoningGame.instance.getGameElement().player.getProfession().getPortriat());
		float alpha=255F;
	}

	@Override
	public ArrayList<HudElement> makeContents() {
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
		imagePanel = new StillImageElement(0,0,HudElement.CENTER_CENTER);
		
		returnElements.add( new TextElement(-100, -100, HudElement.CENTER_CENTER, "GAME OVER",
				DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont));
		returnElements.add(imagePanel);
		return returnElements;
	}
	
}