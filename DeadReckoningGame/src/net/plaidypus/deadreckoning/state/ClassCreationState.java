package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
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

	StillImageElement bigImage, iconImage;
	TextElement playerName;
	Button leftButton, rightButton;
	
	ArrayList<FillerBar>statBars;
	
	public ClassCreationState(int stateID, ArrayList<HudElement> elements)
			throws SlickException {
		super(stateID, elements);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElements = new ArrayList<HudElement>(0);
	
		bigImage = new StillImageElement(0,0,HudElement.TOP_LEFT);
		
		//making the far left part - displays base class information
		ArrayList<HudElement> leftPanelContents = new ArrayList<HudElement>(0);
		iconImage = new StillImageElement(50,0,HudElement.TOP_LEFT);
		playerName = new TextElement(0,0,HudElement.TOP_LEFT,"NO_NAME",DeadReckoningGame.menuTextColor, DeadReckoningGame.menuFont);
		
		statBars = new ArrayList<FillerBar>(6);
		statBars.add(new FillerBar(30,50,new Color(200,70,200)));
		statBars.add(new FillerBar(30,60,new Color(70,200,200)));
		statBars.add(new FillerBar(30,70,new Color(200,70,70)));
		statBars.add(new FillerBar(30,80,new Color(70,70,200)));
		statBars.add(new FillerBar(30,90,new Color(70,200,70)));
		statBars.add(new FillerBar(30,100,new Color(200,200,70)));
		
		leftButton = new TextButton(20,20,HudElement.TOP_LEFT,"<");
		rightButton = new TextButton(20,20,HudElement.TOP_LEFT,">");
		
		leftPanelContents.add(iconImage);
		leftPanelContents.add(leftButton);
		leftPanelContents.add(rightButton);
		leftPanelContents.addAll(statBars);
		
		Panel leftPanel = new Panel(10,100,HudElement.TOP_LEFT,leftPanelContents,15,15);
		
		returnElements.add(leftPanel);
		
		return returnElements;
	}
	
	public void updateToProfession(Profession p){
		
	}
	
	@Override
	public void makeFrom(Object O) {}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		System.out.println("CLASSCREATION");
	}
}
