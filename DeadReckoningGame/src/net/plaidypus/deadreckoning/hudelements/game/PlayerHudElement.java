package net.plaidypus.deadreckoning.hudelements.game;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerHudElement.
 */
public class PlayerHudElement extends HudElement {

	/** The target. */
	GameplayElement target;
	
	/** The img. */
	Image img;
	
	/** The hook element. */
	int hookElement;
	
	/** The display exp value. */
	float displayHPValue, displayMPValue, displayEXPValue;

	/**
	 * Instantiates a new player hud element.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bindMethod the bind method
	 * @param hookElement the hook element
	 */
	public PlayerHudElement(int x, int y, int bindMethod, int hookElement) {
		super(x, y, bindMethod, false);
		this.hookElement = hookElement;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		displayHPValue += (target.player.HP - displayHPValue) * (float) (delta)
				/ 200;
		displayMPValue += (target.player.MP - displayMPValue) * (float) (delta)
				/ 200;
		displayEXPValue += (target.player.EXP - displayEXPValue)
				* (float) (delta) / 200;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang.Object)
	 */
	@Override
	public void makeFrom(Object o) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		img = new Image("res/HUD/PlayerBox.png");
		target = (GameplayElement) (this.getParent().getElement(hookElement));
		displayHPValue = 0;
		displayMPValue = 0;
		displayEXPValue = 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	public int getWidth() {
		return img.getWidth();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	public int getHeight() {
		return img.getHeight();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setFont(DeadReckoningGame.menuSmallFont);
		g.drawImage(img, getX(), getY());
		g.drawImage(target.player.getProfession().getPortriat(), getX() + 19,
				getY() + 23);
		g.setColor(new Color(200, 70, 70));
		g.fillRect(getX() + 126, getY() + 25, 75 * displayHPValue
				/ target.player.getMaxHP(), 9);
		g.setColor(new Color(70, 70, 200));
		g.fillRect(getX() + 126, getY() + 49, 75 * displayMPValue
				/ target.player.getMaxMP(), 9);
		g.setColor(new Color(200, 200, 70));
		g.fillRect(getX() + 126, getY() + 74, 75 * displayEXPValue
				/ target.player.getEXPforLevel(), 9);
		g.setColor(DeadReckoningGame.menuTextColor);
		g.drawString(target.player.HP+"/"+target.player.getProfession().getMaxHP(), getX()+146, getY()+22);
		g.drawString(target.player.MP+"/"+target.player.getProfession().getMaxMP(), getX()+146, getY()+46);
		g.drawString(
				Integer.toString(target.player.getProfession().getLevel()),
				getX(), getY() + getHeight() + 20);
		g.drawString(Integer.toString(target.getBoard().depth), getX(), getY()
				+ getHeight() + 40);
	}

}
