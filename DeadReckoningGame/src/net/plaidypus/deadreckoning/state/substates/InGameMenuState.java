package net.plaidypus.deadreckoning.state.substates;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.save.Save;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

public class InGameMenuState extends PrebakedHudLayersState{
	
	TextButton returnToGameButton, saveGameButton, quitToMenuButton, exitButton;
	
	public InGameMenuState(int stateID, ArrayList<HudElement> elements) throws SlickException {
		super(stateID, elements);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		
		if(returnToGameButton.isPressed()){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
		}
		if(saveGameButton.isPressed()){
			try {
				DeadReckoningGame.instance.getGameElement().updateSave();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(quitToMenuButton.isPressed()){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.MAINMENUSTATE);
		}
		if(exitButton.isPressed()){
			container.exit();
		}
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		
		returnToGameButton = new TextButton(-60, -100, HudElement.CENTER_CENTER, "Return To Game", DeadReckoningGame.menuLargeFont);
		saveGameButton = new TextButton(-60, -50, HudElement.CENTER_CENTER, "Save Game", DeadReckoningGame.menuLargeFont);
		quitToMenuButton = new TextButton(-60, 0, HudElement.CENTER_CENTER, "Quit To Menu", DeadReckoningGame.menuLargeFont);
		exitButton = new TextButton(-60, 50, HudElement.CENTER_CENTER, "Exit Game", DeadReckoningGame.menuLargeFont);

		elements.add(returnToGameButton);
		elements.add(saveGameButton);
		elements.add(quitToMenuButton);
		elements.add(exitButton);
		elements.add(new ReturnToGameElement(Input.KEY_ESCAPE));
		
		return elements;
	}
	
	
}