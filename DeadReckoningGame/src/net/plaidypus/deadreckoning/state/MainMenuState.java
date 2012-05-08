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
import org.newdawn.slick.state.StateBasedGame;

/**
 * The HudLayersState of the Main Menu.
 * 
 */
public class MainMenuState extends HudLayersState {

	/** The state id. */
	int stateID;

	/** The quit button. */
	Button startButton, optionsButton, creditsButton, quitButton;

	/** The lights. */
	static FairyLights lights;

	/**
	 * Instantiates a new main menu state.
	 *
	 * @param stateID the state id
	 * @param background the background
	 * @throws SlickException the slick exception
	 */
	@SuppressWarnings("unchecked")
	public MainMenuState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, (ArrayList<HudElement>) background.clone());
		int x = background.size() + 1;
		this.HudElements.addAll(makeContents());
		this.startButton = (Button) this.getElement(x);
		this.optionsButton = (Button) this.getElement(x + 1);
		this.creditsButton = (Button) this.getElement(x + 2);
		this.quitButton = (Button) this.getElement(x + 3);
	}

	/**
	 * Make contents.
	 *
	 * @return the list of default contents, placed on top of the background. simple enough.
	 * @throws SlickException the slick exception
	 */
	public static ArrayList<HudElement> makeContents() throws SlickException {
		System.out.println("Building MainMenuState");
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);

		elements.add(new StillImageElement(150, 450, HudElement.TOP_LEFT,
				new Image("res/menu/artBar.png")));

		elements.add(new ImageButton(154, 198, HudElement.TOP_LEFT, new Image(
				"res/menu/start.png"), new Image("res/menu/startHighlight.png")));
		elements.add(new ImageButton(154, 253, HudElement.TOP_LEFT, new Image(
				"res/menu/options.png"), new Image(
				"res/menu/optionsHighlight.png")));
		elements.add(new ImageButton(404, 198, HudElement.TOP_LEFT, new Image(
				"res/menu/credits.png"), new Image(
				"res/menu/creditsHighlight.png")));
		elements.add(new ImageButton(404, 253, HudElement.TOP_LEFT, new Image(
				"res/menu/quit.png"), new Image("res/menu/quitHighlight.png")));

		return elements;
	}

	/**
	 * on button press, enter the correct state.
	 * 
	 * @see net.plaidypus.deadreckoning.state.HudLayersState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		if (this.startButton.isPressed()) {
			game.enterState(DeadReckoningGame.SAVESELECTSTATE);
		}
		if (this.quitButton.isPressed()) {
			container.exit();
		}
	}

}
