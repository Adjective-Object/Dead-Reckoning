package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.menuItems.FairyLights;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends HudLayersState {

	int stateID;
	
	Button startButton,optionsButton,creditsButton,quitButton ;

	public MainMenuState(int stateID) throws SlickException {
		super(stateID ,makeContents());
		int x=5;
		this.startButton = (Button) this.getElement(x);
		this.optionsButton = (Button) this.getElement(x+1);
		this.creditsButton = (Button) this.getElement(x+2);
		this.quitButton = (Button) this.getElement(x+3);
	}
	
	public static ArrayList<HudElement> makeContents() throws SlickException{
		System.out.println("Building MainMenuState");
		ArrayList<HudElement> elements = new ArrayList<HudElement> (0);

		elements.add( new StillImageElement(0,0,HudElement.TOP_LEFT,new Image("res/menu/background.png")));
		elements.add(new FairyLights(-50,-300,HudElement.BOTTOM_LEFT,850,250,40,new SpriteSheet(new Image("res/menu/particles.png"),50,50)));
		elements.add(new FairyLights(-50,-200,HudElement.BOTTOM_LEFT,850,150,80,new SpriteSheet(new Image("res/menu/particles.png"),50,50)));
		elements.add(new FairyLights(-50,-100,HudElement.BOTTOM_LEFT,850,100,120,new SpriteSheet(new Image("res/menu/particles.png"),50,50)));
		elements.add( new StillImageElement(150,450,HudElement.TOP_LEFT, new Image("res/menu/artBar.png")));
		
		elements.add( new ImageButton(154,198,HudElement.TOP_LEFT,new Image("res/menu/start.png"), new Image("res/menu/startHighlight.png")));
		elements.add( new ImageButton(154,253,HudElement.TOP_LEFT,new Image("res/menu/options.png"), new Image("res/menu/optionsHighlight.png")));
		elements.add( new ImageButton(404,198,HudElement.TOP_LEFT,new Image("res/menu/credits.png"), new Image("res/menu/creditsHighlight.png")));
		elements.add( new ImageButton(404,253,HudElement.TOP_LEFT,new Image("res/menu/quit.png"), new Image("res/menu/quitHighlight.png")));
		
		return elements;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		
		if(this.startButton.isPressed()){
			game.enterState(DeadReckoningGame.SAVESELECTSTATE);
		}
		if(this.quitButton.isPressed()){
			container.exit();
		}
	}

}
