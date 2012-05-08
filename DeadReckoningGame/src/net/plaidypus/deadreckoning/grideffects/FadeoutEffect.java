package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * The Class FadeoutEffect.
 * 
 * Fades out an image over a certain tile
 * 
 * used to make deletion of objects better looking
 */
public class FadeoutEffect extends GridEffect {

	/** The alpha. */
	float alpha;
	
	/** The image. */
	Image image;

	/**
	 * Instantiates a new fadeout effect.
	 *
	 * @param location the location
	 * @param image the image
	 */
	public FadeoutEffect(Tile location, Image image) {
		super(location);
		this.image = image;
		alpha = 255;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#update(int)
	 */
	@Override
	public void update(int delta) {
		alpha -= (delta / 4);
		if (alpha <= 0) {
			this.setComplete(true);
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#render(org.newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void render(Graphics g, float xOff, float yOff) {
		image.setAlpha(alpha / 255);
		g.drawImage(image, location.getX() * DeadReckoningGame.tileSize + xOff,
				location.getY() * DeadReckoningGame.tileSize + yOff);
		image.setAlpha(1);
	}

}
