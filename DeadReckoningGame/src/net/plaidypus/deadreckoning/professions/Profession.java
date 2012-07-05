package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.FileNameExtentionFilter;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Profession (Playerclass).
 * 
 * The profession class is a custom kind of statmaster that also keeps the
 * skills that the player has access to in a series of skillprogressions
 * 
 * @see net.plaidypus.deadreckoning.professions.SkillProgression
 */
public class Profession extends StatMaster {

	/** tracks all the base professions loaded from modLoader */
	private static ArrayList<Profession> professions = new ArrayList<Profession>(
			0);
	private static HashMap<String, Profession> profMap = new HashMap<String, Profession>(
			0);

	/**
	 * The stat distribution variables (determines how stats are allocated to
	 * HP,MP,STR,DEX,INT,and LUK. From the baseclass).
	 */
	private double[] statDist;// HP, MP, STR, DEX, INT, LUK

	/**
	 * The main weapon of the profession (the weapon they get bonus damage from
	 * using).
	 */
	private Item mainWeapon;

	/**
	 * The skill "tree". (not really branching. instead, three seperate
	 * independent skill progressions)
	 */
	private SkillProgression[] skillTrees;

	/** The portrait image of the profession/character. */
	private Image portrait, selectImage, icon;
	
	public Color color = new Color(255,255,255);

	/**
	 * The base class id. Used to get stat distributions and the character
	 * portrait
	 */
	private int baseClassID;

	/** The name of the playerclass. */
	public String name, parentMod, description;

	/** The base stats and SP gained per level. */
	int baseHP = 50, baseMP = 20, baseStat = 4, spPerLevel = 1;

	/** The skill points earned, distributed with the ratios in statDist. */
	public int skillPoints;

	public Profession(String parentMod, int baseClassID, InputStream tree1,
			InputStream tree2, InputStream tree3) throws SlickException {
		this(parentMod, baseClassID, SkillProgression.loadFromStream(tree1),
				SkillProgression.loadFromStream(tree2), SkillProgression
						.loadFromStream(tree3), 1);
		this.skillTrees[0].setSource(parentMod, baseClassID, 0);
		this.skillTrees[1].setSource(parentMod, baseClassID, 1);
		this.skillTrees[2].setSource(parentMod, baseClassID, 2);
	}

	/**
	 * Instantiates a new profession.
	 * 
	 * with this, you can mix and match skill trees from different classes.
	 * 
	 * @param baseClassID
	 *            the base class id
	 * @param treeA
	 *            the tree a
	 * @param treeB
	 *            the tree b
	 * @param treeC
	 *            the tree c
	 * @param level
	 *            the level
	 * @throws SlickException
	 *             the slick exception
	 */
	public Profession(String parentMod, int baseClassID,
			SkillProgression treeA, SkillProgression treeB,
			SkillProgression treeC, int level) throws SlickException {
		super(0, 0, 0, 0, 0, 0, level);
		skillTrees = new SkillProgression[] { treeA, treeB, treeC };
		System.out.println("New Profession: " + treeA + " " + treeB + " "
				+ treeC);
		this.baseClassID = baseClassID;
		this.parentMod = parentMod;
		this.portrait = ModLoader.loadImage(this.parentMod+"/professions/" + baseClassID + "/Portrait.png");
		this.selectImage = ModLoader.loadImage(this.parentMod+"/professions/" + baseClassID + "/selectscreen.png");
		this.icon = ModLoader.loadImage(this.parentMod+"/professions/" + baseClassID + "/icon.png");
		parseClassTraits(parentMod, baseClassID);
	}

	/**
	 * Parses the class traits, and dumps them into statDist
	 * 
	 * used just for getting the stat ratio variables
	 * 
	 * @param baseClassID
	 *            the base class id
	 */
	private void parseClassTraits(String parentMod, int baseClassID) {
		double[] stats = new double[6];
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(ModLoader.getLoaderFor(parentMod).getResourceAsStream(parentMod+"/professions/" + baseClassID + "/ClassTraits.txt")));
			this.name = r.readLine();
			this.description=r.readLine();

