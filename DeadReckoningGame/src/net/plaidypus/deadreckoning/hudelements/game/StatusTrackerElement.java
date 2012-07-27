package net.plaidypus.deadreckoning.hudelements.game;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.status.Status;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusTrackerElement.
 */
public class StatusTrackerElement extends HudElement {

	/** The target. */
	GameplayElement target;

	/**
	 * Instantiates a new status tracker element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @param hookElement
	 *            the hook element
	 */
	public StatusTrackerElement(int x, int y, int bindMethod, GameplayElement hookElement) {
		super(x, y, bindMethod, false);
		this.target=hookElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input i = gc.getInput();
	
		if (i.getMouseX() > this.getAbsoluteX()
				&& i.getMouseY() > this.getAbsoluteY()
				&& i.getMouseX() < this.getAbsoluteX() + this.getWidth()
				&& i.getMouseY() < this.getAbsoluteY() + this.getHeight()) {
			int rx = (i.getMouseX() - this.getAbsoluteX()) / (DeadReckoningGame.tileSize +6);
			Status x = target.player.getConditions().get(rx);
			this.setMouseoverText(x.getName()+"\n"+x.description+"\n"+x.getDuration()+" turns left");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	@Override
	public void makeFrom(Object o) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return target.player.statuses.size() * 38;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return 36;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame,
	 * org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for (int i = 0; i < target.player.statuses.size(); i++) {
			int x=getAbsoluteX() + i * 37, y = getAbsoluteY() + 2;
			Status s=target.player.statuses.get(i);
			g.drawImage(s.tileImage, x,y);
			g.setColor(DeadReckoningGame.skillInvalidColor);
			float frac = 1F-(1F*s.getDuration()/s.getMaxDuration());
			g.fillRect(x+32*(1-frac)/2, y+32*(1-frac)/2, 32*frac, 32*frac);
			g.setColor(DeadReckoningGame.menuTextColor);
			g.setFont(DeadReckoningGame.menuDispFont);
			g.drawString(Integer.toString(s.getDuration()),
					x+16-g.getFont().getWidth (Integer.toString(s.getDuration()))/2,
					y+16-g.getFont().getHeight(Integer.toString(s.getDuration()))/2);
		}
	}

}
