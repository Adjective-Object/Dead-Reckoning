package net.plaidypus.deadreckoning;

import net.plaidypus.deadreckoning.hudelements.*;
import net.plaidypus.deadreckoning.state.ExclusiveHudLayersState;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.state.MainMenuState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class DeadReckoningGame extends StateBasedGame
{
	
	public static final int MAINMENUSTATE	= 3;
	public static final int GAMEPLAYSTATE	= 2;
	public static final int LOOTSTATE		= 0;
	public static final int INVENTORYSTATE 	= 1;
	
	public static final int tileSize = 32;
	
	public static DeadReckoningGame instance;
	
	DeadReckoningGame() throws SlickException
	{
		super("Dead Reckoning");
		
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new HudLayersState(GAMEPLAYSTATE,new HudElement[] {
				new GameplayElement(0),
				new PlayerHudElement(10,10,HudElement.TOP_LEFT,0),
				new StatusTrackerElement(10,120,HudElement.TOP_LEFT,0),
				new StringPutter(0,0,HudElement.BOTTOM_LEFT,0,80)
				} ));
		this.addState(new ExclusiveHudLayersState(LOOTSTATE, new HudElement[] {
				new StillImageElement(0,0,HudElement.TOP_LEFT),
				new ItemGridElement(-241,-132,HudElement.CENTER_CENTER),
				new ItemGridElement(50,-132,HudElement.CENTER_CENTER),
				new ItemGridInteractionElement(1,2),
				new ReturnToGameElement()} ));
		this.addState(new HudLayersState(INVENTORYSTATE, new HudElement[] {
				new StillImageElement(0,0,HudElement.TOP_LEFT),
				new ItemGridElement(0,0, HudElement.CENTER_CENTER),
				new ReturnToGameElement()}));
		
		this.enterState(MAINMENUSTATE);
		
		DeadReckoningGame.instance=this;
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

	public HudLayersState getHudState(int id){
		return (HudLayersState)(getState(id));
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		HudElement.calculateOffsets(container);
	}
	
}
