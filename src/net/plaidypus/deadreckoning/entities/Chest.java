package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.grideffects.FadeoutEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Chest extends InteractiveEntity{
	
	static Image chest;
	
	public Chest(Tile t, ArrayList<Item> items){
		super(t);
		this.setLocation(t);
		this.setVisible(true);
		this.setInteractive(false);
		
		this.inventory.add(new Equip(0));
	}
	
	public static void init() throws SlickException{
		chest = new Image("res/chest.png");
		System.out.println(chest);
	}
	
	public void update(GameContainer gc, int delta) {
		if(this.inventory.isEmpty()){
			this.kill();
			this.getParent().addEffectOver(new FadeoutEffect(this.getLocation(),chest));
		}
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return new WaitAction(this.getLocation());
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(chest, x,y);
	}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
