package net.plaidypus.deadreckoning.hudelements.button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageButton.
 */
public class ImageButton extends Button {

	/** The image. */
	Image image;
	
	/** The alt image. */
	Image altImage;

	/**
	 * Instantiates a new image button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bindMethod the bind method
	 * @param image the image
	 */
	public ImageButton(int x, int y, int bindMethod, Image image) {
		this(x, y, bindMethod, image, null);
	}

	/**
	 * Instantiates a new image button.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bindMethod the bind method
	 * @param image the image
	 * @param altimage the altimage
	 */
	public ImageButton(int x, int y, int bindMethod, Image image, Image altimage) {
		super(x, y, bindMethod);
		this.image = image;
		this.altImage = altimage;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.button.Button#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang.Object)
	 */
	@Override
	public void makeFrom(Object o) {
		this.image = (Image) (o);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	public int getWidth() {
		if (image != null) {
			return image.getWidth();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		if (image != null) {
			return image.getHeight();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		if (!moused) {
			g.drawImage(image, getX(), getY());
		}
		if (moused) {
			if (altImage == null) {
				g.drawImage(image, getX(), getY());
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			} else {
				g.drawImage(altImage, getX(), getY());
			}
		} else if (pressed) {
			g.setColor(new Color(255, 255, 255, 255));
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}

}
