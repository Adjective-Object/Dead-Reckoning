package net.plaidypus.deadreckoning.professions;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.items.Item;

public class Profession {
	
	private double[] statDist;
	
	private Item mainWeapon;
	
	private SkillProgression[] skillTrees;
	
	private Image portrait;
	private int baseClassID;
	
	public Profession(int baseClassID) throws SlickException{
		skillTrees = new SkillProgression[] {	
			SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree1.txt"),
			SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree2.txt"),
			SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree3.txt")
		};
		this.baseClassID=baseClassID;
		this.portrait=new Image("res/professions/"+baseClassID+"/Portrait.png");
	}
	
	public Profession(int baseClassID, SkillProgression treeA, SkillProgression treeB, SkillProgression treeC) throws SlickException{
		skillTrees = new SkillProgression[] {	
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree1.txt"),
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree2.txt"),
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree3.txt")
			};
		this.baseClassID=baseClassID;
		this.portrait=new Image("res/professions/"+baseClassID+"/Portrait.png");
	}
	
	Profession(int baseClassID, SkillProgression skillsA, SkillProgression skillsB, SkillProgression skillsC,
			double[] statDist, Item main) throws SlickException{
		skillTrees = new SkillProgression[] {	
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree1.txt"),
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree2.txt"),
				SkillProgression.loadFromFile("res/professions/"+Integer.toString(baseClassID)+"/tree3.txt")
			};
		this.mainWeapon=main;
		this.statDist=statDist;
		this.portrait=new Image("res/professions/"+baseClassID+"/Portrait.png");
	}
	
	public double[] getStatDist(){return statDist;}
	public Item getMainWeapon(){return mainWeapon;}
	
	public SkillProgression[] getTrees(){return skillTrees;}
	
	public Image getPortriat(){return portrait;}
	public String getEntityFile(){return "res/professions/"+baseClassID+"/Player.entity";}
	
}
	
