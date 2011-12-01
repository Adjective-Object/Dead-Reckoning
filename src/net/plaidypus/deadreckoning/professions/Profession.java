package net.plaidypus.deadreckoning.professions;


import net.plaidypus.deadreckoning.skills.Skill;

public class Profession {
	
	static final int PROFESSION_MAGICIAN = 0;
	
	private SkillProgression[] skills;
	private int[][] statProgression;
	
	public static Profession[] professions;
	
	Profession( SkillProgression[] skills, int[][] statProg){
		this.skills=skills;
		this.statProgression=statProg;
	}
	
	public static void init(){
		professions = new Profession[5];
		
		SkillProgression testMage =new SkillProgression( new Skill[] {}, new double[] {1.1,1.1,1.1,1.1,1.1}, PROFESSION_MAGICIAN);
		SkillProgression[] mageSkills = {testMage, testMage, testMage, testMage};
		int[][] statProgress = {} ;
		
		professions [0] = new Profession (mageSkills, statProgress);
		
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
