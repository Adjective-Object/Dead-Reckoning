package net.plaidypus.deadreckoning.professions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.skills.Skill;

public class SkillProgression {
	
	int professionAllignment;
	
	ArrayList<Skill> skills;
	
	float HPbuff, MPbuff, STRbuff, WISbuff, AGIbuff; // these are percentage based skill buffs
	
	public SkillProgression(ArrayList<Skill> skills,int[] skillBuffs, int profession){
		this.skills=skills;
		
		HPbuff=skillBuffs[0];
		MPbuff=skillBuffs[1];
		STRbuff=skillBuffs[2];
		WISbuff=skillBuffs[3];
		AGIbuff=skillBuffs[4];
		
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
