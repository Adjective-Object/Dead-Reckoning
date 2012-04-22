package net.plaidypus.deadreckoning.professions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.skills.Skill;

public class Profession extends StatMaster{
	
	private double[] statDist;//HP, MP, STR, DEX, INT, LUK
	
	private Item mainWeapon;
	
	private SkillProgression[] skillTrees;
	
	private Image portrait;
	private int baseClassID;

	public String name;
	int baseHP = 50, baseMP = 20, baseStat= 4, spPerLevel = 5;

	public int skillPoints;
	
	public Profession(int baseClassID) throws SlickException{
		this(baseClassID,
			SkillProgression.loadTree(baseClassID, 1),
			SkillProgression.loadTree(baseClassID, 2),
			SkillProgression.loadTree(baseClassID, 3),
			1
		);
	}
	
	public Profession(int baseClassID, SkillProgression treeA, SkillProgression treeB, SkillProgression treeC, int level) throws SlickException{
		super(0,0,0,0,0,0, level);
		skillTrees = new SkillProgression[] {
				treeA, treeB, treeC
			};
		this.baseClassID=baseClassID;
		this.portrait=new Image("res/professions/"+baseClassID+"/Portrait.png");
		parseClassTraits(baseClassID);
	}
	
	private void parseClassTraits(int baseClassID) {
		double[] stats = new double[6];
		try{
			BufferedReader r = new BufferedReader(new FileReader(new File("res/professions/"+baseClassID+"/ClassTraits.txt")));
			this.name=r.readLine();
			
			for(int i=0; i<6 ; i++){
				stats[i]=Double.parseDouble(r.readLine());
			}
		}
		catch(FileNotFoundException e){ e.printStackTrace();}
		catch (NumberFormatException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		this.statDist=stats;
	}
	
	public static Profession loadFromFile(File f) throws SlickException {
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(f));
			r.readLine();
			return new Profession(r.read(),SkillProgression.loadTree(r.read(), r.read()),
					SkillProgression.loadTree(r.read(), r.read()),
					SkillProgression.loadTree(r.read(), r.read()),
					1//TODO reading and writing level of player to this shiznit
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Profession(0);
	}
		

	public double getHPFrac(){return statDist[0];}
	public double getMPFrac(){return statDist[1];}
	public double getSTRFrac(){return statDist[2];}
	public double getDEXFrac(){return statDist[3];}
	public double getINTFrac(){return statDist[4];}
	public double getLUKFrac(){return statDist[5];}
	
	public int getMaxHP(){return (int) (baseHP+statDist[0]*level*spPerLevel);}
	public int getMaxMP(){return (int) (baseMP+statDist[1]*level*spPerLevel);}
	public int getSTR(){return (int) (baseStat+statDist[2]*level*spPerLevel);}
	public int getDEX(){return (int) (baseStat+statDist[3]*level*spPerLevel);}
	public int getINT(){return (int) (baseStat+statDist[4]*level*spPerLevel);}
	public int getLUK(){return (int) (baseStat+statDist[5]*level*spPerLevel);}
	
	public Item getMainWeapon(){return mainWeapon;}
	
	public SkillProgression[] getTrees(){return skillTrees;}
	
	public Image getPortriat(){return portrait;}
	public String getEntityFile(){return "res/professions/"+baseClassID+"/Player.entity";}

	public int getBaseClass() {
		return this.baseClassID;
	}
	
	public static int enumerateProfessions(){
		return new File("res/professions/").list().length;
	}
	
	public void levelUp(){
		this.level+=1;
		this.skillPoints+=this.spPerLevel;
	}

	public int getLevel() {
		return this.level;
	}

	public ArrayList<Skill> getSkillList() {
		ArrayList<Skill> toRet = new ArrayList<Skill>(0);
		for(int i=0; i<3;i++){
			for(int x=0; x<4;x++){
				toRet.add(this.getTrees()[i].getSkills()[x]);
			}
		}
		return toRet;
	}
	
	public void parentTo(LivingEntity e){
		for(int i=0; i<3;i++){
			for(int x=0; x<4;x++){
				this.getTrees()[i].getSkills()[x].bindTo(e);
			}
		}
	}
	
}
	
