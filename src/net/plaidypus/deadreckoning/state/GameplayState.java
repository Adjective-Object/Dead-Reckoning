package net.plaidypus.deadreckoning.state;


import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.entities.Goblin;
import net.plaidypus.deadreckoning.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState
{
	
	int stateID = -1;
	
	GameBoard gb;
	
	public GameplayState(int stateID) throws SlickException
	{
		this.stateID = stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setVSync(true);
		gb = new GameBoard(25,25);
		gb.init();
		gb.placeEntity(0, 0, new Player(gc.getInput()));
		gb.placeEntity(24, 1, new Goblin());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gb.render(gc,sbg,g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		gb.updateAllTiles(gc,delta);
		if(!gb.isGameSet()){
			gb.chooseActions(gc,delta);
		}
		if(gb.isGameSet()){
			gb.applyActions(delta);
		}
	}

	public int getID() {
		return stateID;
	}
	
}
