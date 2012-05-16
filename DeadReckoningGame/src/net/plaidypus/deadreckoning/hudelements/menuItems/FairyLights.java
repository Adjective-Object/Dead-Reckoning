package net.plaidypus.deadreckoning.hudelements.menuItems;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class FairyLights.
 */
public class FairyLights extends HudElement {

	/** The num particles. */
	int width, height, numParticles;
	
	/** The particle sheet. */
	SpriteSheet particleSheet;
	
	/** The particles. */
	private float[][] particles;

	/**
	 * Instantiates a new fairy lights.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bindMethod the bind method
	 * @param width the width
	 * @param height the height
	 * @param numParticles the num particles
	 * @param particles the particles
	 */
	public FairyLights(int x, int y, int bindMethod, int width, int height,
			int numParticles, SpriteSheet particles) {
		super(x, y, bindMethod, false);
		this.width = width;
		this.height = height;
		this.numParticles = numParticles;

		this.particleSheet = particles;
		this.particles = new float[numParticles][4];// PARTICLE_ID,X,Y,xMomentum,yMomentum,OPACITY,ROTATION

		for (int i = 0; i < numParticles; i++) {
			this.particles[i] = new float[] {
					Utilities.randInt(0, particles.getVerticalCount()
							* particles.getHorizontalCount()),
					Utilities.randFloat() * this.width,
					Utilities.randFloat() * this.height, 0F, 0F,
					Utilities.randFloat(), Utilities.randFloat() * 360F };
		}

	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for (int i = 0; i < particles.length; i++) {
			particles[i][3] += (Utilities.randFloat() - Utilities.randFloat())*0.01;// Momentum
			particles[i][4] += (Utilities.randFloat() - Utilities.randFloat())*0.01;

			particles[i][1] += particles[i][3] * 5 * delta / 1000;// Movement
			particles[i][2] += particles[i][4] * 5 * delta / 1000;

			if (particles[i][1] < 0 || particles[i][1] > width) {// Bounding
				particles[i][3] = 0;
			}
			if (particles[i][2] < 0 || particles[i][2] > height) {
				particles[i][4] = 0;
			}
			particles[i][1] = Utilities.limitTo(particles[i][1], 0, width);
			particles[i][2] = Utilities.limitTo(particles[i][2], 0, height);

			particles[i][6] = (particles[i][2] + Utilities.randFloat() - Utilities
					.randFloat() * delta / 1000) % 360;// Rotation

			particles[i][5] = Utilities.limitTo(
					particles[i][5]
							* (1 + Utilities.randFloat() / 30 - Utilities
									.randFloat() / 30), 0, 1);// flicker

		}
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
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for (int i = 0; i < particles.length; i++) {
			Image img = this.particleSheet.getSprite(
					(int) particles[i][0]
							% this.particleSheet.getHorizontalCount(),
					(int) particles[i][0]
							/ this.particleSheet.getVerticalCount())
					.getFlippedCopy(false, false);
			img.setAlpha(particles[i][5]);
			img.rotate(particles[i][6]);
			g.drawImage(img, this.getX() + particles[i][1], this.getY()
					+ particles[i][2]);
		}
	}

}
