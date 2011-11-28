package net.plaidypus.deadreckoning.professions;

import java.util.ArrayList;

public abstract class Profession {
	
	static final int PROFESSION_MAGICIAN = 0;
	
	private ArrayList<SkillProgression> skills;
	private int[][] statProgression;
	
	private static ArrayList<Profession> professions;
	
	Profession( ArrayList<SkillProgression> skills, int[][] statProg){
		this.skills=skills;
		this.statProgression=statProg;
	}
	
	public static void init(){
		professions = new ArrayList<Profession>(0);
		ArrayList<SkillProgression> mageSkills;
		int[][] mageStats;
		professions.add( new Profession(mageSkills, mageStats) );
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
	public int getAGI(int level){
		return getStatProgression()[4][level];
	}
	
	protected int[][] getStatProgression(){
		return statProgression;
	}
	
	
	public ArrayList<SkillProgression> getSkillProgression(){
		return skills;
	}
}
