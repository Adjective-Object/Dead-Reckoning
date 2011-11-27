package net.plaidypus.deadreckoning.state;


import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Goblin;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

	int stateID;
	int currentEntity;
	boolean actionAssigned;

	public static float cameraX, cameraY;
	public static float cameraDestX, cameraDestY;

	static final float cameraRate = (float) 0.2;

	Input input;
	public Player player;
	static Image backgroundScreen, playerHUD, skillHUD;
	
	GameBoard gb;

	public GameplayState(int stateID) throws SlickException {
		this.stateID = stateID;
		currentEntity = 0;
	}

	/**
	 * initializes the gameplay state
	 * 
	 * @param gc
	 * @param sbg
	 * @throws SlickException
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		System.out.println("Initializing GameplayState");
		
		input = gc.getInput();
		
		DamageEffect.init();
		Tile.init("res\\wallTiles.png");
		Torch.init();
		Chest.init();
		backgroundScreen=new Image(gc.getWidth(),gc.getHeight());
		
		playerHUD = new Image("res/HUD/PlayerBox.png");
		skillHUD = new Image("res/HUD/SkillSlots.png");
		
		gc.setTargetFrameRate(60);
		gc.setVSync(true);
		gb = new GameBoard(25,25);
		gb.init();
		
		player = new Player(gb.getTileAt(4, 4),gc.getInput());
		new Goblin(gb.getTileAt(24, 1));
		cameraX = 0;
		cameraY = 0;
		actionAssigned = false;
	}

	/**
	 * updates the gamestate (called automagically by slick)
	 * 
	 * @param gc
	 * @param sbg
	 * @param delta
	 * @throws SlickException
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		cameraX = cameraX + (cameraDestX - cameraX) * cameraRate;
		cameraY = cameraY + (cameraDestY - cameraY) * cameraRate;
		
		gb.HideAll();
		
		gb.updateSelctor(input, -cameraX, -cameraY);
		gb.updateAllTiles(gc, delta);
		updateActions(gc,delta);
	}
	
	private void updateActions(GameContainer gc, int delta) {
		while(!gb.ingameEntities.get(currentEntity).isInteractive()){
			currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
		}
		
		Entity current = gb.ingameEntities.get(currentEntity);
		
		if (player.canSee(current) && !actionAssigned) {
			cameraDestX = current.getAbsoluteX() - gc.getWidth() / 2 + DeadReckoningGame.tileSize/2;
			cameraDestY = current.getAbsoluteY() - gc.getHeight() / 2 + DeadReckoningGame.tileSize/2;
		}
		
		if (!player.canSee(current)
				|| (Math.abs(cameraDestX - cameraX) <= 0.1 && Math
						.abs(cameraDestY - cameraY) <= 0.1)) {

			if (!this.actionAssigned) {
				current.setAction(current.chooseAction(gc, delta));
				if (current.getAction() != null) {
					this.actionAssigned = true;
				}
			}

			current.applyAction(gc, delta);
			if(this.actionAssigned){
				gb.clearHighlightedSquares();
				gb.clearPrimaryHighlight();
			}
			
			if (this.actionAssigned && current.getAction().completed
					&& gb.isIdle()) {
				gc.getGraphics().copyArea(backgroundScreen, 0, 0);
				this.actionAssigned = false;
				currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
			}

		}
	}

	/**
	 * renders the gamestate (gameboard and particless)
	 * 
	 * @param gc
	 * @param sbg
	 * @param g
	 * @throws SlickException
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gb.render(g, -cameraX, -cameraY);
		g.drawImage(playerHUD,5,5);
		g.drawImage(skillHUD,248,5);
		g.setColor(new Color(200,70,70));
		g.fillRect(131, 30, 75*player.HP/player.maxHP, 9);
		g.setColor(new Color(70,70,200));
		g.fillRect(131, 54, 75*player.MP/player.maxMP, 9);
		g.setColor(new Color(200,200,70));
		g.fillRect(131, 79, 75*player.EXP/player.getEXPforLevel(), 9);
		
		
	}

	public int getID() {
		return stateID;
	}
	
	/**
	 * adds a particle to the particle list in game
	 * @param p the particle to add
	 */
	
	public static Image getImage(){
		return backgroundScreen;
	}

}
