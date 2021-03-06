package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DeathScreenState extends PrebakedHudLayersState{
	
	String saveName;
	StillImageElement imagePanel;
	TextButton quitButton;
	float alpha=255F;
	
	public DeathScreenState(int stateID, ArrayList<HudElement> background) throws SlickException {
		super(stateID, background );
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if(alpha>0.5F){
			alpha=alpha*0.1F/delta;
		}
		if(quitButton.isPressed()){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.MAINMENUSTATE);
		}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.imagePanel.makeFrom(DeadReckoningGame.instance.getGameElement().player.getProfession().getPortriat());
		float alpha=255F;
	}

	@Override
	public ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
		imagePanel = new StillImageElement(0,0,HudElement.CENTER_CENTER);
		imagePanel.personalBindMethod=HudElement.CENTER_CENTER;
		quitButton = new TextButton(0,50,HudElement.CENTER_CENTER,"Quit",DeadReckoningGame.menuLargeFont);
		quitButton.personalBindMethod=HudElement.CENTER_CENTER;
		returnElements.add( new TextElement(-100, -100, HudElement.CENTER_CENTER, "GAME OVER",
				DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont));
		returnElements.add(imagePanel);
		returnElements.add(quitButton);
		return returnElements;
	}
	
}
