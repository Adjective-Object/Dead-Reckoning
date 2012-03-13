package net.plaidypus.deadreckoning.professions;

public class StatMaster {
	
	protected int mHP, mMP, STR, DEX, INT, LUK;
	
	public StatMaster(int hp, int mp, int str, int dex, int INT, int luk){
		this.mHP=hp;
		this.mHP=mp;
		this.STR=str;
		this.DEX=dex;
		this.INT=INT;
		this.LUK=luk;
	}
	
	public int getDEX(){return this.DEX;}
	public int getLUK(){return this.LUK;}
	public int getSTR(){return this.STR;}
	public int getINT(){return this.INT;}
	
	public int getMagDefense(){return this.INT;}
	public int getWepDefense(){return this.STR;}
	
	public int getMaxHP() {return mHP;}
	public int getMaxMP() {return mMP;}

	public int calculateEXPValue() {
		return this.getDEX()+this.getINT()+this.getLUK()+this.getSTR();
	}
	
}
