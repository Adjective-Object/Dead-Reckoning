/*
 * 
 */
package net.plaidypus.deadreckoning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.game.MiniMap;
import net.plaidypus.deadreckoning.hudelements.game.PlayerHudElement;
import net.plaidypus.deadreckoning.hudelements.game.SkillMonitorElement;
import net.plaidypus.deadreckoning.hudelements.game.StatusTrackerElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.BigMap;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.VicariousRenderer;
import net.plaidypus.deadreckoning.hudelements.menuItems.FairyLights;
import net.plaidypus.deadreckoning.hudelements.simple.ColorFiller;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.StringPutter;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.state.menustates.ClassCreationState;
import net.plaidypus.deadreckoning.state.menustates.MainMenuState;
import net.plaidypus.deadreckoning.state.menustates.NewGameState;
import net.plaidypus.deadreckoning.state.menustates.OptionsExitState;
import net.plaidypus.deadreckoning.state.menustates.OptionsState;
import net.plaidypus.deadreckoning.state.menustates.SaveSelectorState;
import net.plaidypus.deadreckoning.state.substates.DeathScreenState;
import net.plaidypus.deadreckoning.state.substates.InGameMenuState;
import net.plaidypus.deadreckoning.state.substates.InventoryState;
import net.plaidypus.deadreckoning.state.substates.LootState;
import net.plaidypus.deadreckoning.state.substates.PlayerViewerState;
import net.plaidypus.deadreckoning.utilities.DeadReckoningLogSystem;
import net.plaidypus.deadreckoning.utilities.KeyConfig;
import net.plaidypus.deadreckoning.utilities.OptionsHandler;
import net.plaidypus.deadreckoning.utilities.RichTextLogSystem;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

/**
 * The Class DeadReckoningGame. the Central Game class of the DeadReckoningGame.
 * It manages several HudLayersStates (an extention of the BasicStates provided
 * by Slick). It also holds important constants (UI colors, indicies of stored 	
 * states, size of tiles)
 * 
 * Only once instance of a DeadReckoningGame can be active at any time. This is
 * kind of an ugly hackish workaround for a few problems encountered deeper in,
 * but the limitations it imposes are mostly minimal.
 * 
 * This is a test of posting things. This is only a test.
 */
public class DeadReckoningGame extends StateBasedGame {

	/** The Constants that guide the referencing of states. */
	public static final int LOOTSTATE = 0, INVENTORYSTATE = 1,
	GAMEPLAYSTATE = 2, MAINMENUSTATE = 3, SAVESELECTSTATE = 4,
	MAPSTATE = 5, SKILLSTATE = 6, NEWGAMESTATE = 7, ERRORSTATE = 8,
	DEATHSTATE = 9, NEWCLASSSTATE = 10, INGAMEMENUSTATE = 11,
	OPTIONSSTATE = 12, OPTIONEXITSTATE=13;

	/**
	 * The Constant tileSize, that governs the size of the tiles in the game
	 * (32x32)
	 */
	public static final int tileSize = 32;

	/** The Constants that control the User Interface Colors */
	public static final Color menuColor = new Color(60, 40, 50, 255),
	menuBackgroundColor = new Color(48, 64, 104),
	menuTextColor = new Color(255, 255, 255),
	menuTextBackgroundColor = new Color(0, 0, 0),
	mouseoverBoxColor = new Color(50, 30, 50, 200),
	mouseoverTextColor = new Color(255, 255, 255, 200),
	menuHighlightColor = new Color(210, 210, 0),
	skillInvalidColor = new Color(0, 0, 0, 120);

	/** The active instance of DeadReckoningGame. */
	public static DeadReckoningGame instance;

	/**
	 * The StringPutter object This that tracks global "announcement" messages.
	 * These are usually sent by actions in GameplayElement, but are
	 * theoretically sendable by anything, anywhere.
	 */
	protected StringPutter messages;

	/**
	 * The main game element. kept here for easier referencing
	 */
	protected GameplayElement game;

