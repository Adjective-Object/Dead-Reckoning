package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.StoreContents;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;
import net.plaidypus.deadreckoning.utilities.KeyConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StoreState extends PrebakedHudLayersState{
	
	public ItemGridElement gridA;
	
	public StoreContents storeGrid;
	
	public StoreState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, background);
	}
	
	public void makeFrom(ArrayList<Item> storeContents){
		this.storeGrid.contents = storeContents;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		
		if( HudLayersState.doubleClick || container.getInput().isKeyPressed(Input.KEY_ENTER) ){
			gridA.addItem(storeGrid.removeItem(storeGrid.getSelected()));
			gridA.contents = Item.collapseItemArray(gridA.contents);
		}
	}
	
	protected boolean hasGold(){
		int goldCost = storeGrid.getSelectedGoldPrice();
		int countedGold=0;
		for(int i=0; i<gridA.contents.size(); i++){
			if(gridA.contents.get(i).matches("core",2)){
				countedGold+=gridA.contents.get(i).stacks;
			}
		}
		return countedGold>=goldCost;
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		gridA= new ItemGridElement(-241, -132,HudElement.CENTER_CENTER);
		storeGrid = new StoreContents(50, -132, HudElement.CENTER_CENTER);

		elements.add(gridA);
		elements.add(storeGrid);
		elements.add(new ReturnToGameElement(KeyConfig.LOOT));
		
		return elements;
	}

}
