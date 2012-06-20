package net.plaidypus.deadreckoning.hudelements.game.substates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.game.MiniMap;

public class BigMap extends MiniMap{
	
	public BigMap(int x, int y, int bindMethod, GameplayElement game) {
		super(x, y, bindMethod, 3, 100, 100, game);
	}

	public void makeFrom(Object o){
		this.width=this.hookState.getBoard().getWidth()-1;
		this.height=this.hookState.getBoard().getHeight()-1;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderTo(g, getX() - (width * scale/2), getY()-(height*scale/2), width/2+1, height/2+1);

	}
	
}
