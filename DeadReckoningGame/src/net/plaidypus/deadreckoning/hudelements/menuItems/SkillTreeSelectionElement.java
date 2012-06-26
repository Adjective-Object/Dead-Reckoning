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
import net.plaidypus.deadreckoning.professions.Profession;

public class SkillTreeSelectionElement extends Panel{

	ArrayList<HudElement> classSelectButtons;
	ArrayList<Button> skillSelectButtons;
	
	Button upButton, downButton;
	
	int scrollValue = 0;
	
	static final int numClassButtons = 7;
	
	public SkillTreeSelectionElement(int x, int y, int bindMethod) throws SlickException {
		super(x, y, bindMethod, new ArrayList<HudElement>(0),0,0);
		
		classSelectButtons = makeClassSelectContents();
		upButton = new ImageButton(0,0,HudElement.TOP_LEFT,new Image("res/CharacterSelect/upArrow.png"));
		downButton = new ImageButton(0,40*numClassButtons+12,HudElement.TOP_LEFT, new Image("res/CharacterSelect/downArrow.png"));
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
		if(upButton.isPressed()){ scrollValue=(scrollValue+Profession.getNumProfessions()-1)%Profession.getNumProfessions();}
		if(downButton.isPressed()){ scrollValue=(scrollValue+1)%Profession.getNumProfessions();}
		
		if(upButton.isPressed()||downButton.isPressed()){
			System.out.println(scrollValue);
			updateToScroll(scrollValue);
		}
	}
	
	public void updateToScroll(int scrollValue){
		for(int i=0; i<numClassButtons; i++){
			classSelectButtons.get(i).makeFrom(Profession.getProfession((i+scrollValue)%Profession.getNumProfessions()).getIcon());
		}
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}
	
	public ArrayList<HudElement> makeClassSelectContents(){
		ArrayList<HudElement> classButtons = new ArrayList<HudElement>(0);
		for(int i=0; i<numClassButtons; i++){
			classButtons.add(new ImageButton(9,12+4+40*i,HudElement.TOP_LEFT, null));
		}
		return classButtons;
	}
	
	public ArrayList<HudElement> makeTreeSelectContents(Profession p){
		return null;
	}

	@Override
	public void makeFrom(Object o) {}

}
