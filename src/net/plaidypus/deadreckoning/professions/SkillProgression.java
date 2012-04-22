package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.SlickException;

public class SkillProgression {

	private Skill[] skillList;

	String name;

	private int[] levelReq;
	public int sourceClass, sourceTree;

	public SkillProgression(String name, Skill[] skills, int[] levels)
			throws SlickException {
		this.name = name;
		skillList = skills;
		levelReq = levels;
		if (skillList.length != levelReq.length)
			throw new SlickException("Error Loading Skills: Length Mismatch");
	}

	public static SkillProgression loadTree(int baseClassID, int treeNum)
			throws SlickException {
		SkillProgression p = SkillProgression.loadFromFile("res/professions/"
				+ Integer.toString(baseClassID) + "/tree" + treeNum + ".txt");
		p.sourceClass = baseClassID;
		p.sourceTree = treeNum;
		return p;
	}

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

	public Skill[] getSkills() {
		return skillList;
	}

	public int[] getLevelReqs() {
		return levelReq;
	}

}