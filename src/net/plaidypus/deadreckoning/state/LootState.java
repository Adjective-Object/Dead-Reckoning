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
	
	static ItemGrid gridB, gridA;
	static Image background;
	static boolean focus;
	static boolean finished;
	
	Input input;
	
	public LootState(int stateID) {
		this.stateID = stateID;
		System.out.println(stateID);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {	
		System.out.println("Initialzing LootState");
		input = gc.getInput();
		gridB=new ItemGrid(new Color(60,40,50),5,8,2,10);
		gridA=new ItemGrid(new Color(60,40,50),5,8,2,10);
		ItemGrid.init();
		finished=false;
	}
	
	public static void makeFrom(Image background, ArrayList<Item> inventoryA, ArrayList<Item> inventoryB){
		LootState.background = background;
		LootState.gridA.setContents(inventoryA);
		LootState.gridB.setContents(inventoryB);
		focus = true;
	}
	
	public static boolean isFinished(){
		if(finished){
			finished=false;
			return true;
		}
		return false;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
		}
		if(input.isKeyPressed(Input.KEY_TAB) || input.isKeyPressed(Input.KEY_Q)){
			focus = !focus;
		}
		gridB.parseInput(input, gc.getWidth()/2+DeadReckoningGame.tileSize/2,gc.getHeight()/2-gridB.getHeight()/2,focus);
		gridA.parseInput(input, gc.getWidth()/2-DeadReckoningGame.tileSize/2-gridA.getWidth(),gc.getHeight()/2-gridA.getHeight()/2,!focus);
		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(focus && gridB.getContents().size()<gridB.getWidth()*gridB.getHeight() && gridB.isValidSelected()){
				gridA.getContents().add(gridB.getSelected());
				gridB.clearSelected();
			}
			else if (gridA.getContents().size()<gridA.getWidth()*gridA.getHeight() && gridA.isValidSelected()){
				gridB.getContents().add(gridA.getSelected());
				gridA.clearSelected();
			}
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(background,0,0);
		gridB.render(g,gc.getWidth()/2+DeadReckoningGame.tileSize/2,gc.getHeight()/2-gridB.getHeight()/2,focus);
		gridA.render(g,gc.getWidth()/2-DeadReckoningGame.tileSize/2-gridA.getWidth(),gc.getHeight()/2-gridA.getHeight()/2,!focus);
	}
	
	public int getID() {
		return stateID;
	}

}
