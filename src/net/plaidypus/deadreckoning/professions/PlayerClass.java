package net.plaidypus.deadreckoning.professions;

import net.plaidypus.deadreckoning.skills.Skill;

public class PlayerClass {
	
	private Profession baseClass;
	private SkillProgression[] activeTrees;
	
	public PlayerClass(Profession base, SkillProgression[] skills)
	{
		baseClass = base;
		activeTrees = skills;
	}
	
	public int[] getLevelUpStats()
	{
		return null;
	}
	
	public Skill[] getAvailableSkills(int level)
	{
		return null;
	}
	
	public Profession getBaseClass(){
		return this.baseClass;
	}

}
