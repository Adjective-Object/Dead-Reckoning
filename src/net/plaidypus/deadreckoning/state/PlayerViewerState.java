package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.button.Button;
import net.plaidypus.deadreckoning.hudelements.button.ImageButton;
import net.plaidypus.deadreckoning.hudelements.game.substates.ReturnToGameElement;
import net.plaidypus.deadreckoning.hudelements.game.substates.StatDisplayElement;
import net.plaidypus.deadreckoning.hudelements.simple.Panel;
import net.plaidypus.deadreckoning.hudelements.simple.StillImageElement;
import net.plaidypus.deadreckoning.hudelements.simple.TextElement;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.professions.SkillProgression;

public class PlayerViewerState extends HudLayersState{
	
	static final int ofX=50, ofY=50;
	
	Panel buttonPanel, statPanel;
	
	Profession sourceProf;
	
	public PlayerViewerState(int stateID) {
		super(stateID, makeState());
		this.buttonPanel=(Panel) this.HudElements.get(1);
		this.statPanel = (Panel) this.HudElements.get(2);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container,game,delta);
		
		for(int i=0; i<buttonPanel.getContents().size()-1; i++){
			Button b = (Button)(buttonPanel.getContents().get(i));
			if(b.isPressed() && sourceProf.skillPoints>0){
				sourceProf.skillPoints--;
				sourceProf.getTrees()[i/4].getSkills()[i%4].levelUp();
				bakeFromProfession(sourceProf);
			}
		}
	}
	
	public void makeFrom(Object[] args){
		this.HudElements.get(0).makeFrom(args[0]);
		Player p = (Player)(args[1]);
		System.out.println(p);
		this.sourceProf = p.getProfession();
		
		bakeFromProfession(sourceProf);
		
		this.buttonPanel.bakeBorders();
		this.statPanel.makeFrom( new Object[]{p.profession.getPortriat(),p} );
		
	}
	
	public void bakeFromProfession(Profession p){
		for(int i=0; i<p.getTrees().length; i++){
			SkillProgression prog = p.getTrees()[i];
			for(int s=0; s<prog.getSkills().length; s++){
				this.buttonPanel.getContents().get(i*prog.getSkills().length+s).makeFrom(prog.getSkills()[s].getImage());
				this.buttonPanel.getContents().get(i*prog.getSkills().length+s).setMouseoverText(
						prog.getSkills()[s].getName()+" ("+prog.getSkills()[s].getLevel()+")\n"+prog.getSkills()[s].getDescription());
			}
		}
		
		this.buttonPanel.getContents().get(this.buttonPanel.getContents().size()-1).makeFrom("Remaining SP: "+Integer.toString(p.skillPoints));
		
	}
	
	public static ArrayList<HudElement> makeState(){
		ArrayList<HudElement> elements = new ArrayList<HudElement>(0);
		elements.add(new StillImageElement(0,0,HudElement.TOP_LEFT));
		
		ArrayList<HudElement> skillButton = new ArrayList<HudElement>(12), playerWindow = new ArrayList<HudElement>(2);
		for(int i=0; i<4; i++){
			for(int x=0; x<3; x++){
				skillButton.add(new ImageButton(ofX+55*x+5,ofY+5+60*i,HudElement.TOP_LEFT,null));
			}
		}
		
		skillButton.add(new TextElement(50,270,HudElement.TOP_LEFT, "", DeadReckoningGame.menuTextColor, DeadReckoningGame.menuSmallFont));
		
		playerWindow.add( new StillImageElement(-200,0,HudElement.CENTER_RIGHT));
		playerWindow.add( new StatDisplayElement(-200,69,HudElement.CENTER_RIGHT));//TODO replace with stat display element;
		
		
		elements.add(new Panel(skillButton));
		elements.add(new Panel(playerWindow));
		
		elements.add(new ReturnToGameElement());
	
		return elements;
	}


}
