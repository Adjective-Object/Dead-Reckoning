package net.plaidypus.deadreckoning.hudelements.game.substates;

import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.game.MiniMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BigMap extends MiniMap{
	
	public BigMap(int x, int y, int bindMethod, GameplayElement game) {
		super(x, y, bindMethod, 3, 100, 100, game);
	}

	@Override
	public void makeFrom(Object o){
		this.width=this.hookState.getBoard().getWidth()-1;
		this.height=this.hookState.getBoard().getHeight()-1;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderTo(g, getAbsoluteX() - (width * scale/2), getAbsoluteY()-(height*scale/2), width/2+1, height/2+1);

	}
	
	@Override
	public void onEnter(GameContainer container, StateBasedGame game){
		this.makeFrom(null);
	}
	
}
