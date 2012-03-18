package net.plaidypus.deadreckoning.state;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.SaveFilter;
import net.plaidypus.deadreckoning.hudelements.Button;
import net.plaidypus.deadreckoning.hudelements.ColorFiller;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;
import net.plaidypus.deadreckoning.hudelements.TextButton;

public class SaveSelectorState extends ExclusiveHudLayersState{
	
	static Save[] saves;
	
	public SaveSelectorState(int stateID) throws SlickException {
		super(stateID, makeElementsList());
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		
		if(this.focus!=-1){
		TextButton currentPressed = (TextButton)this.HudElements.get(this.focus);
			if(currentPressed.isPressed()){
				try {
					HudLayersState h = (HudLayersState) DeadReckoningGame.instance.getState(DeadReckoningGame.GAMEPLAYSTATE);
					GameplayElement g = (GameplayElement)(h.getElement(0));
					if(this.focus>=2){
						saves[focus-2].loadGame( GameplayElement.class.cast(HudLayersState.class.cast(DeadReckoningGame.instance.getState(DeadReckoningGame.GAMEPLAYSTATE)).getElement(0) ));
						DeadReckoningGame.instance.enterState(DeadReckoningGame.GAMEPLAYSTATE);
					}
					else if (this.focus==1){
						DeadReckoningGame.instance.enterState(DeadReckoningGame.NEWGAMESTATE);
						
					}
				
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static HudElement[] makeElementsList() throws SlickException{
		File f = new File("saves/");
		System.out.println(f.list());
		String[] savesList = f.list(new SaveFilter());
		for(int i=0; i<savesList.length; i++){
			System.out.println(savesList[i]);
		}
		HudElement[] buttons = new HudElement[savesList.length+2];
		saves = new Save[savesList.length];
		
		buttons[0]=new ColorFiller(DeadReckoningGame.menuBackgroundColor);
		buttons[1]=new TextButton(10,30,HudElement.TOP_LEFT,new Color(30,50,70),new Color(40,60,80),new Color(60,80,100),"New Game",DeadReckoningGame.menuFont);
		
		for(int i=0; i<savesList.length; i++)
		{
			saves[i]= new Save("saves/"+savesList[i]);
			buttons[i+2]=new TextButton(10,(i+2)*30,HudElement.TOP_LEFT,new Color(30,50,70),new Color(40,60,80),new Color(60,80,100),saves[i].getName(),DeadReckoningGame.menuFont);
		}
		
		return buttons;
	}
	
}
