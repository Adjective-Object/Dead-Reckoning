package net.plaidypus.deadreckoning.hudelements;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Chest;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Monster;
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
	int currentEntity, currentAction;
	int saveNumber;
	int timeOn;
	
	private int width;
	private int height;
	
	public static float cameraX, cameraY;
	public static float cameraDestX, cameraDestY;

	static final float cameraRate = (float) 0.2;
	
	public String message;
	
	Input input;
	public Player player;
	static Image backgroundScreen;
	
	GameBoard gb;
	
	ArrayList<Action> actions;
	
	
	public GameplayElement(int saveNumber) throws SlickException {
		super(0,0,HudElement.TOP_LEFT,true);
		currentEntity = 0;
		currentAction = 0;
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
		new Monster("res/goblin.entity",gb.getTileAt(7, 4),1);
		new Monster("res/goblin.entity",gb.getTileAt(5, 4),2);
		cameraX = 0;
		cameraY = 0;
		cameraDestX = player.getAbsoluteX() - gc.getWidth() / 2 + DeadReckoningGame.tileSize/2;
		cameraDestY = player.getAbsoluteY() - gc.getHeight() / 2 + DeadReckoningGame.tileSize/2;
		timeOn=0;
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
		if(actions.size()==0){
			while(!gb.ingameEntities.get(currentEntity).isInteractive() ){
				currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
			}
			
			Entity current = gb.ingameEntities.get(currentEntity);
			
			if (current.getLocation().isVisible() && current.getLocation().lightLevel>0 && actions.size()==0 && timeOn>500){
				int nx = current.getAbsoluteX() - gc.getWidth() / 2 + DeadReckoningGame.tileSize/2;
				int ny = current.getAbsoluteY() - gc.getHeight() / 2 + DeadReckoningGame.tileSize/2;
				if	( Math.abs( cameraDestX-nx )>gc.getWidth()/3 ){
					cameraDestX = nx;
					timeOn=0;
				}
				if( Math.abs( cameraDestY-ny )>gc.getHeight()/3 ){
					cameraDestY = ny;
					timeOn=0;
				}
				
			}
			
			timeOn+=delta;
			if(Math.abs(cameraDestX-cameraX) <=0.1  && Math.abs(cameraDestY-cameraY) <= 0.1){
				Action a = current.chooseAction(gc, delta);
				if(a!=null){
					actions.addAll(current.advanceTurn());
					actions.add(a);
				}
			}
			
		}
		
		if(!actionsComplete()){
			if(currentAction<actions.size()){
				actions.get(currentAction).applyAction(delta);
				if(actions.get(currentAction).completed){
					setMessage(actions.get(currentAction).getMessage());
					currentAction++;
				}
			}
			if(currentAction==actions.size() && gb.isIdle() || !gb.ingameEntities.get(currentEntity).isInteractive()){
				currentAction=0;
				currentEntity = (currentEntity + 1) % gb.ingameEntities.size();
				updateBoardEffects(gc, delta);
				gb.clearHighlightedSquares();
				gb.clearPrimaryHighlight();
				actions.clear();
				input.clearKeyPressedRecord();
			}
		}
	}

	private boolean actionsComplete() {
		for(int i=0; i<actions.size();i++){
			if(!actions.get(i).completed){
				return false;
			}
		}
		if(actions.size()>0){
			return false;
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
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String newMes){
		this.message=newMes;
	}

	public void clearMessage() {
		this.message=null;
	}
	
}
