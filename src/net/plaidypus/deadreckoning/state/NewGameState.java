package net.plaidypus.deadreckoning.state;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.hudelements.Button;
import net.plaidypus.deadreckoning.hudelements.ColorFiller;
import net.plaidypus.deadreckoning.hudelements.ImageButton;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.Panel;
import net.plaidypus.deadreckoning.hudelements.TextElement;
import net.plaidypus.deadreckoning.hudelements.TextEntryBox;
import net.plaidypus.deadreckoning.professions.Profession;

public class NewGameState extends HudLayersState{
	
	public ArrayList<ImageButton> classButtons;
	public ArrayList<Profession> professions;
	
	public ImageButton newClassButton;
	public TextEntryBox text;
	
	int columA = 15, columB = 300;
	
	public NewGameState(int stateID) throws SlickException{
		super(stateID, new HudElement[0]);
		
		loadClasses();
	}
	
	public void loadClasses() throws SlickException{
		this.HudElements= new ArrayList<HudElement>(0);
		
		classButtons = new ArrayList<ImageButton>(0);
		ArrayList<HudElement> elim = new ArrayList<HudElement>(0);
		ArrayList<HudElement> elimB = new ArrayList<HudElement>(0);
		
		professions = new ArrayList<Profession>(0);
		
		for(int i=0; i<Profession.enumerateProfessions(); i++){
			String className = "WHOP";
			try{
				BufferedReader r = new BufferedReader(new FileReader(new File("res/professions/"+i+"/ClassTraits.txt")));
				className = r.readLine();
				r.close();
			}catch (Exception e){e.printStackTrace();}
			
			ImageButton img = new ImageButton(columA, 20+(i)*79, HudElement.TOP_LEFT, new Image("res/professions/"+i+"/Portrait.png"));
			classButtons.add(img);
			professions.add(new Profession(i));
			elimB.add(img);
			elimB.add(new TextElement(columA+69, 42+(i)*79, HudElement.TOP_LEFT, className, Color.white, DeadReckoningGame.menuFont));
		}
		
		
		newClassButton = new ImageButton(columB, 20, HudElement.TOP_LEFT, new Image("res/newClassButton.png"));
		elim.add(newClassButton);
		elim.add(new TextElement(columB+69,42,HudElement.TOP_LEFT, "New Class", Color.white, DeadReckoningGame.menuFont));
		
		
		int numClasses = 0;
		File f = new File("classes/"+numClasses+".txt");
		while(f.exists()){
			int baseClass=0;
			String className = "Nameless";
			try{
				BufferedReader r = new BufferedReader(new FileReader(f));
				className = r.readLine();
				baseClass = r.read();
				r.close();
			}
			catch(IOException e){}
			ImageButton i = new ImageButton(columB, 20+(numClasses+1)*79, HudElement.TOP_LEFT, new Image("res/professions/"+baseClass+"/Portrait.png"));
			classButtons.add(i);
			professions.add(Profession.loadFromFile(f));
			elim.add(i);
			elim.add(new TextElement(columB+69, 42+(numClasses+1)*79, HudElement.TOP_LEFT, className, Color.white, DeadReckoningGame.menuFont));
			numClasses++;
			f = new File("classes/"+numClasses+".txt");
		}
		
		ArrayList<HudElement> elimC = new ArrayList<HudElement>(0);
		text = new TextEntryBox(-250, 50, HudElement.TOP_RIGHT, 200, 50);
		elimC.add(text);
		
		this.HudElements.add(new ColorFiller(DeadReckoningGame.menuBackgroundColor));
		this.HudElements.add(new Panel(elim) );
		this.HudElements.add(new Panel(elimB));
		this.HudElements.add(new Panel(elimC));
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(newClassButton.isPressed()){
			//TODO make new classes
		}
		
		for(int i=0; i<classButtons.size(); i++){
			if(classButtons.get(i).isPressed() && !text.getContent().equals("")){
				try{
					Save s = Save.makeNewSave("saves/SAVE "+(Save.enumerateSaves())+"/",text.getContent(), professions.get(i));
					s.loadGame( GameplayElement.class.cast(HudLayersState.class.cast(DeadReckoningGame.instance.getState(DeadReckoningGame.GAMEPLAYSTATE)).getElement(0) ));
					game.enterState(DeadReckoningGame.GAMEPLAYSTATE);
				}
				catch(IOException e)			{e.printStackTrace();}
				catch (ClassNotFoundException e){e.printStackTrace();}
				catch (InstantiationException e){e.printStackTrace();}
				catch (IllegalAccessException e){e.printStackTrace();}
			}
		}
	}

}