package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.KeyConfig;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

public class LootState extends PrebakedHudLayersState{
	
	public ItemGridElement gridA, gridB;
	
	public LootState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			if (gridA.hasFocus && gridA.selector < gridA.contents.size()) {
				gridB.contents.add(gridA.contents.remove(gridA.selector));
			} else if (gridB.hasFocus && gridB.selector < gridB.contents.size()) {
				gridA.contents.add(gridB.contents.remove(gridB.selector));
			}
		}
		if (container.getInput().isKeyPressed(Input.KEY_A)) {
			if (gridA.hasFocus) {
				gridA.contents.addAll(gridB.contents);
				gridB.contents.clear();
			} else if (gridB.hasFocus) {
				gridB.contents.addAll(gridA.contents);
				gridA.contents.clear();
			}
		}
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		gridA= new ItemGridElement(-241, -132,HudElement.CENTER_CENTER);
		gridB = new ItemGridElement(50, -132, HudElement.CENTER_CENTER);

		elements.add(gridA);
		elements.add(gridB);
		elements.add(new ReturnToGameElement(KeyConfig.LOOT));
		
		return elements;
	}

}
