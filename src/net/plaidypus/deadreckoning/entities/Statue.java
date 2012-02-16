package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Statue extends StaticImageEntity{
	
	static Image i;
	
	//Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	public Statue(){} 
	
	public Statue(Tile t, int layer) {
		super(t, layer,i);
	}
	
	public void init() throws SlickException{
		 i = new Image("res/statue.png");
	}
	
	@Override
	public void update(GameContainer gc, int delta) {}

	@Override
	public void updateBoardEffects(GameContainer gc, int delta) {}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {return new WaitAction(this);}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(i,x,y);
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		return null;
	}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		System.out.println(" "+attributes[0]+" "+attributes[1]+" "+attributes[2]);
		return new Statue(target.getTileAt(Integer.parseInt(attributes[1]),Integer.parseInt(attributes[2])), Integer.parseInt(attributes[3]));
	}

	@Override
	public String saveToString() {
		return getGenericSave();
	}

	@Override
	public void onDeath() {}

}