	/** The various menu fonts. */
	public static UnicodeFont menuFont, menuSmallFont, menuLargeFont, menuDispFont;

	public static boolean debugMode=false;

	/**
	 * Instantiates a new dead reckoning game.
	 * 
	 * @throws SlickException
	 *             Slick Custom Exception. Encompasses several IO and Graphical
	 *             errors within Slick objects
	 */
	DeadReckoningGame() throws SlickException {
		super("Dead Reckoning");

		this.messages = new StringPutter(0, -42, HudElement.BOTTOM_LEFT, 80);
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
	 * The main method. Creates a game container (A slick window) and puts a new
	 * DeadReckoningGame inside it
	 * 
	 * @param args
	 *            included in Java by default. have no meaning here.
	 * @throws SlickException
	 *             the slick exception
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws SlickException, LWJGLException, FileNotFoundException {
		
		
		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/lib/natives");
		
		String[] libs = System.getProperty("java.library.path").split(";");
		for(int i=0; i<libs.length; i++){
			System.out.println(libs[i]);
		}
		
		//for saving things to Log
		Log.setLogSystem(new DeadReckoningLogSystem("log.rtf"));
		// for options
		OptionsHandler.bakeResolutions();
		OptionsHandler.loadSettings();
		//making the actual game
		AppGameContainer app = new DeadReckoningContainer(new DeadReckoningGame());
		app.setDisplayMode(OptionsHandler.getResolutionX(), OptionsHandler.getResolutionY(), OptionsHandler.fullScreen);
		app.setVSync(OptionsHandler.verticalSynch);
		app.setTargetFrameRate(OptionsHandler.frameRate);
		app.start();
		app.getInput().enableKeyRepeat();

	}

	@Override
	public void enterState(int id){
		super.enterState(id);
		Log.debug("Entering State "+id+", "+getState(id).getClass().getSimpleName()+"@"+System.identityHashCode(getState(id)));
	}
	
	/**
	 * Gets a state stored at a certain index, cast to the HudState type Useful
	 * because the BasicGameState does not specify that the contents must extend
	 * the HudState class.
	 * 
	 * @param id
	 *            the id of the gamestate referenced. Usually just called with
	 *            one of the gamestate constants defined in DeadReckoningGame
	 * @return the hud state
	 */
	@Override
	public HudLayersState getState(int id) {
		return (HudLayersState) (super.getState(id));
	}

	/**
	 * this initializes the states list for the game. creates the various
	 * HudElement states in the DeadReckoningGame.
	 * 
	 * @param container
	 *            the GameContainer object (window from slick) that the game
	 *            takes place in
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		HudElement.calculateOffsets(container);
		defineFonts();

		//this is built before everything else because exception handling
		TextElement t1 = new TextElement(0, 0, HudElement.CENTER_CENTER, "", menuTextColor,menuSmallFont);
		t1.personalBindMethod=HudElement.CENTER_CENTER;
		TextElement t2 = new TextElement(0, 0, HudElement.CENTER_CENTER, "", menuTextColor,menuSmallFont);
		t2.personalBindMethod=HudElement.CENTER_CENTER;
		this.addState(
			new HudLayersState(ERRORSTATE,
				new HudElement[] {
					new ColorFiller(menuBackgroundColor),
					t1,
					t2
				}
			)
		);
		
		try{
			container.getInput().enableKeyRepeat();
			
			ModLoader.loadModpacks(ModLoader.resolveMods(true));
			
			
			// initialization of internal entities and things that will not be
			// initialized by classloader
			new Player().init();
			new Stair().init();
				
				ArrayList<HudElement> subBackground = new ArrayList<HudElement>(0);
				subBackground.add(new VicariousRenderer(DeadReckoningGame.instance.getGameElement()));
				subBackground.add(new ColorFiller(new Color(0,0,0,100)) );
				
				defineStates(makeMenuBackground(container), subBackground);
		} catch (SlickException e){
			Log.error(e);
			RichTextLogSystem.closeWriter();
		}
	}

	private void defineStates(ArrayList<HudElement> menuBackground, ArrayList<HudElement> subBackground) throws SlickException {
				
		this.addState(new MainMenuState(MAINMENUSTATE, menuBackground));

		this.addState(
			new HudLayersState(GAMEPLAYSTATE,
				new HudElement[] {
					game,
					new PlayerHudElement(10, 10, HudElement.TOP_LEFT, game),
					new StatusTrackerElement(10, 120, HudElement.TOP_LEFT, game),
					new SkillMonitorElement(-200, -45, HudElement.BOTTOM_CENTER,
							game),
					new MiniMap(-3, 1, HudElement.TOP_RIGHT, 2, 70, 70, game),
					messages
				}
			)
		);
		
		
		this.addState(new LootState(LOOTSTATE,subBackground));

		this.addState(new InventoryState(INVENTORYSTATE,subBackground));

		this.addState(
			new HudLayersState(MAPSTATE,
				new HudElement[] {
					messages,
					new BigMap(0,0,HudElement.CENTER_CENTER, game),
					new ReturnToGameElement(KeyConfig.MINIMAP)
				},
				subBackground
			)
		);

		this.addState(new SaveSelectorState(SAVESELECTSTATE, menuBackground));
		this.addState(new PlayerViewerState(SKILLSTATE, subBackground));
		this.addState(new NewGameState(NEWGAMESTATE, menuBackground));
		this.addState(new DeathScreenState(DEATHSTATE,subBackground));
		this.addState(new ClassCreationState(NEWCLASSSTATE, menuBackground));
		this.addState(new OptionsState(OPTIONSSTATE, menuBackground));
		this.addState(new OptionsExitState(OPTIONEXITSTATE,menuBackground));
		this.addState(new InGameMenuState(INGAMEMENUSTATE,subBackground));

		
		this.enterState(MAINMENUSTATE);
	}

	/*
	 * The menu background. This is to maintain the same particles and other
	 * graphical polishes across several states, along with it just being easier
	 * to deal with this way (no need for repetition)
	 */
	private ArrayList<HudElement> makeMenuBackground(GameContainer container) throws SlickException {
		ArrayList<HudElement> menuBackground= new ArrayList<HudElement>(0);
		SpriteSheet particles = new SpriteSheet(new Image(
		"res/menu/particles.png"), 50, 50);
		menuBackground.add(new ColorFiller(DeadReckoningGame.menuBackgroundColor));
		menuBackground.add(new StillImageElement(-400,75,HudElement.TOP_CENTER,new Image("res/menu/titleBar.png")));
		menuBackground.add(new FairyLights(-50, -300, HudElement.BOTTOM_LEFT,
				container.getWidth()+50, 250, container.getWidth()/10, particles));
		menuBackground.add(new FairyLights(-50, -200, HudElement.BOTTOM_LEFT,
				container.getWidth()+50, 150, container.getWidth()/8, particles));
		menuBackground.add(new FairyLights(-50, -100, HudElement.BOTTOM_LEFT,
				container.getWidth()+50, 100, container.getWidth()/6, particles));
		return menuBackground;
	}

	@SuppressWarnings("unchecked")
	private static void defineFonts() throws SlickException {
		menuFont = new UnicodeFont("/res/visitor.ttf", 20, true, false);
		menuFont.addNeheGlyphs();
		menuFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuFont.loadGlyphs();

		menuSmallFont = new UnicodeFont("/res/visitor.ttf", 15, true, false);
		menuSmallFont.addNeheGlyphs();
		menuSmallFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuSmallFont.loadGlyphs();
		
		menuDispFont = new UnicodeFont("/res/visitor.ttf", 15, true, false);
		menuDispFont.addNeheGlyphs();
		menuDispFont.getEffects().add(new OutlineEffect(2,java.awt.Color.BLACK));
		menuDispFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuDispFont.loadGlyphs();

		menuLargeFont = new UnicodeFont("/res/visitor.ttf", 30, true, false);
		menuLargeFont.addNeheGlyphs();
		menuLargeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuLargeFont.loadGlyphs();
	}

}