			for (int i = 0; i < 6; i++) {
				stats[i] = Double.parseDouble(r.readLine());
			}
			this.color = new Color(
					Integer.parseInt(r.readLine()),
					Integer.parseInt(r.readLine()),
					Integer.parseInt(r.readLine()));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.statDist = stats;
	}

	/**
	 * Gets the hP frac. (from statDist)
	 * 
	 * @return the hP frac
	 */
	public double getHPFrac() {
		return statDist[0];
	}

	/**
	 * Gets the mP frac. (from statDist)
	 * 
	 * @return the mP frac
	 */
	public double getMPFrac() {
		return statDist[1];
	}

	/**
	 * Gets the sTR frac. (from statDist)
	 * 
	 * @return the sTR frac
	 */
	public double getSTRFrac() {
		return statDist[2];
	}

	/**
	 * Gets the dEX frac. (from statDist)
	 * 
	 * @return the dEX frac
	 */
	public double getDEXFrac() {
		return statDist[3];
	}

	/**
	 * Gets the iNT frac. (from statDist)
	 * 
	 * @return the iNT frac
	 */
	public double getINTFrac() {
		return statDist[4];
	}

	/**
	 * Gets the lUK frac. (from statDist)
	 * 
	 * @return the lUK frac
	 */
	public double getLUKFrac() {
		return statDist[5];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getMaxHP()
	 */
	public int getRawMaxHP() {
		return (int) (baseHP + statDist[0] * level * spPerLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getMaxMP()
	 */
	public int getRawMaxMP() {
		return (int) (baseMP + statDist[1] * level * spPerLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getSTR()
	 */
	public int getRawSTR() {
		return (int) (baseStat + statDist[2] * level * spPerLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getDEX()
	 */
	public int getRawDEX() {
		return (int) (baseStat + statDist[3] * level * spPerLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getINT()
	 */
	public int getRawINT() {
		return (int) (baseStat + statDist[4] * level * spPerLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getLUK()
	 */
	public int getRawLUK() {
		return (int) (baseStat + statDist[5] * level * spPerLevel);
	}

	/**
	 * Gets the main weapon.
	 * 
	 * @return the main weapon
	 */
	public Item getMainWeapon() {
		return mainWeapon;
	}

	/**
	 * Gets the trees.
	 * 
	 * @return the trees
	 */
	public SkillProgression[] getTrees() {
		return skillTrees;
	}

	/**
	 * Gets the portriat.
	 * 
	 * @return the portriat
	 */
	public Image getPortriat() {
		return portrait;
	}
	
	public Image getSelectImage() {
		return this.selectImage;
	}
	

	public Image getIcon() {
		return this.icon;
	}

	/**
	 * Gets the entity file.
	 * 
	 * @return the entity file
	 */
	public String getEntityFile() {
		return "professions/" + baseClassID + "/player.entity";
	}

	/**
	 * Gets the base class.
	 * 
	 * @return the base class
	 */
	public int getBaseClass() {
		return this.baseClassID;
	}

	/**
	 * Level up.
	 */
	public void levelUp() {
		this.level += 1;
		this.skillPoints += this.spPerLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getLevel()
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * Gets the skill list.
	 * 
	 * @return the skill list
	 */
	public ArrayList<Skill> getSkillList() {
		ArrayList<Skill> toRet = new ArrayList<Skill>(0);
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 4; x++) {
				toRet.add(this.getTrees()[i].getSkills()[x]);
			}
		}
		return toRet;
	}

	/**
	 * Parent to.
	 * 
	 * @param e
	 *            the e
	 */
	public void parentTo(LivingEntity e) {
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 4; x++) {
				this.getTrees()[i].getSkills()[x].bindTo(e);
			}
		}
	}

	public String getParentMod() {
		return this.parentMod;
	}

	public static void addProfession(Profession profession) {
		Profession.professions.add(profession);
		profMap.put(profession.parentMod + profession.baseClassID, profession);
	}

	public static ArrayList<Profession> getProfessions() {
		return professions;
	}
	
	public static int getNumProfessions(){
		return professions.size();
	}

	public static Profession getProfession(int prof) {
		return professions.get(prof);
	}

	public static Profession loadProfession(String modname, int profNumber) {
		return Profession.profMap.get(modname + profNumber);
	}
	
	
	public static Profession loadCustomProfession(int profileID) throws SlickException{
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("classes/"+profileID+".prof"));
			String name = reader.readLine(),
					description = reader.readLine(),
					baseclassMod = reader.readLine(),
					tree1Mod = reader.readLine(),
					tree2Mod = reader.readLine(),
					tree3Mod = reader.readLine();
			int baseclass = Integer.parseInt(reader.readLine()),
					tree1class = Integer.parseInt(reader.readLine()),
					tree1tree = Integer.parseInt(reader.readLine()),
					tree2class = Integer.parseInt(reader.readLine()),
					tree2tree = Integer.parseInt(reader.readLine()),
					tree3class = Integer.parseInt(reader.readLine()),
					tree3tree = Integer.parseInt(reader.readLine());
			
			SkillProgression tree1 = SkillProgression.loadTree(tree1Mod, tree1class, tree1tree),
					tree2 = SkillProgression.loadTree(tree2Mod, tree2class, tree2tree),
					tree3 = SkillProgression.loadTree(tree3Mod, tree3class, tree3tree);
			
			Profession p = new Profession(baseclassMod, baseclass, tree1, tree2, tree3, 1);
			p.name=name;
			p.description=description;
			reader.close();
			
			return p;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveCustomProfession(Profession p) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(new File("classes/"+Profession.enumerateCustomProfessions()+".prof")));
		String[] toWrite = {
				p.name,
				p.description,
				p.parentMod,
				p.getTrees()[0].sourceMod,
				p.getTrees()[1].sourceMod,
				p.getTrees()[2].sourceMod,
				Integer.toString(p.getBaseClass()),
				Integer.toString(p.getTrees()[0].sourceClass),
				Integer.toString(p.getTrees()[0].sourceTree),
				Integer.toString(p.getTrees()[1].sourceClass),
				Integer.toString(p.getTrees()[1].sourceTree),
				Integer.toString(p.getTrees()[2].sourceClass),
				Integer.toString(p.getTrees()[2].sourceTree)
		};
		
		for(int i=0; i<toWrite.length; i++){
			w.write(toWrite[i]);
			w.newLine();
		}
		w.close();
	}
	
	public static int enumerateCustomProfessions(){
		File f = new File("classes/");
		File[] p = f.listFiles(new FileNameExtentionFilter(".prof"));
		return p.length;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String toString(){
		return "<Profession "+this.name+">";
	}

}
