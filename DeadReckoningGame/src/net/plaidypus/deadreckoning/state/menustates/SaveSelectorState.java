package net.plaidypus.deadreckoning.state.menustates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.save.Save;
import net.plaidypus.deadreckoning.save.SaveFilter;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;

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
	 * @see net.plaidypus.deadreckoning.save.Save#loadGame(GameplayElement)
	 * @see net.plaidypus.deadreckoning.state.ExclusiveHudLayersState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		for (int i=0; i<buttonList.size(); i++){
			TextButton currentPressed = buttonList.get(i);
			if (currentPressed.isPressed()) {
				if(i!=0){ //because index 0 is the new game button
					try {
						saves[i-1].loadGame(DeadReckoningGame.instance.getGameElement(),container);
						DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (i==0) {
					DeadReckoningGame.instance
							.enterState(DeadReckoningGame.NEWGAMESTATE);
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
	
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		System.out.println("ENTERING");
		super.enter(container,game);
		this.contents.removeAll(this.buttonList);
		this.contents.addAll(this.makeContents());
	}
}
