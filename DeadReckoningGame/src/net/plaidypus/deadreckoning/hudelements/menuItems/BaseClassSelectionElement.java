package net.plaidypus.deadreckoning.hudelements.menuItems;

import java.io.File;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseClassSelectionElement.
 */
public class BaseClassSelectionElement extends HudElement {

	/** The current class. */
	int currentClass = 0;

	/** The num classes. */
	int numClasses;

	/** The icon image. */
	Image iconImage;

	/**
	 * Instantiates a new base class selection element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @throws SlickException
	 *             the slick exception
	 */
	public BaseClassSelectionElement(int x, int y, int bindMethod)
			throws SlickException {
		super(x, y, bindMethod, true);
		numClasses = enumerateClasses();
		iconImage = new Image("res/professions/" + currentClass
				+ "/Portrait.png");
	}

	/**
	 * Enumerate classes.
	 * 
	 * @return the int
	 */
	private int enumerateClasses() {
		int numClass = 0;
		File f = new File("res/professions/" + numClass);
		while (f.exists()) {
			numClass++;
			f = new File("res/professions/" + numClass);
		}
		return numClass;
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
		if (this.hasFocus) {
			if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
				currentClass = (currentClass + numClasses - 1) % numClasses;
				iconImage = new Image("res/professions/" + currentClass
						+ "/Portrait.png");
			}
			if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
				currentClass = (currentClass + 1) % numClasses;
				iconImage = new Image("res/professions/" + currentClass
						+ "/Portrait.png");
			}
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
		return iconImage.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return iconImage.getHeight();
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
		g.drawImage(iconImage, getAbsoluteX(), getAbsoluteY());
	}

}
