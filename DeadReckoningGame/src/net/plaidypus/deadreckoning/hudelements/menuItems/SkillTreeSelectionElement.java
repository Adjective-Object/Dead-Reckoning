package net.plaidypus.deadreckoning.hudelements.menuItems;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.statmaster.Profession;
import net.plaidypus.deadreckoning.statmaster.SkillProgression;

public class SkillTreeSelectionElement extends Panel{

	ArrayList<Button> classSelectButtons;
	ArrayList<Button> skillSelectButtons;
	
	Button upButton, downButton;
	
	int scrollValue = 0;
	
	int numClassButtons = 9;
	
	Profession selectedProfession = null;
	SkillProgression selectedTree = null;
	
	static Image upArrow, downArrow, backButton;
	boolean treeChanged;
	
	public SkillTreeSelectionElement(int x, int y, int bindMethod, int numbers) throws SlickException {
		super(x, y, bindMethod, new ArrayList<HudElement>(0),0,0);
		
		this.numClassButtons = numbers;
		
		downArrow = new Image("res/CharacterSelect/downArrow.png");
		upArrow = new Image("res/CharacterSelect/upArrow.png");
		backButton = new Image("res/CharacterSelect/backButton.png");
		
		classSelectButtons = makeClassSelectContents();
		skillSelectButtons = makeSkillSelectContents();
		upButton = new ImageButton(0,0,HudElement.TOP_LEFT,upArrow);
		downButton = new ImageButton(0,40*numClassButtons+12,HudElement.TOP_LEFT, downArrow);
		this.addElement(upButton);
		this.addElement(downButton);
		classSelectButtons = makeClassSelectContents();
		this.addAllElements(classSelectButtons);
		updateToScroll(0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(gc, sbg, delta);
		treeChanged=false;
				
		if(selectedProfession==null){
			if(upButton.isPressed()){ scrollValue=(scrollValue+Profession.getNumProfessions()-1)%Profession.getNumProfessions();}
			if(downButton.isPressed()){ scrollValue=(scrollValue+1)%Profession.getNumProfessions();}
			
			if(upButton.isPressed()||downButton.isPressed()){
				updateToScroll(scrollValue);
			}
			
			for(int i=0; i<numClassButtons; i++){
				if(classSelectButtons.get(i).isPressed()){
					this.selectedProfession=Profession.getProfession((i+scrollValue)%Profession.getNumProfessions());
					this.enterSkillSelect();
					break;
				}
			}
		}
		
		else if(selectedProfession!=null){
			for(int i=1; i<4; i++){
				if(skillSelectButtons.get(i).isPressed()){
					this.selectedTree=this.selectedProfession.getTrees()[i-1];
					treeChanged=true;
				}
			}
			
			if(skillSelectButtons.get(0).isPressed()){
				this.selectedProfession=null;
				this.enterClassSelect();
			}
		}
	}
	
	public void enterSkillSelect(){
		this.clearElements();
		this.addAllElements(skillSelectButtons);
		this.updateToProfession(selectedProfession);
	}
	
	public void enterClassSelect() {
		this.clearElements();
		this.addElement(upButton);
		this.addElement(downButton);
		this.addAllElements(classSelectButtons);
	}
	
	public void reset(){
		this.selectedTree=null;
		if(this.selectedProfession!=null){
			this.selectedProfession=null;
			this.enterClassSelect();
		}
	}
	
	private void updateToProfession(Profession p) {
		this.skillSelectButtons.get(0).makeFrom(backButton);
		for(int i=1; i<this.skillSelectButtons.size(); i++){
			this.skillSelectButtons.get(i).makeFrom(p.getTrees()[i-1].getSkills()[0].getIcon());
		}
	}

	public void updateToScroll(int scrollValue){
		for(int i=0; i<numClassButtons; i++){
			classSelectButtons.get(i).makeFrom(Profession.getProfession((i+scrollValue)%Profession.getNumProfessions()).getIcon());
		}
	}
	
	public ArrayList<Button> makeClassSelectContents(){
		ArrayList<Button> classButtons = new ArrayList<Button>(numClassButtons);
		for(int i=0; i<numClassButtons; i++){
			classButtons.add(new ImageButton(9,12+4+40*i,HudElement.TOP_LEFT, null));
		}
		return classButtons;
	}
	
	public ArrayList<Button> makeSkillSelectContents(){
		ArrayList<Button> skillButtons = new ArrayList<Button>(4);
		int height = 12+4+40*(numClassButtons+1);
		for(int i=0; i<4; i++){
			skillButtons.add(new ImageButton(9,(height-32)/5*(i+1)-12,HudElement.TOP_LEFT, null));
		}
		return skillButtons;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}
	
	public SkillProgression getSelectedTree(){
		return this.selectedTree;
	}

	public boolean isTreeChanged(){
		return treeChanged;
	}
	
	@Override
	public void makeFrom(Object o) {}

}
