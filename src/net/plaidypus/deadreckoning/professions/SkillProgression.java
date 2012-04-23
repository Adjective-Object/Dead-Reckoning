package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	public int sourceClass, sourceTree;

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

	/**
	 * Load tree.
	 *
	 * @param baseClassID the base class id
	 * @param treeNum the tree num
	 * @return the skill progression
	 * @throws SlickException the slick exception
	 */
	public static SkillProgression loadTree(int baseClassID, int treeNum)
			throws SlickException {
		SkillProgression p = SkillProgression.loadFromFile("res/professions/"
				+ Integer.toString(baseClassID) + "/tree" + treeNum + ".txt");
		p.sourceClass = baseClassID;
		p.sourceTree = treeNum;
		return p;
	}

	/**
	 * Load from file.
	 *
	 * @param string the string
	 * @return the skill progression
	 * @throws SlickException the slick exception
	 */
	private static SkillProgression loadFromFile(String string)
			throws SlickException {
		SkillProgression s = null;
		try {
			BufferedReader r = new BufferedReader(new FileReader(string));
			ClassLoader c = ClassLoader.getSystemClassLoader();
			Skill[] array = new Skill[4];
			int[] levels = new int[] { 0, 1, 2, 3 };

			String name = r.readLine();

			for (int i = 0; i < 4; i++) {
				Class<? extends Skill> clas = c.loadClass(r.readLine())
						.asSubclass(Skill.class);
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

}