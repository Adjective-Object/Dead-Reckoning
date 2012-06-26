package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.menuItems.SkillTreeSelectionElement;
import net.plaidypus.deadreckoning.hudelements.simple.FillerBar;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.professions.Profession;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ClassCreationState extends PrebakedHudLayersState{

	static final int outDistance = 150;
	int currentProfession = 0;
	
	StillImageElement bigImage, iconImage;
	TextElement playerName, descriptionBox;
	Button leftButton, rightButton;
	
	ArrayList<FillerBar>statBars;
	
	SkillTreeSelectionElement selector1, selector2, selector3;
	
	public ClassCreationState(int stateID, ArrayList<HudElement> elements)
			throws SlickException {
		super(stateID, elements);
	}


	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if(leftButton.isPressed()){
			this.currentProfession= (this.currentProfession-1+Profession.getNumProfessions())%Profession.getNumProfessions();
		}
		if(rightButton.isPressed()){
			this.currentProfession= (this.currentProfession+1)%Profession.getNumProfessions();
		}
		if(rightButton.isPressed() || leftButton.isPressed()){
			this.updateToProfession(Profession.getProfession(this.currentProfession));
		}
		this.bigImage.xoff+=(outDistance-this.bigImage.xoff)*delta/100;
		if(this.bigImage.xoff<this.outDistance){
			this.bigImage.xoff+=1;
		}
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
	
		bigImage = new StillImageElement(0,25,HudElement.TOP_LEFT);
		returnElements.add(bigImage);
		
		//making the far left part - displays base class information
		ArrayList<HudElement> leftPanelContents = new ArrayList<HudElement>(0);
		iconImage = new StillImageElement(25,18,HudElement.TOP_LEFT);
		playerName = new TextElement(5,0,HudElement.TOP_LEFT,"NO_NAME",DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont);
		
		statBars = new ArrayList<FillerBar>(6);
		statBars.add(new FillerBar(5,70,new Color(200,70,200)));
		statBars.add(new FillerBar(5,80,new Color(70,200,200)));
		statBars.add(new FillerBar(5,90,new Color(200,70,70)));
		statBars.add(new FillerBar(5,100,new Color(70,70,200)));
		statBars.add(new FillerBar(5,110,new Color(70,200,70)));
		statBars.add(new FillerBar(5,120,new Color(200,200,70)));
		
		leftButton = new TextButton(10,51,HudElement.TOP_LEFT,"<");
		rightButton = new TextButton(55,51,HudElement.TOP_LEFT,">");
		
		leftPanelContents.add(iconImage);
		leftPanelContents.add(leftButton);
		leftPanelContents.add(rightButton);
		leftPanelContents.add(playerName);
		leftPanelContents.addAll(statBars);
		
		Panel leftPanel = new Panel(40,40,HudElement.TOP_LEFT,leftPanelContents,15,15);
		returnElements.add(leftPanel);
		
		//making the skill tree selectors
		selector1= new SkillTreeSelectionElement(400, 100, HudElement.TOP_LEFT);
		selector2= new SkillTreeSelectionElement(525, 100, HudElement.TOP_LEFT);
		selector3= new SkillTreeSelectionElement(650, 100, HudElement.TOP_LEFT);
		returnElements.add(selector1);
		returnElements.add(selector2);
		returnElements.add(selector3);
		
		this.descriptionBox = new TextElement(50,-170,700,HudElement.BOTTOM_LEFT,"NO_DESC",DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont);
		returnElements.add(descriptionBox);
		
		return returnElements;
	}
	
	public void updateToProfession(Profession p){
		this.bigImage.makeFrom(p.getSelectImage());
		this.bigImage.xoff=-bigImage.getWidth();
		this.iconImage.makeFrom(p.getIcon());
		this.playerName.makeFrom(p.name);
		this.descriptionBox.makeFrom(p.description);
		
		double[] fractions = new double[] {
				p.getHPFrac()/5,
				p.getMPFrac()/5,
				p.getSTRFrac(),
				p.getINTFrac(),
				p.getDEXFrac(),
				p.getLUKFrac()};
		
		for (int i=0; i<this.statBars.size(); i++){
			this.statBars.get(i).setPosition(0F);
			this.statBars.get(i).setDestination((float)fractions[i]);
		}
	}
	
	@Override
	public void makeFrom(Object O) {}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		this.updateToProfession(Profession.getProfession(0));
	}
}
