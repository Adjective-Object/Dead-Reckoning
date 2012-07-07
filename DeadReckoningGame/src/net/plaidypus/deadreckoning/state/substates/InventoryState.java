package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.config.KeyConfig;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.EquipGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

public class InventoryState extends PrebakedHudLayersState{

	ItemGridElement itemGrid;
	EquipGridElement equipGrid;
	
	public InventoryState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if(doubleClick){
			if(itemGrid.getClicked()!=null && itemGrid.getClicked().classification==Item.ITEM_EQUIP){
				itemGrid.addItem(equipGrid.target.equipItem((Equip) itemGrid.getClicked()));
				itemGrid.removeItem(itemGrid.getClicked());
				equipGrid.target.profession.resetStatBonuses();
			}
			//else if(equipGrid.getClicked()!=null)
		}
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> tp = new ArrayList<HudElement> (0);
		itemGrid = new ItemGridElement(-100, 0, HudElement.CENTER_CENTER);
		itemGrid.personalBindMethod=HudElement.CENTER_CENTER;
		equipGrid = new EquipGridElement(100, 0, HudElement.CENTER_CENTER);
		equipGrid.personalBindMethod=HudElement.CENTER_CENTER;
		tp.add(itemGrid);
		tp.add(equipGrid);
		tp.add(new ReturnToGameElement(KeyConfig.INVENTORY));
		return tp;
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		this.itemGrid.makeFrom(DeadReckoningGame.instance.getGameElement().player);
		this.equipGrid.makeFrom(DeadReckoningGame.instance.getGameElement().player);
	}


}
