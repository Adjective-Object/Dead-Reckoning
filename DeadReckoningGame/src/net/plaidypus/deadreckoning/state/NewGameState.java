package net.plaidypus.deadreckoning.state;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextEntryBox;
import net.plaidypus.deadreckoning.professions.Profession;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Class NewGameState.
 */
public class NewGameState extends HudLayersState {

	/** The buttons that reference the saved and default classes */
	public ArrayList<ImageButton> classButtons;

	/** The button to create a new class. */
	public ImageButton newClassButton;

	/** The text. */
	public TextEntryBox text;

	/** The x, and y locations of the class buttons. */
	int columA = 15, columB = 300;

	/**
	 * Instantiates a new game creator state.
	 * 
	 * @param stateID
	 *            the state id
	 * @param background
	 *            the background of the screen
	 * @throws SlickException
	 *             the slick exception
	 */
	@SuppressWarnings("unchecked")
	public NewGameState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, (ArrayList<HudElement>) (background.clone()));
		loadClasses();
	}

	/**
	 * loads classes and builds the necessary HudElements
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public void loadClasses() throws SlickException {
		classButtons = new ArrayList<ImageButton>(0);
		ArrayList<HudElement> elim = new ArrayList<HudElement>(0);
		ArrayList<HudElement> elimB = new ArrayList<HudElement>(0);

		// this quick block creates a the "create new class" button
		newClassButton = new ImageButton(columB, 20, HudElement.TOP_LEFT,
				new Image("res/newClassButton.png"));
		elim.add(newClassButton);
		elim.add(new TextElement(columB + 69, 42, HudElement.TOP_LEFT,
				"New Class", Color.white, DeadReckoningGame.menuFont));

		// this block of fuck loads the custom professions, and creates the
		// buttons
		// that are needed for them
		// untested, may or may not work. TODO fix that....
		for (int prof = 0; prof < Profession.getProfessions().size(); prof++) {
			int baseClass = 0;
			ImageButton i = new ImageButton(columA, 15 + (prof) * 79,
					HudElement.TOP_LEFT, Profession.getProfession(prof)
							.getPortriat());
			classButtons.add(i);
			elimB.add(i);
			elimB.add(new TextElement(columA + 69, 15 + 22 + (prof) * 79,
					HudElement.TOP_LEFT, Profession.getProfession(prof).name,
					Color.white, DeadReckoningGame.menuFont));
		}

		// TODO add custom loaded professions

		// this creates the text box necessary for saving the game
		ArrayList<HudElement> elimC = new ArrayList<HudElement>(0);
		text = new TextEntryBox(-250, 50, HudElement.TOP_RIGHT, 200, 50);
		elimC.add(text);

		// this packages the hudelement lists into Panels and drops them into
		// the "real" HudElements list
		this.HudElements.add(new Panel(elim));
		this.HudElements.add(new Panel(elimB));
		this.HudElements.add(new Panel(elimC));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.state.HudLayersState#update(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (newClassButton.isPressed()) {
			DeadReckoningGame.instance.enterState(DeadReckoningGame.NEWCLASSSTATE);
		}
		else{
			for (int i = 0; i < classButtons.size(); i++) {
				if (classButtons.get(i).isPressed()
						&& !text.getContent().equals("")) {
					try {
						Save s = Save.makeNewSave(
								"saves/SAVE " + (Save.enumerateSaves()) + "/",
								text.getContent(), Profession.getProfession(i));
						s.loadGame(GameplayElement.class.cast(HudLayersState.class
								.cast(DeadReckoningGame.instance
										.getState(DeadReckoningGame.GAMEPLAYSTATE))
								.getElement(0)), game.getContainer());
						game.enterState(DeadReckoningGame.GAMEPLAYSTATE);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
