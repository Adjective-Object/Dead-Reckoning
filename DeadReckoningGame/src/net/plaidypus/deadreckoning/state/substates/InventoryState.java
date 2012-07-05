package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.config.KeyConfig;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

public class InventoryState extends PrebakedHudLayersState{

	ItemGridElement itemGrid;
	
	public InventoryState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> tp = new ArrayList<HudElement> (0);
		itemGrid = new ItemGridElement(0, 0, HudElement.CENTER_CENTER);
		itemGrid.personalBindMethod=HudElement.CENTER_CENTER;
		tp.add(itemGrid);
		tp.add(new ReturnToGameElement(KeyConfig.INVENTORY));
		return tp;
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.itemGrid.makeFrom(DeadReckoningGame.instance.getGameElement().player);
	}


}
