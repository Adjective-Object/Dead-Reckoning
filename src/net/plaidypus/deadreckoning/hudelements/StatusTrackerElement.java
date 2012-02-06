package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StatusTrackerElement extends HudElement{

	int hookElement;
	GameplayElement target;
	
	public StatusTrackerElement(int x, int y, int bindMethod, int hookElement) {
		super(x, y, bindMethod, false);
		this.hookElement=hookElement;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	@Override
	public void makeFrom(Object o) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		target = (GameplayElement)(this.getParent().getElement(hookElement));
	}

	@Override
	public int getWidth() {
		return target.player.statuses.size()*37;
	}

	@Override
	public int getHeight() {
		return 36;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for(int i=0; i<target.player.statuses.size();i++){
			g.drawImage(target.player.statuses.get(i).tileImage,getX()+i*37,getY()+2);
		}
	}

}
