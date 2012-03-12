package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
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

	public static SkillProgression loadFromFile(String string) throws SlickException {
		SkillProgression s = null;
		try {
			BufferedReader r = new BufferedReader(new FileReader(string));
			ClassLoader c = ClassLoader.getSystemClassLoader();
			Skill[] array = new Skill[4];
			int[] levels = new int[] {0,1,2,3};
			
			for(int i=0; i<4; i++){
				Class<? extends Skill> clas = c.loadClass(r.readLine()).asSubclass(Skill.class);
				Skill k = clas.newInstance();
				array[i] = k;
			}
			
			return new SkillProgression(array,levels);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		return s;
	}

	public Skill[] getSkills() {
		return skillList;
	}

	public int[] getLevelReqs() {
		return levelReq;
	}
	
}