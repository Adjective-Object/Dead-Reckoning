package net.plaidypus.deadreckoning.state.menustates;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.config.OptionsHandler;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.menuItems.SkillTreeSelectionElement;
import net.plaidypus.deadreckoning.hudelements.simple.FillerBar;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.state.PrebakedHudLayersState;
import net.plaidypus.deadreckoning.statmaster.Profession;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ClassCreationState extends PrebakedHudLayersState{

	static final int outDistance = 150;
	int currentProfession = 0;
	
	StillImageElement bigImage;
	TextElement playerName, descriptionBox;
	Button leftButton, rightButton, resetButton, okayButton, cancelButton;
	
	ArrayList<FillerBar>statBars;
	
	ArrayList<SkillTreeSelectionElement> selectors;
	ArrayList<StillImageElement> treeImages;
	
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
		this.bigImage.xoff+=(outDistance-this.bigImage.xoff)*0.1;
		if(this.bigImage.xoff<this.outDistance){
			this.bigImage.xoff+=1;
		}
		
		for(int i=0; i<3; i++){
			if(this.selectors.get(i).isTreeChanged()){
				this.treeImages.get(i).makeFrom(selectors.get(i).getSelectedTree().getSkills()[0].getIcon());
			}
		}
		
		if(resetButton.isPressed()){
			this.reset();
		}
		
		if(cancelButton.isPressed()){
			this.reset();
			DeadReckoningGame.instance.enterState(DeadReckoningGame.NEWGAMESTATE);
		}
		
		if(okayButton.isPressed() &&
				selectors.get(0).getSelectedTree()!=null &&
				selectors.get(1).getSelectedTree()!=null &&
				selectors.get(2).getSelectedTree()!=null){
			Profession saver = new Profession(Profession.getProfession(currentProfession).getParentMod(),
					Profession.getProfession(currentProfession).getBaseClass(),
					selectors.get(0).getSelectedTree(),
					selectors.get(1).getSelectedTree(),
					selectors.get(2).getSelectedTree(),1);
			saver.name = "NONAMENEWCLASS";
			saver.description="FUUUCK";
			try {
				Profession.saveCustomProfession(saver);
			} catch (IOException e) {
				e.printStackTrace();
			}
			NewGameState ng = (NewGameState)DeadReckoningGame.instance.getState(DeadReckoningGame.NEWGAMESTATE);
			ng.buildCustomClasses();
			DeadReckoningGame.instance.enterState(DeadReckoningGame.NEWGAMESTATE);
			this.reset();
		}
		
	}
	
	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
	
		bigImage = new StillImageElement(0,25,HudElement.TOP_LEFT);
		returnElements.add(bigImage);
		
		//making the far left part - displays base class information
		playerName = new TextElement(50,20,HudElement.TOP_LEFT,"NO_NAME",DeadReckoningGame.menuTextColor, DeadReckoningGame.menuLargeFont);
		
		statBars = new ArrayList<FillerBar>(6);
		statBars.add(new FillerBar(65,330,new Color(200,70,200)));
		statBars.add(new FillerBar(95,330,new Color(70,200,200)));
		statBars.add(new FillerBar(125,330,new Color(200,70,70)));
		statBars.add(new FillerBar(155,330,new Color(70,70,200)));
		statBars.add(new FillerBar(185,330,new Color(70,200,70)));
		statBars.add(new FillerBar(215,330,new Color(200,200,70)));
		
		//making the tree indication elements and the selectors
		treeImages = new ArrayList<StillImageElement>(3);
		selectors = new ArrayList<SkillTreeSelectionElement>(0);
		for(int i=0; i<3; i++){
			treeImages.add(new StillImageElement(50,60+52*i,HudElement.TOP_LEFT,new Image("res/noSkill.png")));
			selectors.add(new SkillTreeSelectionElement(350+(OptionsHandler.getResolutionX()-350)/4*(i+1)-25, 20, HudElement.TOP_LEFT,(OptionsHandler.getResolutionY()-214)/40));
		}
		returnElements.addAll(treeImages);
		returnElements.addAll(selectors);
		
		leftButton = new TextButton(50,250,HudElement.TOP_LEFT,"<");
		rightButton = new TextButton(300,250,HudElement.TOP_LEFT,">");
		returnElements.add(leftButton);
		returnElements.add(rightButton);
		returnElements.add(playerName);
		returnElements.addAll(statBars);
		
		this.descriptionBox = new TextElement(30,-170,OptionsHandler.getResolutionX()-130-30*2,170-30,HudElement.BOTTOM_LEFT,"NO_DESC",DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont);
		ArrayList<HudElement> boxPanelCont = new ArrayList<HudElement>(1);
		boxPanelCont.add(this.descriptionBox);
		returnElements.add(new Panel(boxPanelCont));
		
		this.resetButton = new TextButton(-130,-170, HudElement.BOTTOM_RIGHT,"RESET",DeadReckoningGame.menuLargeFont);
		this.cancelButton = new TextButton(-130,-120, HudElement.BOTTOM_RIGHT,"CANCEL",DeadReckoningGame.menuLargeFont);
		this.okayButton = new TextButton(-130,-70, HudElement.BOTTOM_RIGHT,"OKAY",DeadReckoningGame.menuLargeFont);
		returnElements.add(resetButton);
		returnElements.add(okayButton);
		returnElements.add(cancelButton);
		
		return returnElements;
	}
	
	public void updateToProfession(Profession p){
		this.bigImage.makeFrom(p.getSelectImage());
		this.bigImage.xoff=-bigImage.getWidth(); 
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
			this.statBars.get(i).setDestination((float)fractions[i]);
		}
	}
	
	public void reset() throws SlickException{
		for(int i=0; i<3; i++){
			this.selectors.get(i).reset();
			this.treeImages.get(i).makeFrom(new Image("res/noSkill.png"));
		}
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		this.updateToProfession(Profession.getProfession(0));
	}
}
