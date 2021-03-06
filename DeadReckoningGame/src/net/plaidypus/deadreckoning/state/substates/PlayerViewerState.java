package net.plaidypus.deadreckoning.state.substates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.StatDisplayElement;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;
import net.plaidypus.deadreckoning.statmaster.Profession;
import net.plaidypus.deadreckoning.statmaster.SkillProgression;
import net.plaidypus.deadreckoning.utilities.KeyConfig;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Class PlayerViewerState.
 * 
 * used to view the player's stats (as determined by level and stat ratios),
 * along with viewing the player's skills, and when skill points are available,
 * editing them.
 */
public class PlayerViewerState extends PrebakedHudLayersState {

	/** The Constant ofY. */
	static final int ofX = 50, ofY = 50;

	/** The stat panel. */
	Panel buttonPanel, statPanel;
	
	/** The source prof. */
	Profession sourceProf;

	/** The images. */
	Image[][] images = new Image[3][12];// dimmed,highlighted, both

	/**
	 * Instantiates a new player viewer state.
	 * 
	 * @param stateID
	 *            the state id
	 * @throws SlickException 
	 */
	public PlayerViewerState(int stateID, ArrayList<HudElement> background) throws SlickException {
		super(stateID, background);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.state.HudLayersState#update(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		for (int i = 0; i < buttonPanel.getContents().size() - 1; i++) {
			Button b = (Button) (buttonPanel.getContents().get(i));
			Skill siq = sourceProf.getTrees()[i / 4].getSkills()[i % 4];
			if (b.isPressed()
					&& sourceProf.skillPoints > 0
					&& siq.getLevelCap() > siq.getLevel()
					&& this.sourceProf.getLevel() > siq.getLevelReq()
					&& (i % 4 == 0 || sourceProf.getTrees()[i / 4].getSkills()[i % 4 - 1]
							.getLevel() > 0)) {
				sourceProf.skillPoints--;
				siq.levelUp();
				bakeFromProfession(sourceProf);
			}
		}
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		
		Player p = DeadReckoningGame.instance.getGameElement().player;
		this.sourceProf = p.getProfession();

		ArrayList<Skill> skills = this.sourceProf.getSkillList();
		// this whole block is used to bake the class skill buttons to speed up
		// rendering.
		// it creates a sizable amount of lag on call.
		// this makes me sad
		for (int i = 0; i < skills.size(); i++) {
			try {

				Image img = skills.get(i).getIcon().getFlippedCopy(false, false);
				Graphics g = img.getGraphics();
				g.setColor(new Color(0, 0, 0, 80));
				g.fillRect(0, 0, 32, 32);
				images[0][i] = img;
			
				img = skills.get(i).getIcon().getFlippedCopy(false, false);

				g = img.getGraphics();
				g.setColor(DeadReckoningGame.skillInvalidColor);
				g.fillRect(0, 0, 32, 32);
				g.setColor(DeadReckoningGame.menuHighlightColor);
				g.drawRect(0, 0, 31, 31);
				images[2][i] = img;

				img = skills.get(i).getIcon().getFlippedCopy(false, false);

				g = img.getGraphics();
				g.setColor(DeadReckoningGame.menuHighlightColor);
				g.drawRect(0, 0, 31, 31);
				images[1][i] = img;
			} catch (SlickException e) {}
		}

		bakeFromProfession(sourceProf);

		this.buttonPanel.bakeBorders();
		this.statPanel.makeFrom(new Object[] { p.profession.getPortriat(), p });


	}

	/**
	 * "Bakes" the state from a profession.
	 * 
	 * used to update the display images and mouse over text of the skill
	 * buttons, and also updates the skill-points text.
	 * 
	 * @param p
	 *            the p
	 */
	public void bakeFromProfession(Profession p) {
		for (int i = 0; i < 3; i++) {
			SkillProgression prog = p.getTrees()[i];
			for (int s = 0; s < 4; s++) {
				Image img;

				// used to determine what image a skill has
				// if a skill should be highlighted (is available for level up)
				if (sourceProf.skillPoints > 0
						&& (s == 0 || prog.getSkills()[s - 1].getLevel() > 0)
						&& p.getLevel() >= prog.getSkills()[s].getLevelReq()
						&& prog.getSkills()[s].getLevel() < prog.getSkills()[s]
								.getLevelCap()) { //
					// if a skill is unlocked
					if (prog.getSkills()[s].getLevel() >= 1) {
						img = images[1][i * 4 + s];
					} else {
						img = images[2][i * 4 + s];
					}
				}
				// otherwise, if a skill is not available for level up
				else {
					if (prog.getSkills()[s].getLevel() >= 1) {
						img = prog.getSkills()[s].getIcon();
					} else {
						img = images[0][i * 4 + s];
					}
				}

				
				this.buttonPanel.getContents().get(i * 4 + s).makeFrom(img);
				this.buttonPanel
						.getContents()
						.get(i * 4 + s)
						.setMouseoverText(
								prog.getSkills()[s].getName() + " ("
										+ prog.getSkills()[s].getLevel()
										+ ")\n"
										+ prog.getSkills()[s].getDescription());
			}
		}

		this.buttonPanel.getContents()
				.get(this.buttonPanel.getContents().size() - 1)
				.makeFrom("Remaining SP: " + Integer.toString(p.skillPoints));

	}

	/**
	 * Makes the default contents of the state.
	 * 
	 * called in the initialization. function
	 * 
	 * @return the array list
	 */
	@Override
	public ArrayList<HudElement> makeContents() {
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		// building skill buttons
		ArrayList<HudElement> skillButton = new ArrayList<HudElement>(12), playerWindow = new ArrayList<HudElement>(
				2);
		for (int x = 0; x < 3; x++) {
			for (int i = 0; i < 4; i++) {
				skillButton.add(new ImageButton(ofX + 55 * x + 5, ofY + 5 + 60
						* i, HudElement.TOP_LEFT, null));
			}
		}

		skillButton.add(new TextElement(50, 270, HudElement.TOP_LEFT, "",
				DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont));

		// building the player window
		playerWindow
				.add(new StillImageElement(-200, 0, HudElement.CENTER_RIGHT));
		playerWindow.add(new StatDisplayElement(-200, 69,
				HudElement.CENTER_RIGHT));// TODO replace with stat display
											// element;

		buttonPanel = new Panel(skillButton);
		elements.add(buttonPanel);
		statPanel = new Panel(playerWindow);
		elements.add(statPanel);

		elements.add(new ReturnToGameElement(KeyConfig.VIEWPLAYER));

		return elements;
	}

}
