package net.plaidypus.deadreckoning.hudelements.game.substates;

import java.util.ArrayList;
import java.util.Random;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.simple.SimplePanel;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.state.HudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StoreContents extends SimplePanel {
	public ArrayList<Item> contents;

	int externalBorder, internalBorder;
	static final int
		displayGrid = 5,
		tileHeight = 50 ,tileWidth = 100,
		imageoffx = 20, imageoffy=12
	;
	
	public int selector;
	protected ArrayList<Integer> prices;
	
	public int fromLastSelect, selectThreshold=1000;
	Item clickedItem;
	
	static Image tileimage;

	public StoreContents(int x, int y, int borderMeth) {
		this(x, y, borderMeth, 2, 10);
	}

	public StoreContents(int x, int y, int bindMethod, int internalBorder, int externalBorder) {
		super(x, y,
				externalBorder, externalBorder, bindMethod);
		this.internalBorder = internalBorder;
		this.selector = -1;
	}
	
	@Override
	public void makeFrom(Object o) {
		this.selector = -1;
		InteractiveEntity e = (InteractiveEntity) (o);
		this.setContents(e.getInventory());
		Random r = new Random(e.getX()+e.getY());
		prices = new ArrayList<Integer>(this.contents.size());
		for(int i=0; i<this.contents.size(); i++){
			prices.set(i, (int)( this.contents.get(i).goldvalue*(0.5+r.nextFloat()) )  );
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		tileimage = new Image("res/lootscreen/ItemSlot.png");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input i = gc.getInput();
		
		fromLastSelect+=delta;
		
		if (i.getMouseX() > this.getAbsoluteX()
				&& i.getMouseY() > this.getAbsoluteY()
				&& i.getMouseX() < this.getAbsoluteX() + this.getWidth()
				&& i.getMouseY() < this.getAbsoluteY() + this.getHeight()) {
			int k = (i.getMouseY()-this.getAbsoluteY())/(this.tileHeight+this.internalBorder);
			if(this.contents.size()>k){
				this.setMouseoverText(this.contents.get(k).getMouseoverText());
			}
			else{
				this.setMouseoverText(null);
			}
			if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){	
				selector = k;
				fromLastSelect=0;
				if(HudLayersState.doubleClick){
					this.clickedItem=this.getSelected();
				}
			}
		}
		else if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) || fromLastSelect>selectThreshold){
			selector=-1;
			clickedItem=null;
		}
	}

	public void setContents(ArrayList<Item> newcont) {
		this.contents = newcont;
	}

	public ArrayList<Item> getContents() {
		return contents;
	}
	
	public void addItem(Item e){
		if(e!=null){
			this.contents.add(e);
		}
		this.selector=-1;
	}

	public int getSelectedGoldPrice(){
		return this.prices.get(this.selector);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		g.setColor(DeadReckoningGame.menuBackgroundColor);
		g.fillRect(this.getAbsoluteX(),this.getAbsoluteY(), this.getWidth(), this.getHeight());
		for (int i = 0;  i < displayGrid; i++) {
			g.setColor(DeadReckoningGame.menuBackgroundColor);
			g.setColor(DeadReckoningGame.menuHighlightColor);
			g.fillRect(
					this.getAbsoluteX() + externalBorder +internalBorder,
					this.getAbsoluteY() + externalBorder + internalBorder*i ,
					tileWidth,
					tileHeight );
			g.drawImage(
					this.contents.get(i).getImage(),
					getAbsoluteX() + externalBorder +imageoffx,
					getAbsoluteY() + externalBorder + (tileHeight+this.internalBorder)*i +imageoffy );
			if (selector == i) {
				g.setColor(DeadReckoningGame.menuTextColor);
				g.drawRect(
						getAbsoluteX() + externalBorder,
						getAbsoluteY() + externalBorder + (tileHeight+this.internalBorder)*i +imageoffy,
						tileWidth,
						tileHeight );
			}
		}
	}
	
	@Override
	public int getWidth(){
		return this.externalBorder*2+StoreContents.tileWidth;
	} 
	@Override
	public int getHeight(){
		return this.externalBorder*2+StoreContents.tileHeight*StoreContents.displayGrid;
	}
	
	public Item getSelected() {
		if(contents.size()>selector && selector!=-1){
			return contents.get(selector);
		}
		return null;
	}

	public boolean isValidSelected() {
		return contents.size() > selector;
	}

	public void clearSelected() {
		this.contents.remove(selector);
	}

	public Item getClicked() {
		return this.clickedItem;
	}
	
	public void clearClicked() {
		this.clickedItem=null;
	}

	//returns true if item is removed altogether
	public Item removeItem(Item item) throws SlickException{
		if(item.stacks==1){
			this.contents.remove(contents.indexOf(item));
			this.selector=-1;
			return item;
		} else{
			item.stacks-=1;
			return item.getSingleCopy();
		}
	}
}