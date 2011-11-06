package net.plaidypus.deadreckoning.particles;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Particle {
	
	public boolean toKill;
	
	public Particle(){
		toKill=false;
	}
	
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta);
	
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g);
}
