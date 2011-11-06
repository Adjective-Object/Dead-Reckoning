package net.plaidypus.deadreckoning;

import net.plaidypus.deadreckoning.state.GameplayState;
import net.plaidypus.deadreckoning.state.MainMenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DeadReckoningGame extends StateBasedGame
{
	
	public static final int MAINMENUSTATE	= 0;
	public static final int GAMEPLAYSTATE	= 1;
	
	public static final int tileSize = 32;
	public static final int animationRate = 120;
	
	DeadReckoningGame() throws SlickException
	{
		super("Dead Reckoning");
		
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.enterState(MAINMENUSTATE);
	}
	
	public static void main(String[] args) throws SlickException
	{
		
		try{
			AppGameContainer app = new AppGameContainer(new DeadReckoningGame());
				app.setDisplayMode(800, 600, false);
				app.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		//Jeff, I don't know what this IS for, but you used it wrong. that is all.
	}
	
}
