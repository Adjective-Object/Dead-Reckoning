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
	
	public Chest(String stringCode){
		super(stringCode);
	}
	
	public Chest(Tile t, ArrayList<Item> items){
		super(t);
		this.setLocation(t);
		this.setVisible(true);
		this.inventory.addAll(items);
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
		ArrayList<Item> content = new ArrayList<Item>(0);
		for(int i=3; i<toload.length; i++){
			content.add(new EtcDrop(Integer.parseInt(toload[i])));//TODO equip parsing
		}
		return new Chest(g.getTileAt(Integer.parseInt(toload[1]),Integer.parseInt(toload[2])),content);
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

}
