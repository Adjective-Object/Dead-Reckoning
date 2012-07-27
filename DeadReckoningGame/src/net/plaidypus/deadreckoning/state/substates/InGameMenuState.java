package net.plaidypus.deadreckoning.state.substates;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class InGameMenuState extends PrebakedHudLayersState{
	
	TextButton returnToGameButton, saveGameButton, quitToMenuButton, exitButton;
	
	public InGameMenuState(int stateID, ArrayList<HudElement> elements) throws SlickException {
		super(stateID, elements);
	}

	@Override
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
		
		returnToGameButton = new TextButton(0, -100, HudElement.CENTER_CENTER, "Return To Game", DeadReckoningGame.menuLargeFont);
		returnToGameButton.personalBindMethod = HudElement.CENTER_CENTER;
		saveGameButton = new TextButton(0, -50, HudElement.CENTER_CENTER, "Save Game", DeadReckoningGame.menuLargeFont);
		saveGameButton.personalBindMethod = HudElement.CENTER_CENTER;
		quitToMenuButton = new TextButton(0, 0, HudElement.CENTER_CENTER, "Quit To Menu", DeadReckoningGame.menuLargeFont);
		quitToMenuButton.personalBindMethod = HudElement.CENTER_CENTER;
		exitButton = new TextButton(0, 50, HudElement.CENTER_CENTER, "Exit Game", DeadReckoningGame.menuLargeFont);
		exitButton.personalBindMethod = HudElement.CENTER_CENTER;

		elements.add(returnToGameButton);
		elements.add(saveGameButton);
		elements.add(quitToMenuButton);
		elements.add(exitButton);
		elements.add(new ReturnToGameElement(Input.KEY_ESCAPE));
		
		return elements;
	}
	
	
}
