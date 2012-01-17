package net.plaidypus.deadreckoning.professions;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.skills.Skill;

public class SkillProgression {
	
	private Skill[] skillList;
	
	private int[] levelReq;

	public SkillProgression(Skill[] skills, int[] levels) throws SlickException
	{
		skillList = skills;
		levelReq = levels;
		if(skillList.length != levelReq.length)
			throw new SlickException("Error Loading Skills: Length Mismatch");
	}
	
}