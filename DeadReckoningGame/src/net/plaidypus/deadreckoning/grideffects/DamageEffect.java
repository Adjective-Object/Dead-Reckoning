package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

/**
 * The Class DamageEffect.
 * 
 * displays damage numbers when someone attacks created by AttackAction
 * 
 * @see net.plaidypus.deadreckoning.actions.AttackAction
 */
public class DamageEffect extends GridEffect {

	/** The font. */
	static UnicodeFont font;

	/** The "gravity". */
	static final float gravity = (float) 0.05;

	/** The fade out time. */
	static final float fadeout = (float) 0.8;

	/**
	 * The variables that control individual behavior (random X movement,
	 * mostly)
	 */
	float x, y, xMove, yMove, visibility;

	/** The damage string to display */
	String damage;

	/**
	 * easy constructor for making DamageParticles off of the tile system.
	 * 
	 * @param t
	 *            the t
	 * @param damage
	 *            the damage
	 */
	public DamageEffect(Tile t, String damage) {
		super(t);
		this.x = Utilities.randInt(0, DeadReckoningGame.tileSize);
		this.y = Utilities.randInt(0, DeadReckoningGame.tileSize);
		this.xMove = 0;
		this.yMove = -1;
		this.visibility = (float) 1.0;
		this.damage = damage;
	}

	/**
	 * initializes the variables needed for damageparticle (called once, in
	 * StatebasedGame.init())
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public static void init() throws SlickException {
		font = new UnicodeFont("res/visitor.ttf", 7, false, false);
	}

	/**
	 * updates the damage particle (gravity, momentum, you know the drill).
	 * 
	 * @param delta
	 *            the delta
	 */
	public void update(int delta) {
		visibility = visibility * fadeout;
		yMove += gravity;
		x += xMove;
		y += yMove;

		if (this.visibility <= 0.00001) {// basically this provides a visibility
											// cutoff to delete the effect when
											// it is no longer visible
			this.setComplete(true);
		}

	}

	/**
	 * renders the damage particle onto a graphics object with x and y offsets
	 * (camera X and camera Y, usually).
	 * 
	 * @param g
	 *            the g
	 * @param xOff
	 *            the x off
	 * @param yOff
	 *            the y off
	 */
	public void render(Graphics g, float xOff, float yOff) {
		g.setColor(new Color(200, 0, 0, 255 * visibility));
		g.drawString(damage,
				location.getX() * DeadReckoningGame.tileSize -g.getFont().getWidth(damage) /2 + x + xOff,
				location.getY() * DeadReckoningGame.tileSize -g.getFont().getHeight(damage)/2 + y + yOff);
	}
}
