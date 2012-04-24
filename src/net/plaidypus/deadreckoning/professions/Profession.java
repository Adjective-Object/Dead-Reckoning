package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Profession (Playerclass).
 * 
 * The profession class is a custom kind of statmaster that also keeps the skills
 * that the player has access to in a series of skillprogressions
 * 
 * @see net.plaidypus.deadreckoning.professions.SkillProgression
 */
public class Profession extends StatMaster {

	/** The stat distribution variables (determines how stats are allocated to HP,MP,STR,DEX,INT,and LUK. From the baseclass). */
	private double[] statDist;// HP, MP, STR, DEX, INT, LUK

	/** The main weapon of the profession (the weapon they get bonus damage from using). */
	private Item mainWeapon;

	/** The skill "tree". (not really branching. instead, three seperate independent skill progressions) */
	private SkillProgression[] skillTrees;

	/** The portrait image of the profession/character. */
	private Image portrait;
	
	/** The base class id. Used to get stat distributions and the character portrait */
	private int baseClassID;

	/** The name of the playerclass. */
	public String name;
	
	/** The base stats and SP gained per level. */
	int baseHP = 50, baseMP = 20, baseStat = 4, spPerLevel = 5;

	/** The skill points earned, distributed with the ratios in statDist. */
	public int skillPoints;

	/**
	 * Loads a profession (one of the base classes)
	 *
	 * @param baseClassID the base class id
	 * @throws SlickException the slick exception
	 */
	public Profession(int baseClassID) throws SlickException {
		this(baseClassID, SkillProgression.loadTree(baseClassID, 1),
				SkillProgression.loadTree(baseClassID, 2), SkillProgression
						.loadTree(baseClassID, 3), 1);
	}

	/**
	 * Instantiates a new profession.
	 * 
	 * with this, you can mix and match skill trees from different classes.
	 *
	 * @param baseClassID the base class id
	 * @param treeA the tree a
	 * @param treeB the tree b
	 * @param treeC the tree c
	 * @param level the level
	 * @throws SlickException the slick exception
	 */
	public Profession(int baseClassID, SkillProgression treeA,
			SkillProgression treeB, SkillProgression treeC, int level)
			throws SlickException {
		super(0, 0, 0, 0, 0, 0, level);
		skillTrees = new SkillProgression[] { treeA, treeB, treeC };
		this.baseClassID = baseClassID;
		this.portrait = new Image("res/professions/" + baseClassID
				+ "/Portrait.png");
		parseClassTraits(baseClassID);
	}

	/**
	 * Parses the class traits, and dumps them into statDist
	 * 
	 *  used just for getting the stat ratio variables
	 *
	 * @param baseClassID the base class id
	 */
	private void parseClassTraits(int baseClassID) {
		double[] stats = new double[6];
		try {
			BufferedReader r = new BufferedReader(new FileReader(new File(
					"res/professions/" + baseClassID + "/ClassTraits.txt")));
			this.name = r.readLine();

			for (int i = 0; i < 6; i++) {
				stats[i] = Double.parseDouble(r.readLine());
			}
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
	 * Loads from file.
	 * 
	 * unimplemented
	 * 
	 * @param f the file
	 * @return the loaded profession
	 * @throws SlickException the slick exception
	 */
	public static Profession loadFromFile(File f) throws SlickException {
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(f));
			r.readLine();
			return new Profession(r.read(), SkillProgression.loadTree(r.read(),
					r.read()), SkillProgression.loadTree(r.read(), r.read()),
					SkillProgression.loadTree(r.read(), r.read()), 1// TODO
																	// reading
																	// and
																	// writing
																	// level of
																	// player to
																	// this
																	// shiznit
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Profession(0);
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

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getMaxHP()
	 */
	public int getMaxHP() {
		return (int) (baseHP + statDist[0] * level * spPerLevel);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getMaxMP()
	 */
	public int getMaxMP() {
		return (int) (baseMP + statDist[1] * level * spPerLevel);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getSTR()
	 */
	public int getSTR() {
		return (int) (baseStat + statDist[2] * level * spPerLevel);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getDEX()
	 */
	public int getDEX() {
		return (int) (baseStat + statDist[3] * level * spPerLevel);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getINT()
	 */
	public int getINT() {
		return (int) (baseStat + statDist[4] * level * spPerLevel);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.professions.StatMaster#getLUK()
	 */
	public int getLUK() {
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

	/**
	 * Gets the entity file.
	 *
	 * @return the entity file
	 */
	public String getEntityFile() {
		return "res/professions/" + baseClassID + "/Player.entity";
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
	 * Enumerate professions.
	 *
	 * @return the int
	 */
	public static int enumerateProfessions() {
		return new File("res/professions/").list().length;
	}

	/**
	 * Level up.
	 */
	public void levelUp() {
		this.level += 1;
		this.skillPoints += this.spPerLevel;
	}

	/* (non-Javadoc)
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
	 * @param e the e
	 */
	public void parentTo(LivingEntity e) {
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 4; x++) {
				this.getTrees()[i].getSkills()[x].bindTo(e);
			}
		}
	}

}
