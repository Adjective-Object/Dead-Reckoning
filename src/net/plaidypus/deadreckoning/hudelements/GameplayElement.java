package net.plaidypus.deadreckoning.hudelements;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Goblin;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.grideffects.DamageEffect;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.skills.Fireball;
import net.plaidypus.deadreckoning.status.OnFire;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayElement extends HudElement {

	int stateID;
	int currentEntity;
	int saveNumber;
	
	private int width;
	private int height;
	
	public static float cameraX, cameraY;
	public static float cameraDestX, cameraDestY;

	static final float cameraRate = (float) 0.2;

	Input input;
	public Player player;
	static Image backgroundScreen;
	
	GameBoard gb;
	
	ArrayList<Action> actions;
	
	
	public GameplayElement(int saveNumber) throws SlickException {
		super(0,0,HudElement.TOP_LEFT,true);
		currentEntity = 0;
		saveNumber = saveNumber;
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
		
		this.width = gc.getWidth();
		this.height=gc.getHeight();
		
		System.out.println("Initializing GameplayState");
		
		input = gc.getInput();
		
		Fireball.init();
		DamageEffect.init();
		Tile.init("res\\wallTiles.png");
		Torch.init();
		Chest.init();
		OnFire.init();
		GameBoard.init();
		
		backgroundScreen=new Image(gc.getWidth(),gc.getHeight());
		
		gc.setTargetFrameRate(60);
		gc.setVSync(true);
		try {
			gb = new GameBoard(this,saveNumber,0);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		player = new Player(gb.getTileAt(4,4), new Profession (0), input);
		new Goblin(gb.getTileAt(7, 4));
		cameraX = 0;
		cameraY = 0;
		actions = new ArrayList<Action> (0);
		
		updateBoardEffects(gc, 0);
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
		
		gb.updateSelctor(input, -cameraX, -cameraY);
		gb.updateAllTiles(gc, delta);
		updateActions(gc,delta);
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
		gb.HideAll();
		gb.updateBoardEffects(gc, delta);
		gb.revealFromEntity(player);//TODO replace with from player
	}
	
	private void updateActions(GameContainer gc, int delta) {
		
		while(!gb.ingameEntities.get(currentEntity).isInteractive() ){
			currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
		}
		
		Entity current = gb.ingameEntities.get(currentEntity);
		
		if (current.getLocation().isVisible() && current.getLocation().lightLevel>0 && actions.size()==0) {
			cameraDestX = current.getAbsoluteX() - gc.getWidth() / 2 + DeadReckoningGame.tileSize/2;
			cameraDestY = current.getAbsoluteY() - gc.getHeight() / 2 + DeadReckoningGame.tileSize/2;
		}
		
		if ((!current.getLocation().isVisible() && current.getLocation().lightLevel==0)
				|| (Math.abs(cameraDestX - cameraX) <= 0.01 && Math
						.abs(cameraDestY - cameraY) <= 0.01)) {
			
			//System.out.println(actions);
			
			if (this.actions.size()==0) {
				current.advanceTurn();
				Action a = current.chooseAction(gc, delta);
				if(a!=null){
					actions.add(a);
				}
			}

			if(!actionsComplete() && actions.size()>0 && gb.isIdle()){
				actions.get(0).applyAction(delta);
				for(int i=0; i<actions.size(); i++){
					if(!actions.get(i).completed){
						actions.get(i).applyAction(delta);
						break;
					}
				}
			}
			
			if (actionsComplete() & actions.size()>0 && gb.isIdle()) {
				gb.clearHighlightedSquares();
				gb.clearPrimaryHighlight();
				gc.getGraphics().copyArea(backgroundScreen, 0, 0);
				currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
				updateBoardEffects(gc,delta);
				actions.clear();
			}

		}
	}

	private boolean actionsComplete() {
		for(int i=0; i<actions.size();i++){
			if(!actions.get(i).completed){
				return false;
			}
		}
		return true;
	}

	/**
	 * renders the gamestate (gameboard and particless)
	 * 
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.clear();
		gb.render(g, -cameraX, -cameraY);
		gc.getGraphics().copyArea(backgroundScreen, 0, 0);
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
	
	public void addAction(Action a){
		this.actions.add(a);
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void makeFrom(Object o) {}//TODO making from integer saveNumber instead of passing that way cna be mde from selector

}
