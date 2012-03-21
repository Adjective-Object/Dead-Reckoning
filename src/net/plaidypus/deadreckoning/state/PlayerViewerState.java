package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.ImageButton;
import net.plaidypus.deadreckoning.hudelements.Panel;
import net.plaidypus.deadreckoning.hudelements.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.StatDisplayElement;
import net.plaidypus.deadreckoning.hudelements.StillImageElement;
import net.plaidypus.deadreckoning.professions.SkillProgression;

public class PlayerViewerState extends HudLayersState{
	
	static final int ofX=50, ofY=50;
	
	Panel buttonPanel, statPanel;
	
	public PlayerViewerState(int stateID) {
		super(stateID, makeState());
		this.buttonPanel=(Panel) this.HudElements.get(1);
		this.statPanel = (Panel) this.HudElements.get(2);
	}
	
	public void makeFrom(Object[] args){
		this.HudElements.get(0).makeFrom(args[0]);
		Player p = (Player)(args[1]);
		System.out.println(p);
		
		LinkedList<Object> objs = new LinkedList<Object>();
		for(int i=0; i<p.getProfession().getTrees().length; i++){
			SkillProgression prog = p.getProfession().getTrees()[i];
			for(int s=0; s<prog.getSkills().length; s++){
				objs.add(prog.getSkills()[s].getImage());
			}
		}
		
		this.buttonPanel.makeFrom(objs);
		this.statPanel.makeFrom( new Object[]{p.profession.getPortriat(),p} );
		
	}
	
	public static ArrayList<HudElement> makeState(){
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		elements.add(new StillImageElement(0,0,HudElement.TOP_LEFT));
		
		ArrayList<HudElement> skillButton = new ArrayList<HudElement>(12), playerWindow = new ArrayList<HudElement>(2);
		for(int i=0; i<4; i++){
			for(int x=0; x<3; x++){
				skillButton.add(new ImageButton(ofX+50*x+5,ofY+5+60*i,HudElement.TOP_LEFT,null));
			}
		}
		
		playerWindow.add( new StillImageElement(-200,0,HudElement.CENTER_RIGHT));
		playerWindow.add( new StatDisplayElement(-200,69,HudElement.CENTER_RIGHT));//TODO replace with stat display element;
		
		
		elements.add(new Panel(skillButton));
		elements.add(new Panel(playerWindow));
		
		elements.add(new ReturnToGameElement());
	
		return elements;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	throws SlickException {
		for(int i=0; i<HudElements.size(); i++){
			HudElements.get(i).update(container,game,delta);
		}
	}

}