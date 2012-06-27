package net.plaidypus.deadreckoning.state;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.OptionsHandler;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.TextButton;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.hudelements.simple.TickerBox;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsState extends PrebakedHudLayersState{

	TickerBox vsynch, stretch;
	
	TextButton resL, resR,
				frameL, frameR,
				confirm, cancel;
	
	TextElement frameText, resText;
	
	public OptionsState(int stateID, ArrayList<HudElement> elements)
			throws SlickException {
		super(stateID, elements);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		
		if(resL.isPressed()||resR.isPressed()){
			if(resL.isPressed()){
				OptionsHandler.resolution=(OptionsHandler.resolution+OptionsHandler.resolutions.length-1)%OptionsHandler.resolutions.length;
			}
			if(resR.isPressed()){
				OptionsHandler.resolution=(OptionsHandler.resolution+1)%OptionsHandler.resolutions.length;
			}
			resText.makeFrom(OptionsHandler.getResolution(OptionsHandler.resolution));
		}
		
		if(frameL.isPressed()||frameR.isPressed()){
			if(frameL.isPressed() && OptionsHandler.frameRate>1){
				OptionsHandler.frameRate--;
			}
			if(frameR.isPressed()){
				OptionsHandler.frameRate++	;
			}
			frameText.makeFrom(Integer.toString(OptionsHandler.frameRate));
		}
		
		if(confirm.isPressed()){
			OptionsHandler.saveSettings();
			DeadReckoningGame.instance.enterState(DeadReckoningGame.MAINMENUSTATE);
		}
		if(cancel.isPressed()){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.MAINMENUSTATE);
		}
	}

	
	@Override
	public void makeFrom(Object O) {}

	@Override
	protected ArrayList<HudElement> makeContents() throws SlickException {
		ArrayList<HudElement> returnElm = new ArrayList<HudElement>(0);
		
		vsynch=new TickerBox(100,-20,HudElement.CENTER_CENTER);
		stretch=new TickerBox(100,20,HudElement.CENTER_CENTER);
		
		returnElm.add(vsynch);
		returnElm.add(stretch);
		
		returnElm.add(new TextElement(120,-20,HudElement.CENTER_CENTER,"V Synch",Color.white,DeadReckoningGame.menuFont));
		returnElm.add(new TextElement(120,20,HudElement.CENTER_CENTER,"Stretch Screen",Color.white,DeadReckoningGame.menuFont));

		
		resL = new TextButton(-250,-20,HudElement.CENTER_CENTER,"<");
		resR = new TextButton(0,-20,HudElement.CENTER_CENTER,">");
		frameL = new TextButton(-250,20,HudElement.CENTER_CENTER,"<");
		frameR = new TextButton(0,20,HudElement.CENTER_CENTER,">");

		returnElm.add(resL);
		returnElm.add(resR);
		returnElm.add(new TextElement(-250,-43,HudElement.CENTER_CENTER,"Resolution",DeadReckoningGame.menuTextColor,DeadReckoningGame.menuFont));
		returnElm.add(frameL);
		returnElm.add(frameR);
		returnElm.add(new TextElement(-250,-3,HudElement.CENTER_CENTER,"Frame Rate",DeadReckoningGame.menuTextColor,DeadReckoningGame.menuFont));

		frameText = new TextElement(-210,15,HudElement.CENTER_CENTER,Integer.toString(OptionsHandler.frameRate),DeadReckoningGame.menuTextColor,DeadReckoningGame.menuFont);
		resText = new TextElement(-210,-25,HudElement.CENTER_CENTER,OptionsHandler.getResolution(OptionsHandler.resolution),DeadReckoningGame.menuTextColor,DeadReckoningGame.menuFont);
		
		returnElm.add(frameText);
		returnElm.add(resText);

		confirm = new TextButton(-250, 100, HudElement.CENTER_CENTER, "CONFIRM");
		cancel = new TextButton(170, 100, HudElement.CENTER_CENTER, "CANCEL");
		
		returnElm.add(confirm);
		returnElm.add(cancel);
		
		return returnElm;
	}
	
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException{
		resText.makeFrom(OptionsHandler.getResolution(OptionsHandler.resolution));
		frameText.makeFrom(Integer.toString(OptionsHandler.frameRate));
		vsynch.makeFrom(OptionsHandler.verticalSynch);
		stretch.makeFrom(OptionsHandler.stretchScreen);
	}

}
