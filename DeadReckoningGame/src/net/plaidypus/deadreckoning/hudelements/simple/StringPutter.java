package net.plaidypus.deadreckoning.hudelements.simple;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class StringPutter.
 */
public class StringPutter extends HudElement {

	static int maxMessages = 4;

	/** The alphas. */
	private ArrayList<Double> alphas;

	/** The messages. */
	private ArrayList<String> messages;

	/** The fadeout rate. */
	private int fadeoutRate;

	/**
	 * Instantiates a new string putter.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @param fadeoutRate
	 *            the fadeout rate
	 */
	public StringPutter(int x, int y, int bindMethod, int fadeoutRate) {
		super(x, y, bindMethod, false);
		this.fadeoutRate = fadeoutRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		alphas = new ArrayList<Double>(0);
		messages = new ArrayList<String>(0);
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
		for (int i = 0; i < alphas.size(); i++) {
			alphas.set(i, alphas.get(i) - fadeoutRate * (delta / 1000F));
			if (i < alphas.size() - maxMessages) {
				alphas.set(i, alphas.get(i) - alphas.get(i) * (delta / 250F));
			}
			if (alphas.get(i) <= 0) {
				alphas.remove(i);
				messages.remove(i);
				i--;
			}
		}
	}

	/**
	 * Adds the message.
	 * 
	 * @param string
	 *            the string
	 */
	public void addMessage(String string) {
		messages.add(string);
		alphas.add(255.0);
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
		for (int i = 0; i < messages.size(); i++) {
			g.setFont(DeadReckoningGame.menuSmallFont);
			g.setColor(new Color(255, 255, 255, alphas.get(i).intValue()));
			g.drawString(messages.get(i), this.getX(),
					this.getY() - ((messages.size() - i) * 25));
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
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return 0;
	}

}
