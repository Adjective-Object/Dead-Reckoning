package net.plaidypus.deadreckoning.professions;

import net.plaidypus.deadreckoning.skills.Skill;

public class PlayerClass {
	
	private Profession baseClass;
	private SkillProgression[] activeTrees;
	
	public PlayerClass(Profession base, SkillProgression ... tree)
	{
		baseClass = base;
		activeTrees = tree;
	}
	
	public int[] getLevelUpStats()
	{
		return null;
	}
	
	public Skill[] getAvailableSkills(int level)
	{
		return null;
	}

}
