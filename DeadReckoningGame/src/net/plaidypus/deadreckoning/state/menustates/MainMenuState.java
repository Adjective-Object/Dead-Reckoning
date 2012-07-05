package net.plaidypus.deadreckoning.state.menustates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.menuItems.FairyLights;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The HudLayersState of the Main Menu.
 * 
 */
public class MainMenuState extends PrebakedHudLayersState {

	/** The state id. */
	int stateID;

	/** The quit button. */
	Button startButton, optionsButton, creditsButton, quitButton;

	/** The lights. */
	static FairyLights lights;

	/**
	 * Instantiates a new main menu state.
	 * 
	 * @param stateID
	 *            the state id
	 * @param background
	 *            the background
	 * @throws SlickException
	 *             the slick exception
	 */
	@SuppressWarnings("unchecked")
	public MainMenuState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, (ArrayList<HudElement>) background.clone());
		int x = background.size() + 1;
	}

	/**
	 * Make contents.
	 * 
	 * @return the list of default contents, placed on top of the background.
	 *         simple enough.
	 * @throws SlickException
	 *             the slick exception
	 */
	public ArrayList<HudElement> makeContents() throws SlickException {
		System.out.println("Building MainMenuState");
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);


		this.startButton = new ImageButton(-246, -112, HudElement.CENTER_CENTER, new Image(
				"res/menu/start.png"), new Image("res/menu/startHighlight.png"));
		this.optionsButton = new ImageButton(-246, -47, HudElement.CENTER_CENTER, new Image(
				"res/menu/options.png"), new Image(
				"res/menu/optionsHighlight.png"));
		this.creditsButton = new ImageButton(4, -112, HudElement.CENTER_CENTER, new Image(
				"res/menu/credits.png"), new Image(
				"res/menu/creditsHighlight.png"));
		this.quitButton = new ImageButton(4, -47, HudElement.CENTER_CENTER, new Image(
				"res/menu/quit.png"), new Image("res/menu/quitHighlight.png"));
		
		elements.add(new StillImageElement(-250, 150, HudElement.CENTER_CENTER,
				new Image("res/menu/artBar.png")));

		elements.add(startButton);
		elements.add(optionsButton);
		elements.add(creditsButton);
		elements.add(quitButton);

		return elements;
	}

	/**
	 * on button press, enter the correct state.
	 * 
	 * @see net.plaidypus.deadreckoning.state.HudLayersState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		if (this.startButton.isPressed()) {
			game.enterState(DeadReckoningGame.SAVESELECTSTATE);
		}
		if(this.optionsButton.isPressed()) {
			game.enterState(DeadReckoningGame.OPTIONSSTATE);
		}
		if (this.quitButton.isPressed()) {
			container.exit();
		}
	}
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		DeadReckoningGame.instance.getGameElement().resetBoard();
		System.out.println("asdasdfdgfaMOO");
	}

}
