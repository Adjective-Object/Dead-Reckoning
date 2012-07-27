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

	/** The display exp value. */
	float displayHPValue, displayMPValue, displayEXPValue;

	/**
	 * Instantiates a new player hud element.
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
	public PlayerHudElement(int x, int y, int bindMethod, GameplayElement hookElement) {
		super(x, y, bindMethod, false);
		this.target = hookElement;
		displayHPValue=0;
		displayMPValue=0;
		displayEXPValue=0;
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
		
		displayHPValue += ((float)target.player.HP/target.player.getMaxHP()-displayHPValue)*0.2;
		displayMPValue += ((float)target.player.MP/target.player.getMaxMP()-displayMPValue)*0.2;
		displayEXPValue += ((float)target.player.EXP/target.player.getEXPforLevel() - displayEXPValue)*0.2;
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
		img = new Image("res/HUD/PlayerBox.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return img.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return img.getHeight();
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
		g.setFont(DeadReckoningGame.menuSmallFont);
		g.drawImage(img, getAbsoluteX(), getAbsoluteY());
		g.drawImage(target.player.getProfession().getPortriat(), getAbsoluteX() + 19,
				getAbsoluteY() + 23);
		g.setColor(new Color(200, 70, 70));
		g.fillRect(getAbsoluteX() + 126, getAbsoluteY() + 25, 75 * displayHPValue, 9);
		g.setColor(new Color(70, 70, 200));
		g.fillRect(getAbsoluteX() + 126, getAbsoluteY() + 49, 75 * displayMPValue, 9);
		g.setColor(new Color(200, 200, 70));
		g.fillRect(getAbsoluteX() + 126, getAbsoluteY() + 74, 75 * displayEXPValue, 9);
		g.setColor(DeadReckoningGame.menuTextColor);
		g.drawString(target.player.HP + "/"
				+ target.player.getProfession().getMaxHP(), getAbsoluteX() + 128,
				getAbsoluteY() + 22);
		g.drawString(target.player.MP + "/"
				+ target.player.getProfession().getMaxMP(), getAbsoluteX() + 128,
				getAbsoluteY() + 46);
		g.drawString(
				Integer.toString(target.player.getProfession().getLevel()),
				getAbsoluteX(), getAbsoluteY() + getHeight() + 20);
		g.drawString(Integer.toString(target.getBoard().depth), getAbsoluteX(), getAbsoluteY()
				+ getHeight() + 40);
		g.drawString(Integer.toString(target.player.getX()), getAbsoluteX(), getAbsoluteY()
				+ getHeight() + 60);
		g.drawString(Integer.toString(target.player.getY()), getAbsoluteX() + 30,
				getAbsoluteY() + getHeight() + 60);
	}

}
