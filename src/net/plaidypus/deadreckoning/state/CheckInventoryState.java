package net.plaidypus.deadreckoning.state;

import net.plaidypus.deadreckoning.ItemGrid;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CheckInventoryState extends BasicGameState{
	
	int stateID;
	
	ItemGrid grid;
	Input input;
	boolean finished;
	
	public CheckInventoryState(int stateID) throws SlickException {
		this.stateID=stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("Initialzing LootState");
		input = container.getInput();
		grid=new ItemGrid(new Color(60,40,50),5,8,2,10);
		ItemGrid.init();
		finished=false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	
	
}
