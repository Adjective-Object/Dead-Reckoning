package net.plaidypus.deadreckoning.professions;

import net.plaidypus.deadreckoning.entities.Player;

public class Profession {
	
	static final int PROFESSION_MAGICIAN = 0;
	
	private SkillProgression[] skills;
	private int[][] statProgression;
	
	Profession( SkillProgression[] skills, int[][] statProg){
		this.skills=skills;
		this.statProgression=statProg;
	}
	
	public int getHP(int level){
		return getStatProgression()[0][level];
	}
	public int getMP(int level){
		return getStatProgression()[1][level];
	}	
	public int getSTR(int level){
		return getStatProgression()[2][level];
	}
	public int getWIS(int level){
		return getStatProgression()[3][level];
	}
	public int getLUK(int level){
		return getStatProgression()[4][level];
	}
	public int getAGI(int level){
		return getStatProgression()[5][level];
	}
	
	protected int[][] getStatProgression(){
		return statProgression;
	}
	
	public SkillProgression[] getSkillProgression(){
		return skills;
	}
}
