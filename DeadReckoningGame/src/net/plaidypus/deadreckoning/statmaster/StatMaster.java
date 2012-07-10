package net.plaidypus.deadreckoning.statmaster;

import net.plaidypus.deadreckoning.Utilities;

// TODO: Auto-generated Javadoc
/**
 * The Class StatMaster.
 * 
 * Statmastes are used to manage the stats of livingentities. while yes, it
 * WOULD be simpler just to put that in the livingentitiy class, this
 * encapsulates it to simplify thinking, and allows for the creation of custom
 * statmasters later on
 * 
 * custom stat calculation is done with formulas roughly adapted from
 * Maplestory, Immediately after the Big Bang update, or from League of Legends,
 * shortly after the varus patch.
 * 
 * @see net.plaidypus.deadreckoning.statmaster.Profession
 */
public class StatMaster {

	/** The internal stats */
	protected int mHP, mMP, STR, DEX, INT, LUK, level, dodge;

	private int crit;

	protected int modHP, modMP, modSTR, modDEX, modINT, modLUK, modAtt, modDef,
			modMagAtt, modMagDef;

	/**
	 * Instantiates a new stat master.
	 * 
	 * @param hp
	 *            the hp
	 * @param mp
	 *            the mp
	 * @param str
	 *            the str
	 * @param dex
	 *            the dex
	 * @param INT
	 *            the iNT
	 * @param luk
	 *            the luk
	 * @param level
	 *            the level
	 */
	public StatMaster(int hp, int mp, int str, int dex, int INT, int luk,
			int level) {
		this.mHP = hp;
		this.mMP = mp;
		this.STR = str;
		this.DEX = dex;
		this.INT = INT;
		this.LUK = luk;
		resetStatBonuses();
		this.level = level;
	}

	public int getRawMaxHP() {
		return this.mHP;
	}

	public int getRawMaxMP() {
		return this.mMP;
	}

	/**
	 * Gets the dEX.
	 * 
	 * @return the dEX
	 */
	public int getRawDEX() {
		return this.DEX;
	}

	/**
	 * Gets the lUK.
	 * 
	 * @return the lUK
	 */
	public int getRawLUK() {
		return this.LUK;
	}

	/**
	 * Gets the sTR.
	 * 
	 * @return the sTR
	 */
	public int getRawSTR() {
		return this.STR;
	}

	/**
	 * Gets the iNT.
	 * 
	 * @return the iNT
	 */
	public int getRawINT() {
		return this.INT;
	}

	/**
	 * Gets the dEX.
	 * 
	 * @return the dEX
	 */
	public int getDEX() {
		return this.getRawDEX() + this.modDEX;
	}

	/**
	 * Gets the lUK.
	 * 
	 * @return the lUK
	 */
	public int getLUK() {
		return this.getRawLUK() + this.modLUK;
	}

	/**
	 * Gets the sTR.
	 * 
	 * @return the sTR
	 */
	public int getSTR() {
		return this.getRawSTR() + this.modSTR;
	}

	/**
	 * Gets the iNT.
	 * 
	 * @return the iNT
	 */
	public int getINT() {
		return this.getRawLUK() + this.modINT;
	}

	/**
	 * Gets the Magical defense.
	 * 
	 * 
	 * @return the Magic defense
	 */
	public int getMagDefense() {
		return (int) (1.2F * this.getINT() + 0.5F * this.getDEX() + 0.5F
				* this.getLUK() + 0.4F * this.getSTR() + this.modMagDef);
	}

	/**
	 * Gets the wep defense.
	 * 
	 * @return the wep defense
	 */
	public int getWepDefense() {
		return (int) (0.4F * this.getINT() + 0.5F * this.getDEX() + 0.5F
				* this.getLUK() + 1.2F * this.getSTR() + this.modDef);
	}

	/**
	 * Gets the max hp.
	 * 
	 * @return the max hp
	 */
	public int getMaxHP() {
		return this.getRawMaxHP() + modHP;
	}

	/**
	 * Gets the max mp.
	 * 
	 * @return the max mp
	 */
	public int getMaxMP() {
		return this.getRawMaxMP() + modMP;
	}

	/**
	 * Calculate exp value.
	 * 
	 * @return the int
	 */
	public int calculateEXPValue() {
		return this.getDEX() + this.getINT() + this.getLUK() + this.getSTR();
	}

	public void editHP(int modValue) {
		this.modHP += modValue;
	}

	public void editMP(int modValue) {
		this.modMP += modValue;
	}

	public void editSTR(int modValue) {
		this.modSTR += modValue;
	}

	public void editDEX(int modValue) {
		this.modDEX += modValue;
	}

	public void editINT(int modValue) {
		this.modINT += modValue;
	}

	public void editLUK(int modValue) {
		this.modLUK += modValue;
	}

	public void editAtt(int modValue) {
		this.modAtt += modValue;
	}

	public void editDef(int modValue) {
		this.modDef += modValue;
	}

	public void editMAtt(int modValue) {
		this.modMagAtt += modValue;
	}

	public void editMDef(int modValue) {
		this.modMagDef += modValue;
	}

	/**
	 * resets the bonuses on base and other stats to 0
	 */
	public void resetStatBonuses() {
		this.modHP = 0;
		this.modMP = 0;
		this.modSTR = 0;
		this.modDEX = 0;
		this.modINT = 0;
		this.modLUK = 0;
		this.modAtt = 0;
		this.modMagAtt = 0;
		this.modDef = 0;
		this.modMagDef = 0;
		this.dodge=0;
		this.setCrit(0);
	}

	public int getPhysicalDamageTo(int rawDamageValue) {
		return (int) (rawDamageValue * (100F / (100 + this.getWepDefense())));
	}

	public int getMagicalDamageTo(int rawDamageValue) {
		return (int) (rawDamageValue * (100F / (100 + this.getMagDefense())));
	}
	
	public int getPhysicalDamageFrom(){
		int x = this.getSTR();
		if(Utilities.randFloat()<=this.getCrit()/100F){
			x=2*x;
		}
		return x;
	}
	
	/**
	 * Gets the level of the creature this covers.
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return this.level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return mHP + "," + mMP + "," + STR + "," + DEX + "," + INT + "," + LUK
				+ "," + level;
	}

	public int getBonusMaxHP() {
		return modHP;
	}

	public int getBonusMaxMP() {
		return modMP;
	}

	public int getBonusSTR() {
		return modSTR;
	}

	public int getBonusINT() {
		return modINT;
	}

	public int getBonusDEX() {
		return modDEX;
	}

	public int getBonusLUK() {
		return modLUK;
	}
	
	public int getDodge() {
		return this.dodge;
	}
	
	/**
	 * dodge does not stack.
	 * Higher values will be kept.
	 * Must be re-assigned every turn.
	 * 
	 * @param value
	 * @return
	 */
	public void setDodge(int value) {
		if (value>this.dodge){
			this.dodge=value;
		}
	}
	
	public float getDodgeChance(){
		return 100F/(100+this.dodge); 
	}

	public int getCrit() {
		return crit;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}


}
