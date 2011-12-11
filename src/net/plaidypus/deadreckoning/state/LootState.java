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

public class LootState extends BasicGameState{
	
	int stateID;
	
	static ItemGrid gridA, gridB;
	static Image background;
	static int selector;
	static boolean finished;
	
	Input input;
	
	public LootState(int stateID) {
		this.stateID = stateID;
		System.out.println(stateID);
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {	
		System.out.println("Initialzing LootState");
		input = gc.getInput();
		gridA=new ItemGrid(new Color(60,40,50),5,8,2,10);
		gridB=new ItemGrid(new Color(60,40,50),5,8,2,10);
		ItemGrid.init();
		finished=false;
	}
	
	public static void makeFrom(Image background, ArrayList<Item> inventoryA, ArrayList<Item> inventoryB){
		LootState.background = background;
		LootState.gridA.setContents(inventoryA);
		LootState.gridB.setContents(inventoryB);
		selector = 0;
	}
	
	public static boolean isFinished(){
		if(finished){
			finished=false;
			return true;
		}
		return false;
	}
	
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(background,0,0);
		gridA.render(g,gc.getWidth()/2+DeadReckoningGame.tileSize/2,gc.getHeight()/2-gridA.getHeight()/2);
		gridB.render(g,gc.getWidth()/2-DeadReckoningGame.tileSize/2-gridB.getWidth(),gc.getHeight()/2-gridB.getHeight()/2);
	}
	
	public int getID() {
		return 0;
	}

}
