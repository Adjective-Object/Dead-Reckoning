package net.plaidypus.deadreckoning.particles;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class DamageParticle extends Particle {

	static UnicodeFont font;
	static float gravity = (float) 0.05;
	static float fadeout = (float) 0.8;
	float x, y, xMove, yMove, visibility;
	String damage;

	/**
	 * particle for displaying the damage dealt to a LivingEntity
	 * 
	 * @param x
	 * @param y
	 * @param damage
	 */
	public DamageParticle(int x, int y, String damage) {
		super();
		this.x = x;
		this.y = y;
		this.xMove = 0;
		this.yMove = -1;
		this.visibility = (float) 1.0;
		this.damage = damage;
	}

	/**
	 * easy constructor for making DamageParticles off of the tile system
	 * 
	 * @param t
	 * @param damage
	 */
	public DamageParticle(Tile t, String damage) {
		this(t.getX() * DeadReckoningGame.tileSize
				+ Utilities.randInt(0, DeadReckoningGame.tileSize / 2), t
				.getY()
				* DeadReckoningGame.tileSize
				+ Utilities.randInt(0, DeadReckoningGame.tileSize / 2), damage);
	}

	/**
	 * initializes the variables needed for damageparticle (called once, in
	 * StatebasedGame.init())
	 * 
	 * @throws SlickException
	 */
	public static void init() throws SlickException {
		font = new UnicodeFont("res/visitor.ttf", 7, false, false);
	}

	/**
	 * updates the damage particle (gravity, momentum, you know the drill)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		visibility = visibility * fadeout;
		yMove += gravity;
		x += xMove;
		y += yMove;

		if (this.visibility <= 0.00001) {
			this.toKill = true;
		}

	}

	/**
	 * renders the damage particle onto a graphics object with x and y offsets
	 * (camera X and camera Y, usually)
	 */
	public void render(Graphics g, float xOff, float yOff) {
		g.setColor(new Color(200, 0, 0, 255 * visibility));
		g.drawString(damage, x + xOff, y + yOff);

	}
}
