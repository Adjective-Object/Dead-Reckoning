package net.plaidypus.deadreckoning.professions;

public class StatMaster {

	protected int mHP, mMP, STR, DEX, INT, LUK, level;

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

	public int getDEX() {
		return this.DEX;
	}

	public int getLUK() {
		return this.LUK;
	}

	public int getSTR() {
		return this.STR;
	}

	public int getINT() {
		return this.INT;
	}

	public int getMagDefense() {
		return this.INT;
	}

	public int getWepDefense() {
		return this.STR;
	}

	public int getMaxHP() {
		return mHP;
	}

	public int getMaxMP() {
		return mMP;
	}

	public int calculateEXPValue() {
		return this.getDEX() + this.getINT() + this.getLUK() + this.getSTR();
	}

	public String toString() {
		return mHP + ":" + mMP + ":" + STR + ":" + DEX + ":" + INT + ":" + LUK
				+ ":" + level;
	}

	public int getLevel() {
		return this.level;
	}

}
