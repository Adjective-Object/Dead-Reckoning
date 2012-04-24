package net.plaidypus.deadreckoning.professions;

// TODO: Auto-generated Javadoc
/**
 * The Class StatMaster.
 * 
 * Statmastes are used to manage the stats of livingentities.
 * while yes, it WOULD be simpler just to put that in the livingentitiy class,
 * this encapsulates it to simplify thinking, and allows for the creation of
 * custom statmasters later on
 * 
 * @see net.plaidypus.deadreckoning.professions.Profession
 */
public class StatMaster {

	/** The internal stats */
	protected int mHP, mMP, STR, DEX, INT, LUK, level;

	/**
	 * Instantiates a new stat master.
	 *
	 * @param hp the hp
	 * @param mp the mp
	 * @param str the str
	 * @param dex the dex
	 * @param INT the iNT
	 * @param luk the luk
	 * @param level the level
	 */
	public StatMaster(int hp, int mp, int str, int dex, int INT, int luk,
			int level) {
		this.mHP = hp;
		this.mMP = mp;
		this.STR = str;
		this.DEX = dex;
		this.INT = INT;
		this.LUK = luk;
		this.level = level;
	}

	/**
	 * Gets the dEX.
	 *
	 * @return the dEX
	 */
	public int getDEX() {
		return this.DEX;
	}

	/**
	 * Gets the lUK.
	 *
	 * @return the lUK
	 */
	public int getLUK() {
		return this.LUK;
	}

	/**
	 * Gets the sTR.
	 *
	 * @return the sTR
	 */
	public int getSTR() {
		return this.STR;
	}

	/**
	 * Gets the iNT.
	 *
	 * @return the iNT
	 */
	public int getINT() {
		return this.INT;
	}

	/**
	 * Gets the mag defense.
	 *
	 * @return the mag defense
	 */
	public int getMagDefense() {
		return this.INT;
	}

	/**
	 * Gets the wep defense.
	 *
	 * @return the wep defense
	 */
	public int getWepDefense() {
		return this.STR;
	}

	/**
	 * Gets the max hp.
	 *
	 * @return the max hp
	 */
	public int getMaxHP() {
		return mHP;
	}

	/**
	 * Gets the max mp.
	 *
	 * @return the max mp
	 */
	public int getMaxMP() {
		return mMP;
	}

	/**
	 * Calculate exp value.
	 *
	 * @return the int
	 */
	public int calculateEXPValue() {
		return this.getDEX() + this.getINT() + this.getLUK() + this.getSTR();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return mHP + ":" + mMP + ":" + STR + ":" + DEX + ":" + INT + ":" + LUK
				+ ":" + level;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return this.level;
	}

}
