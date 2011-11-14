package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Goblin;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.particles.DamageParticle;
import net.plaidypus.deadreckoning.particles.Particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

	int stateID = -1;
	int currentEntity;
	boolean actionAssigned;

	public static float cameraX, cameraY;
	public static float cameraDestX, cameraDestY;

	static final float cameraRate = (float) 0.2;

	Input input;
	public Player player;

	GameBoard gb;

	public static ArrayList<Particle> particles;

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
		input = gc.getInput();

		DamageParticle.init();
		Tile.init("res\\wallTiles.png");
		Torch.init();
		Chest.init();
		
		particles = new ArrayList<Particle>(0);

		gc.setTargetFrameRate(60);
		gc.setVSync(true);
		gb = new GameBoard(25,25);
		gb.init();
		
		player = new Player(gc.getInput());
		gb.placeEntity(4, 4, player);
		gb.placeEntity(24, 1, new Goblin());
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
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(gc, sbg, delta);
			if (particles.get(i).toKill) {
				particles.remove(i);
				i--;
			}
		}
		
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
			cameraDestX = current.getAbsoluteX() - gc.getWidth() / 2;
			cameraDestY = current.getAbsoluteY() - gc.getHeight() / 2;
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
			
			if (this.actionAssigned && current.getAction().completed
					&& gb.isIdle()) {
				gb.clearHighlightedSquares();
				gb.clearPrimaryHighlight();
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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		gb.render(g, -cameraX, -cameraY);
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g, -cameraX, -cameraY);
		}
	}

	public int getID() {
		return stateID;
	}
	
	/**
	 * adds a particle to the particle list in game
	 * @param p the particle to add
	 */
	public static void spawnParticle(Particle p) {
		particles.add(p);
	}

}
