package net.plaidypus.deadreckoning.professions;

import net.plaidypus.deadreckoning.skills.Skill;

public class SkillProgression {
	
	int professionAllignment;
	
	Skill[] skills;
	
	float HPbuff, MPbuff, STRbuff, WISbuff, AGIbuff; // these are percentage based skill buffs
	
	public SkillProgression(Skill[] skills, double[] skillBuffs, int profession){
		this.skills=skills;
		
		HPbuff=(float)skillBuffs[0];
		MPbuff=(float)skillBuffs[1];
		STRbuff=(float)skillBuffs[2];
		WISbuff=(float)skillBuffs[3];
		AGIbuff=(float)skillBuffs[4];
		
		this.professionAllignment=profession;
	}
	
	public float getHPBuff(){
		return HPbuff;
	}
	
	public float getMPBuff(){
		return MPbuff;
	}
	
	public float getSTRBuff(){
		return STRbuff;
	}
	
	public float getWISBuff(){
		return WISbuff;
	}
	
	public float getAGIBuff(){
		return AGIbuff;
	}
}
