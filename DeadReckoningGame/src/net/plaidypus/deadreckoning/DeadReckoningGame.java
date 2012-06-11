/*
 * 
 */
package net.plaidypus.deadreckoning;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.game.MiniMap;
import net.plaidypus.deadreckoning.hudelements.game.PlayerHudElement;
import net.plaidypus.deadreckoning.hudelements.game.SkillMonitorElement;
import net.plaidypus.deadreckoning.hudelements.game.StatusTrackerElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ItemGridInteractionElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.menuItems.FairyLights;
import net.plaidypus.deadreckoning.hudelements.simple.ColorFiller;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.StringPutter;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.state.ExclusiveHudLayersState;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.state.MainMenuState;
import net.plaidypus.deadreckoning.state.NewGameState;
import net.plaidypus.deadreckoning.state.PlayerViewerState;
import net.plaidypus.deadreckoning.state.SaveSelectorState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Class DeadReckoningGame.
 * the Central Game class of the DeadReckoningGame.
 * It manages several HudLayersStates (an extention of the BasicStates provided by Slick).
 * It also holds important constants (UI colors, indicies of stored states, size of tiles)
 * 
 * Only once instance of a DeadReckoningGame can be active at any time.
 * This is kind of an ugly hackish workaround for a few problems encountered deeper in, but
 * the limitations it imposes are mostly minimal.
 */
public class DeadReckoningGame extends StateBasedGame {
	
	/** The Constants that guide the referencing of states. */
	public static final int LOOTSTATE = 0, INVENTORYSTATE = 1,
			GAMEPLAYSTATE = 2, MAINMENUSTATE = 3, SAVESELECTSTATE = 4,
			MAPSTATE = 5, SKILLSTATE = 6, NEWGAMESTATE = 7, ERRORSTATE = 8;

	/** The Constant tileSize, that governs the size of the tiles in the game (32x32)*/
	public static final int tileSize = 32;
	
	/** The Constants that control the User Interface Colors*/
	public static final Color menuColor = new Color(60, 40, 50, 255),
			menuBackgroundColor = new Color(20, 40, 60),
			menuTextColor = new Color(255, 255, 255),
			menuTextBackgroundColor = new Color(0, 0, 0),
			mouseoverBoxColor = new Color(50, 30, 50, 200),
			mouseoverTextColor = new Color(255, 255, 255, 200),
			menuHighlightColor = new Color(210, 210, 0),
			skillInvalidColor = new Color(0, 0, 0, 80);
	
	/** The active instance of DeadReckoningGame. */
	public static DeadReckoningGame instance;
	
	/** The menu background.
	 * This is to maintain the same particles and other graphical polishes across several states,
	 * along with it just being easier to deal with this way (no need for repetition)*/
	protected ArrayList<HudElement> menuBackground;
	
	/** The StringPutter object
	 * This that tracks global "announcement" messages.
	 * These are usually sent by actions in GameplayElement,
	 * but are theoretically sendable by anything, anywhere. */
	protected StringPutter messages;
	
	/** The main game element.
	 * kept here for easier referencing*/
	protected GameplayElement game;

	/** The various menu fonts. */
	public static UnicodeFont menuFont, menuSmallFont;

