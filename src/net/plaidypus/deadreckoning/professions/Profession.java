package net.plaidypus.deadreckoning.professions;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.items.Item;

public class Profession {
	
	private int[] statDist;
	
	private Item mainWeapon;
	
	private SkillProgression skillTreeA;
	private SkillProgression skillTreeB;
	private SkillProgression skillTreeC;
	
	private SkillProgression passiveTree;
	
	private Image portrait;
	private int baseClassID;
	
	Profession(int baseClassID, SkillProgression skillsA, SkillProgression skillsB, SkillProgression skillsC, SkillProgression passives, int[] statDist, Item main) throws SlickException{
		this.skillTreeA=skillsA;
		this.skillTreeB=skillsB;
		this.skillTreeC=skillsC;
		this.passiveTree=passives;
		this.mainWeapon=main;
		this.statDist=statDist;
		this.portrait=new Image("res/professions/"+baseClassID+"/Portrait.png");
	}
	
	public int[] getStatDist(){return statDist;}
	public Item getMainWeapon(){return mainWeapon;}
	public SkillProgression getActiveTreeA(){return skillTreeA;}
	public SkillProgression getActiveTreeB(){return skillTreeB;}
	public SkillProgression getActiveTreeC(){return skillTreeC;}
	public SkillProgression getPassiveTree(){return passiveTree;}
	
	public Image getPortriat(){return portrait;}
	public String getEntityFile(){return "res/professions/"+baseClassID+"/Player.entity";}
	
		//this.spriteSheet=new SpriteSheet("res/professions/"+baseClass+"/spriteSheet.png",32,32);
		//this.entityFile = "res/professions/"+baseClass+"/Player.entity";
		//this.portrait=new Image("res/professions/"+baseClass+"/Portrait.png");
		//this.icon=new Image("res/professions/"+baseClass+"/icon.png");
	
//	public static void init() throws SlickException{
//		professions = new Profession[5];
//		
//		SkillProgression testMage =new SkillProgression( new Skill[] {}, new double[] {1.1,1.1,1.1,1.1,1.1}, PROFESSION_MAGICIAN);
//		SkillProgression[] mageSkills = {testMage, testMage, testMage, testMage};
//		int[][] statProgress = {
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
//				{4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
//				} ;
//		
//		professions [0] = new Profession ( Profession.PROFESSION_MAGICIAN ,mageSkills, statProgress);
//	}
//	
//	//public Image getPortriat(){return portrait;}
//	
//	//public Image getIcon(){return icon;}
//	
//	//public SpriteSheet getSpriteSheet(){return spriteSheet;}
//	
//	public int getHP(int level){
//		return getStatProgression()[0][level];
//	}
//	public int getMP(int level){
//		return getStatProgression()[1][level];
//	}	
//	public int getSTR(int level){
//		return getStatProgression()[2][level];
//	}
//	public int getWIS(int level){
//		return getStatProgression()[3][level];
//	}
//	public int getLUK(int level){
//		return getStatProgression()[4][level];
//	}
//	public int getAGI(int level){
//		return getStatProgression()[5][level];
//	}
//	
//	protected int[][] getStatProgression(){
//		return statProgression;
//	}
//	
//	public SkillProgression[] getSkillProgression(){
//		return skills;
//	}
//
//	public String getEntityFile() {
//		return entityFile;
//	}
//}

}
	
