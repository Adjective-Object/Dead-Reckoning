package net.plaidypus.deadreckoning.hudelements.game.substates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.simple.SimplePanel;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.state.HudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EquipGridElement extends SimplePanel{
	
	static Image equipBackground;
	
	int selected;
	
	static final int[][] itemCoords = new int[][] {
		new int[] {70,50},
		new int[] {70,100}, 
		new int[] {130,100},
		new int[] {70,200},
		new int[] {40,100},
		new int[] {100,100},
		new int[] {40,150},
		new int[] {100,150},
	};
	
	ArrayList<Equip> contents = new ArrayList<Equip>(8);
	Equip clickedEquip;
	public Player target;
	
	public EquipGridElement(int x, int y, int bindMethod) {
		super(x, y, 168, 270,10,10,bindMethod);
	}
	
	public void makeFrom(Player p){
		this.contents = p.getEquips();
		target = p;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		equipBackground = new Image("res/lootScreen/PlayerImage.png");
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input i = gc.getInput();
		if (i.getMouseX() > this.getAbsoluteX()
				&& i.getMouseY() > this.getAbsoluteY()
				&& i.getMouseX() < this.getAbsoluteX() + this.getWidth()
				&& i.getMouseY() < this.getAbsoluteY() + this.getHeight()) {
			int rx = i.getMouseX() - this.getAbsoluteX();
			int ry = i.getMouseY() - this.getAbsoluteY();
			
			boolean nothingMoused = true;
			for(int x=0; x<itemCoords.length; x++){
				if( rx>itemCoords[x][0] &&
					rx<itemCoords[x][0]+32 &&
					ry>itemCoords[x][1] &&
					ry<itemCoords[x][1]+32 &&
					contents.get(x)!=null){
					this.setMouseoverText(contents.get(x).getMouseoverText());
					nothingMoused=false;
					if(HudLayersState.doubleClick){
						this.clickedEquip=contents.get(x);
					}
				}
			}
			if(nothingMoused){
				this.setMouseoverText(null);
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		g.drawImage(equipBackground,
				this.getAbsoluteX()+this.getWidth()/2-equipBackground.getWidth()/2,
				this.getAbsoluteY()+this.getHeight()/2-equipBackground.getHeight()/2);
		for(int i=0; i<contents.size(); i++){
			if(contents.get(i)!=null){
				g.drawImage(contents.get(i).getImage(), getAbsoluteX() + itemCoords[i][0], getAbsoluteY() + itemCoords[i][1]);
			}
		}
	}
	
	public Equip getClickedEquip(){
		return this.clickedEquip;
	}
	
	public void clearClickedEquip(){
		this.clickedEquip=null;
	}
	
	public void removeEquip(Equip e){
		this.contents.set(e.getSlot(),null);
	}
}
