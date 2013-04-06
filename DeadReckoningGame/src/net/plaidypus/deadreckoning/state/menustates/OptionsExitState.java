package net.plaidypus.deadreckoning.state.menustates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsExitState extends PrebakedHudLayersState{
	
	TextElement shitsDone;
	
	public OptionsExitState(int stateID, ArrayList<HudElement> elements)
			throws SlickException {
		super(stateID, elements);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		
		if(container.getInput().isKeyPressed(Input.KEY_ENTER)){
			container.exit();
		}
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElm = new ArrayList<HudElement>(0);
		
		shitsDone = new TextElement(0, 0, HudElement.CENTER_CENTER, "The game will now exit. Press Enter to continue",
				DeadReckoningGame.menuTextColor, DeadReckoningGame.menuLargeFont);
		shitsDone.personalBindMethod=HudElement.CENTER_CENTER;
		returnElm.add(shitsDone);
		
		return returnElm;
	}

}