	/**
	 * Instantiates a new dead reckoning game.
	 * 
	 * @throws SlickException Slick Custom Exception. Encompasses several IO and Graphical errors within Slick objects
	 */
	DeadReckoningGame() throws SlickException {
		super("Dead Reckoning");

		this.messages = new StringPutter(0, -42, HudElement.BOTTOM_LEFT,80);
		this.game = new GameplayElement(0);

		DeadReckoningGame.instance = this;

		String[] s = { "classes/", "saves/" };
		for (int i = 0; i < s.length; i++) {
			File f = new File(s[i]);
			try {
				if (!f.exists())
					f.mkdir();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the game element.
	 *
	 * @return the game element
	 */
	public GameplayElement getGameElement() {
		return game;
	}

	/**
	 * Gets the message element.
	 *
	 * @return the message element
	 */
	public StringPutter getMessageElement() {
		return messages;
	}

	/**
	 * The main method.
	 * Creates a game container (A slick window) and puts a new DeadReckoningGame inside it
	 *
	 * @param args included in Java by default. have no meaning here.
	 * @throws SlickException the slick exception
	 */
	public static void main(String[] args) throws SlickException {
		try {
			AppGameContainer app = new AppGameContainer(new DeadReckoningGame());
			app.setDisplayMode(800, 600, false);
			app.start();
			app.getInput().enableKeyRepeat();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets a state stored at a certain index, cast to the HudState type
	 * Useful because the BasicGameState does not specify that the contents must extend the HudState class.
	 *
	 * @param id the id of the gamestate referenced. Usually just called with one of the gamestate constants defined in DeadReckoningGame
	 * @return the hud state
	 */
	public HudLayersState getHudState(int id) {
		return (HudLayersState) (getState(id));
	}

	/**
	 * this initializes the states list for the game.
	 * creates the various HudElement states in the DeadReckoningGame.
	 * 
	 * @param container the GameContainer object (window from slick) that the game takes place in
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		try {
			ModLoader.loadModpacks(ModLoader.resolveMods(true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(Biome.getBiomes());
		System.out.println(Profession.getProfessions());
		
		this.menuBackground = new ArrayList<HudElement>(0);
		SpriteSheet particles = new SpriteSheet(new Image(
				"res/menu/particles.png"), 50, 50);
		menuBackground.add(new StillImageElement(0, 0, HudElement.TOP_LEFT,
				new Image("res/menu/background.png")));
		menuBackground.add(new FairyLights(-50, -300, HudElement.BOTTOM_LEFT,
				850, 250, 80, particles));
		menuBackground.add(new FairyLights(-50, -200, HudElement.BOTTOM_LEFT,
				850, 150, 100, particles));
		menuBackground.add(new FairyLights(-50, -100, HudElement.BOTTOM_LEFT,
				850, 100, 120, particles));

		menuFont = new UnicodeFont("/res/visitor.ttf", 20, true, false);
		menuFont.addNeheGlyphs();
		menuFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuFont.loadGlyphs();
		menuSmallFont = new UnicodeFont("/res/visitor.ttf", 15, true, false);
		menuSmallFont.addNeheGlyphs();
		menuSmallFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuSmallFont.loadGlyphs();

		container.getInput().enableKeyRepeat();

		HudElement.calculateOffsets(container);
		//initialization of internal entities and things that will not be initialized by classloader
		new Player().init();
		new Stair().init();

		this.addState(new MainMenuState(MAINMENUSTATE, this.menuBackground));

		this.addState(new HudLayersState(GAMEPLAYSTATE, new HudElement[] {
				game,
				new PlayerHudElement(10, 10, HudElement.TOP_LEFT, 0),
				new StatusTrackerElement(10, 120, HudElement.TOP_LEFT, 0),
				new SkillMonitorElement(-200, -45, HudElement.BOTTOM_CENTER, game),
				new MiniMap(-3,1,HudElement.TOP_RIGHT,2,70,70,game),
				messages }));

		this.addState(new ExclusiveHudLayersState(LOOTSTATE,
				new HudElement[] { // TODO create custom state for this, instead
									// of "interaction" element
						new StillImageElement(0, 0, HudElement.TOP_LEFT),
						messages,
						new ItemGridElement(-241, -132,
								HudElement.CENTER_CENTER),
						new ItemGridElement(50, -132, HudElement.CENTER_CENTER),
						new ItemGridInteractionElement(2, 3),
						new ReturnToGameElement() }));

		this.addState(new HudLayersState(INVENTORYSTATE, new HudElement[] {
				new StillImageElement(0, 0, HudElement.TOP_LEFT), messages,
				new ItemGridElement(0, 0, HudElement.CENTER_CENTER),
				new ReturnToGameElement() }));

		this.addState(new HudLayersState(ERRORSTATE, new HudElement[] {
				new ColorFiller(menuBackgroundColor),
				new TextElement(0, 0, HudElement.TOP_LEFT, "", menuTextColor,
						menuFont) }));

		this.addState(new SaveSelectorState(SAVESELECTSTATE, menuBackground));
		this.addState(new PlayerViewerState(SKILLSTATE));
		this.addState(new NewGameState(NEWGAMESTATE, menuBackground));

		this.enterState(MAINMENUSTATE);
	}

}
