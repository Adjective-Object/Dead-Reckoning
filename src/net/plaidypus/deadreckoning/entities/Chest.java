package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Chest extends InteractiveEntity{
	
	static Image chest;
	ArrayList<Item> items;
	
	public Chest(Tile t, ArrayList<Item> items){
		super(t);
		this.setLocation(t);
		this.items=items;
		this.setVisible(true);
		this.setInteractive(false);
	}
	
	public static void init() throws SlickException{
		chest = new Image("res/chest.png");
		System.out.println(chest);
	}
	
	public void update(GameContainer gc, int delta) {
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
	}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return new WaitAction(this.getLocation());
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(chest, x,y);
	}

}
