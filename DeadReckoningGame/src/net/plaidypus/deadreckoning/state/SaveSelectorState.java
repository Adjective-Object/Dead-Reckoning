package net.plaidypus.deadreckoning.state;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.SaveFilter;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Class SaveSelectorState.
 * 
 * it's used to SELECT THE FUCKING SAVE TO BE PASSED TO THE GAME. WHO WOULD HAVE
 * FUCKING THOUGHT IT.
 * 
 * anyway, it also allows for the creation of new states
 */
public class SaveSelectorState extends PrebakedHudLayersState {

	/** The saves. */
	static Save[] saves;

	/** The button index. */
	int buttonIndex;
	
	ArrayList<TextButton> buttonList;

	/**
	 * Instantiates a new save selector state.
	 * 
	 * @param stateID
	 *            the state id
	 * @param background
	 *            the background
	 * @throws SlickException
	 *             the slick exception
	 */
	@SuppressWarnings("unchecked")
	public SaveSelectorState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, (ArrayList<HudElement>) background.clone());
		this.buttonIndex = background.size();
	}

	/**
	 * the update loop
	 * 
	 * basically, if any button is pressed, that button's corresponding save has
	 * it's loadGame called
	 * 
	 * @see net.plaidypus.deadreckoning.Save#loadGame(GameplayElement)
	 * @see net.plaidypus.deadreckoning.state.ExclusiveHudLayersState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		for (int i=0; i<buttonList.size(); i++){
			TextButton currentPressed = buttonList.get(i);
			if (currentPressed.isPressed()) {
				try {
					if(i!=0){ //because index 0 is the new game button
						saves[i-1].loadGame(
										GameplayElement.class
												.cast(HudLayersState.class
														.cast(DeadReckoningGame.instance
																.getState(DeadReckoningGame.GAMEPLAYSTATE))
														.getElement(0)),
										container);
						DeadReckoningGame.instance
								.enterState(DeadReckoningGame.GAMEPLAYSTATE);
					} else if (i==0) {
						DeadReckoningGame.instance
								.enterState(DeadReckoningGame.NEWGAMESTATE);
					}

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

	/**
	 * Makes the default HudElement list.
	 * 
	 * it's used in the initialization function, because each instance of this
	 * state will have the same contents
	 * 
	 * @return the array list
	 * @throws SlickException
	 *             the slick exception
	 */
	protected ArrayList<HudElement> makeContents() throws SlickException {
		// enumerates the number of saves
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
		
		File f = new File("saves/");
		String[] savesList = f.list(new SaveFilter());

		buttonList = new ArrayList<TextButton>(
				savesList.length + 2);
		saves = new Save[savesList.length];

		buttonList.add(new TextButton(10, 30, HudElement.TOP_LEFT, new Color(30,
				50, 70), new Color(40, 60, 80), new Color(60, 80, 100),
				"New Game", DeadReckoningGame.menuFont));

		// creates the buttons needed to reference those saves
		for (int i = 0; i < savesList.length; i++) {
			saves[i] = new Save("saves/" + savesList[i]);
			buttonList.add(new TextButton(
							10 + (i * 30)
							/ (DeadReckoningGame.instance.getContainer()
									.getHeight() - 60) * 150,
							60 + (i * 30)
							% (DeadReckoningGame.instance.getContainer()
									.getHeight() - 60),
					HudElement.TOP_LEFT,
					new Color(30, 50, 70), new Color(40, 60, 80), new Color(60,
							80, 100), saves[i].getName(),
					DeadReckoningGame.menuFont));
		}
		
		returnElements.addAll(buttonList);
		return returnElements;
	}
	
	public void onEnter(GameContainer container, StateBasedGame game) throws SlickException{
		System.out.println("ENTERING");
		this.contents.removeAll(this.buttonList);
		this.contents.addAll(this.makeContents());
	}

	
	@Override
	public void makeFrom(Object O) {
	}
}
