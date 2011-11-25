package net.plaidypus.deadreckoning.state;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	int stateID;

	Image background;
	Image startButton;
	Image optionsButton;
	Image creditsButton;
	Image quitButton;

	Image startButtonHighlight;
	Image optionsButtonHighlight;
	Image creditsButtonHighlight;
	Image quitButtonHighlight;

	Image artBar;

	final int startX = 154;
	final int startY = 198;
	final int optionsX = 154;
	final int optionsY = 253;
	final int creditsX = 404;
	final int creditsY = 198;
	final int quitX = 404;
	final int quitY = 253;

	final int artBarX = 150;
	final int artBarY = 450;

	boolean inStart;
	boolean inOptions;
	boolean inCredits;
	boolean inQuit;

	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	/**
	 * initializes the menu screen
	 * 
	 * @param gc
	 * @param sbg
	 * @throws SlickException
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		System.out.println("Initializing MainMenuState");
		
		background = new Image("res/menu/background.png");
		artBar = new Image("res/menu/artBar.png");

		startButton = new Image("res/menu/start.png");
		optionsButton = new Image("res/menu/options.png");
		creditsButton = new Image("res/menu/credits.png");
		quitButton = new Image("res/menu/quit.png");

		startButtonHighlight = new Image("res/menu/startHighlight.png");
		optionsButtonHighlight = new Image("res/menu/optionsHighlight.png");
		creditsButtonHighlight = new Image("res/menu/creditsHighlight.png");
		quitButtonHighlight = new Image("res/menu/quitHighlight.png");

		inStart = false;
		inOptions = false;
		inCredits = false;
		inQuit = false;

	}

	/**
	 * renders the main menu
	 * 
	 * @param gc
	 * @param sbg
	 * @param g
	 * @throws SlickException
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();

		if (inStart) {
			startButtonHighlight.draw(startX, startY);
		} else {
			startButton.draw(startX, startY);
		}
		if (inOptions) {
			optionsButtonHighlight.draw(optionsX, optionsY);
		} else {
			optionsButton.draw(optionsX, optionsY);
		}
		if (inCredits) {
			creditsButtonHighlight.draw(creditsX, creditsY);
		} else {
			creditsButton.draw(creditsX, creditsY);
		}
		if (inQuit) {
			quitButtonHighlight.draw(quitX, quitY);
		} else {
			quitButton.draw(quitX, quitY);
		}

		artBar.draw(artBarX, artBarY);
	}

	/**
	 * updates the main menu. Called automatically by the slick gamestate
	 * manager
	 * 
	 * @param gc
	 * @param sbg
	 * @param delta
	 * @throws SlickException
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		if ((mouseX >= startX && mouseX <= startX + startButton.getWidth())
				&& (mouseY >= startY && mouseY <= startY
						+ startButton.getHeight()))
			inStart = true;
		else
			inStart = false;

		if ((mouseX >= optionsX && mouseX <= optionsX
				+ optionsButton.getWidth())
				&& (mouseY >= optionsY && mouseY <= optionsY
						+ optionsButton.getHeight()))
			inOptions = true;
		else
			inOptions = false;

		if ((mouseX >= creditsX && mouseX <= creditsX
				+ creditsButton.getWidth())
				&& (mouseY >= creditsY && mouseY <= creditsY
						+ creditsButton.getHeight()))
			inCredits = true;
		else
			inCredits = false;

		if ((mouseX >= quitX && mouseX <= quitX + quitButton.getWidth())
				&& (mouseY >= quitY && mouseY <= quitY + quitButton.getHeight()))
			inQuit = true;
		else
			inQuit = false;

		if (inStart) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sbg.enterState(DeadReckoningGame.GAMEPLAYSTATE);
			}
		} else {
		}
		if (inOptions) {
		} else {
		}
		if (inCredits) {
		} else {
		}
		if (inQuit) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				gc.exit();
			}
		} else {
		}

	}

	public int getID() {
		return stateID;
	}

}
