/*
 * 
 */
package net.plaidypus.deadreckoning;

import java.util.Random;

import net.plaidypus.deadreckoning.board.Tile;

/**
 * The Class Utilities. contains some basic self-explanatory methods that are
 * used around the game (easy random generation, basic math, number range
 * limits, etc.)
 */
public class Utilities {

	/** The random that is used to seed the rest of the game. */
	private static Random r = new Random();

	/**
	 * gets the Fractional Part of the float passed.
	 * 
	 * @param frac
	 *            the number
	 * @return the fPart
	 */
	public static float fPart(float frac) {
		return frac - (int) frac;
	}

	/**
	 * Gets the distance from tile A to tile B. uses pythag.
	 * 
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return the distance
	 */
	public static double getDistance(Tile a, Tile b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2)
				+ Math.pow(a.getY() - b.getY(), 2));
	}

	/**
	 * Random int generation.
	 * 
	 * @param lower
	 *            the lower
	 * @param higher
	 *            the higher
	 * @return the int
	 */
	public static int randInt(int lower, int higher) {
		return r.nextInt(higher - lower) + lower;
	}

	/**
	 * Random float generation, between 0 and 1.
	 * 
	 * @return the float
	 */
	public static float randFloat() {
		return r.nextFloat();
	}

	/**
	 * parses boolean as +1 or -1 for use in simple stuff
	 * 
	 * @param b
	 *            the boolean
	 * @return +=1
	 */
	public static int booleanPlusMin(boolean b) {
		if (b) {
			return 1;
		}
		return -1;
	}

	/**
	 * converts int into boolean, a la python (1=True, 0=False).
	 * 
	 * @param b
	 *            the b
	 * @return the double
	 */
	public static double intBool(boolean b) {
		if (b) {
			return 1;
		}
		return 0;
	}

	/**
	 * Limits the # between the upper and lower bound.
	 * 
	 * @param toLimit
	 *            the to limit
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 * @return the limited #
	 */
	public static int limitTo(int toLimit, int lowerBound, int upperBound) {
		if (toLimit < lowerBound) {
			return lowerBound;
		}
		if (toLimit >= upperBound) {
			return upperBound - 1;
		}
		return toLimit;
	}

	/**
	 * Limits the # between the upper and lower bound.
	 * 
	 * @param toLimit
	 *            the to limit
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 * @return the limited #
	 */
	public static float limitTo(float toLimit, float lowerBound,
			float upperBound) {
		if (toLimit < lowerBound) {
			return lowerBound;
		}
		if (toLimit > upperBound) {
			return upperBound;
		}
		return toLimit;
	}

	/**
	 * Move towards a destination, capping at a certain distance.
	 * 
	 * @param x
	 *            the starting x
	 * @param y
	 *            the starting y
	 * @param TargetX
	 *            the destination x
	 * @param TargetY
	 *            the destination y
	 * @param movespeed
	 *            the maximum distance to travel
	 * @return an int[] with the resulting coordinates [x,y]
	 */
	public static int[] moveTowards(int x, int y, int TargetX, int TargetY,
			int movespeed) {
		if (Math.sqrt(Math.pow(x - TargetX, 2) + Math.pow(y - TargetY, 2)) <= movespeed) {
			double[] v = rotate(movespeed, 0, findAngle(x, y, TargetX, TargetY));

			x += v[0];
			y += v[1];
		} else {
			x = TargetX;
			y = TargetY;
		}
		return new int[] { x, y };
	}

	/**
	 * Rotates a line starting at the origin and ending at x,y by a certain
	 * angle (in radians).
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param angle
	 *            the angle
	 * @return the resulting vector [x,y]
	 */
	public static double[] rotate(double x, double y, double angle) {
		return new double[] { x * Math.cos(angle) - y * Math.sin(angle),
				x * Math.sin(angle) + y * Math.cos(angle) };
	}

	//
	/**
	 * Find angle.
	 * 
	 * this is derptacular and doesn't really work, but it's ok for now it's
	 * meant to find the angle in radians from the origin (x,y) to the
	 * destination(destx,desty)
	 * 
	 * @param x
	 *            the start x
	 * @param y
	 *            the start y
	 * @param destx
	 *            the destination x
	 * @param desty
	 *            the destination y
	 * @return the angle between the vertical and the vector drawn to the X,Y,
	 *         in radians
	 */
	public static double findAngle(double x, double y, double destx,
			double desty) {
		double anglepart = Math.atan((y - desty) / (x - destx));
		if (destx < x) {
			anglepart += Math.PI;
		}
		return anglepart;
	}

	/**
	 * Round up.
	 * 
	 * @param target
	 *            the target
	 * @return the int
	 */
	public static int roundUp(float target) {
		if (fPart(target) > 0) {
			return (int) (target) + 1;
		}
		return (int) (target);
	}

	/**
	 * Round down.
	 * 
	 * @param target
	 *            the target
	 * @return the int
	 */
	public static int roundDown(float target) {
		return (int) (target);
	}

	/**
	 * Round up.
	 * 
	 * @param d
	 *            the d
	 * @return the int
	 */
	public static int roundUp(double d) {
		return roundUp((float) d);
	}

	/**
	 * Round down.
	 * 
	 * @param d
	 *            the d
	 * @return the int
	 */
	public static int roundDown(double d) {
		return roundDown((float) d);
	}
	
	public static String collapseNewlines(String s){
		return s.replaceAll("\\\\n", "\n");
	}
}
