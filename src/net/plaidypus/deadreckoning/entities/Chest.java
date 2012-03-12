package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.FadeoutEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.items.EtcDrop;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Chest extends InteractiveEntity{
	
	static Image chest;
	
	//Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	public Chest(){} 
	
	public Chest(Tile t, int layer, ArrayList<Item> items){
		super(t, layer);
		this.setLocation(t);
		this.setVisible(true);
		this.inventory.addAll(items);
		this.setName("a chest");
	}
	
	public void init() throws SlickException{
		Chest.chest = new Image("res/chest.png");
		System.out.println(chest);
	}
	
	public void update(GameContainer gc, int delta) {
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
		if(this.inventory.isEmpty()){
			this.kill();
		}
	}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(chest, x,y);
	}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		ArrayList<Item> content = new ArrayList<Item>(0);
		for(int i=4; i<toload.length; i++){
			content.add(new EtcDrop(Integer.parseInt(toload[i]),1));//TODO equip parsing
		}
		return new Chest(g.getTileAt(Integer.parseInt(toload[1]),Integer.parseInt(toload[2])),Integer.parseInt(toload[3]),content);
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

	@Override
	public void onDeath() {
		this.getParent().addEffectOver(new FadeoutEffect(this.getLocation(),chest));
	}

}
