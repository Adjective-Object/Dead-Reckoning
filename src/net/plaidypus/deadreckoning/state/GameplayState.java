package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Goblin;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.particles.DamageParticle;
import net.plaidypus.deadreckoning.particles.Particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState
{
	
	int stateID = -1;
	int currentEntity;
	
	Input input;
	
	GameBoard gb;
	
	public static ArrayList<Particle> particles;
	
	public GameplayState(int stateID) throws SlickException
	{
		this.stateID = stateID;
		currentEntity=0;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input=gc.getInput();
		
		DamageParticle.init();
		particles=new ArrayList<Particle>(0);
		
		gc.setTargetFrameRate(60);
		gc.setVSync(true);
		gb = new GameBoard(25,25);
		gb.init();
		gb.placeEntity(0, 0, new Player(gc.getInput()));
		gb.placeEntity(24, 1, new Goblin());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gb.render(gc,sbg,g);
		for(int i=0; i<particles.size();i++){
			particles.get(i).render(gc,sbg,g);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		for(int i=0; i<particles.size();i++){
			particles.get(i).update(gc, sbg, delta);
			if(particles.get(i).toKill){
				particles.remove(i);
				i--;
			}
		}
		
		Entity current = gb.ingameEntities.get(currentEntity);
		if(current.isIdle()){
			current.setAction(current.chooseAction(gc,delta));
		}
		if(!current.isIdle()){
			current.applyAction(gc, delta);
			if(current.isIdle()){
				currentEntity = (currentEntity+1)%gb.ingameEntities.size();
			}
		}
		
		gb.updateSelctor(input);
		gb.updateAllTiles(gc, delta);
		
	}

	public int getID() {
		return stateID;
	}
	
	public static void spawnParticle(Particle p){
		particles.add(p);
	}
	
}
