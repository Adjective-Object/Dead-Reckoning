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

public class SaveSelectorState extends ExclusiveHudLayersState {

	static Save[] saves;
	int buttonIndex;

	@SuppressWarnings("unchecked")
	public SaveSelectorState(int stateID, ArrayList<HudElement> background)
			throws SlickException {
		super(stateID, (ArrayList<HudElement>) background.clone());
		this.buttonIndex = background.size();
		this.HudElements.addAll(SaveSelectorState.makeElementsList());
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		if (this.focus != -1) {
			TextButton currentPressed = (TextButton) this.HudElements
					.get(this.focus);
			if (currentPressed.isPressed()) {
				try {
					if (this.focus > buttonIndex) {
						saves[focus - buttonIndex - 1]
								.loadGame(GameplayElement.class
										.cast(HudLayersState.class
												.cast(DeadReckoningGame.instance
														.getState(DeadReckoningGame.GAMEPLAYSTATE))
												.getElement(0)));
						DeadReckoningGame.instance
								.enterState(DeadReckoningGame.GAMEPLAYSTATE);
					} else if (this.focus == buttonIndex) {
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

	private static ArrayList<HudElement> makeElementsList()
			throws SlickException {
		File f = new File("saves/");
		System.out.println(f.list());
		String[] savesList = f.list(new SaveFilter());
		for (int i = 0; i < savesList.length; i++) {
			System.out.println(savesList[i]);
		}
		ArrayList<HudElement> buttons = new ArrayList<HudElement>(
				savesList.length + 2);
		saves = new Save[savesList.length];

		buttons.add(new TextButton(10, 30, HudElement.TOP_LEFT, new Color(30,
				50, 70), new Color(40, 60, 80), new Color(60, 80, 100),
				"New Game", DeadReckoningGame.menuFont));

		for (int i = 0; i < savesList.length; i++) {
			saves[i] = new Save("saves/" + savesList[i]);
			buttons.add(new TextButton(
					10
							+ (i * 30)
							/ (DeadReckoningGame.instance.getContainer()
									.getHeight() - 60) * 150, 60
							+ (i * 30)
							% (DeadReckoningGame.instance.getContainer()
									.getHeight() - 60), HudElement.TOP_LEFT,
					new Color(30, 50, 70), new Color(40, 60, 80), new Color(60,
							80, 100), saves[i].getName(),
					DeadReckoningGame.menuFont));
		}

		return buttons;
	}

}
