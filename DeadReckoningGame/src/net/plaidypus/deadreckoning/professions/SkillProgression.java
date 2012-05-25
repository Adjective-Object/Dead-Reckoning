package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class SkillProgression.
 */
public class SkillProgression {

	/** The skill list. */
	private Skill[] skillList;

	/** The name. */
	String name;

	/** The level req. */
	private int[] levelReq;
	
	/** The source tree. */
	public int sourceTree, sourceClass;
	
	/** The path to the source class */
	public String sourceMod;

	/**
	 * Instantiates a new skill progression.
	 *
	 * @param name the name
	 * @param skills the skills
	 * @param levels the levels
	 * @throws SlickException the slick exception
	 */
	public SkillProgression(String name, Skill[] skills, int[] levels)
			throws SlickException {
		this.name = name;
		skillList = skills;
		levelReq = levels;
		if (skillList.length != levelReq.length)
			throw new SlickException("Error Loading Skills: Length Mismatch");
	}
	
	public void setSource(String sourceMod, int sourceClass, int sourceTree){
		this.sourceMod = sourceMod;
		this.sourceClass=sourceClass;
		this.sourceTree=sourceTree;
	}
	
	/**
	 * Load from inputStream.
	 *
	 * @param stream the inputstream to read from
	 * @return the skill progression
	 * @throws SlickException the slick exception
	 */
	public static SkillProgression loadFromStream(InputStream stream)
			throws SlickException {
		SkillProgression s = null;
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(stream));
			Skill[] array = new Skill[4];
			int[] levels = new int[] { 0, 1, 2, 3 };

			String name = r.readLine();

			for (int i = 0; i < 4; i++) {
				@SuppressWarnings("unchecked")
				Class<? extends Skill> clas = ModLoader.loadClass(r.readLine()).asSubclass(Skill.class);
				Skill k = clas.newInstance();
				array[i] = k;
			}

			return new SkillProgression(name, array, levels);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public Skill[] getSkills() {
		return skillList;
	}

	/**
	 * Gets the level reqs.
	 *
	 * @return the level reqs
	 */
	public int[] getLevelReqs() {
		return levelReq;
	}

	public static SkillProgression loadTree(String modName,int profNum, int treeNum) {
		System.out.println("Loading Tree: "+modName+" "+profNum+" "+treeNum);
		return Profession.loadProfession(modName,profNum).getTrees()[treeNum];
	}
	
	public String toString(){
		return "["+skillList[0]+","+skillList[1]+","+skillList[2]+","+skillList[3]+"]";
	}

}