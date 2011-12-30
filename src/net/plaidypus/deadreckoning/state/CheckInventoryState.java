package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.ItemGrid;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CheckInventoryState extends BasicGameState{//TODO aplosticianicanal (Do WOrk)
	
	int stateID;
	
	static ItemGrid grid;
	Input input;
	static boolean finished;
	static Image background;
	
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
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(background,0,0);
		grid.render(g, gc.getWidth()/2-grid.getWidth()/2,gc.getHeight()/2-grid.getHeight()/2, true);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
		}
		grid.parseInput(input, gc.getWidth()/2, gc.getHeight()/2 ,true);
	}

	@Override
	public int getID() {
		return stateID;
	}

	public static void makeFrom(Image image, ArrayList<Item> inventory) {
		background=image;
		grid.setContents(inventory);
	}
	
	
	
}
