package net.plaidypus.deadreckoning.hudelements.game.substates;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.simple.SimplePanel;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ItemGridElement extends SimplePanel {
	public ArrayList<Item> contents;

	int gridWidth, gridHeight, externalBorder, internalBorder;
	public int selector;
	
	public int fromLastSelect, selectThreshold=1000;
	Item clickedItem;
	
	static Image tileimage;

	public ItemGridElement(int x, int y, int borderMeth) {
		this(x, y, borderMeth, 5, 8, 2, 10);
	}

	public ItemGridElement(int x, int y, int bindMethod,
			int gridWidth, int gridHeight, int internalBorder, int externalBorder) {
		super(x, y,
				gridWidth*(32+internalBorder)-internalBorder, gridHeight*(32+internalBorder)-internalBorder,
				externalBorder, externalBorder, bindMethod);
		this.internalBorder = internalBorder;
		this.selector = -1;
		this.gridWidth=gridWidth;
		this.gridHeight=gridHeight;
	}

	@Override
	public void makeFrom(Object o) {
		this.selector = -1;
		InteractiveEntity e = (InteractiveEntity) (o);// TODO making
		this.setContents(e.getInventory());
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		tileimage = new Image("res/lootscreen/ItemSlot.png");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input i = gc.getInput();
		if (i.isKeyPressed(Input.KEY_LEFT)) {
			selector = Utilities.limitTo(selector - 1, 0, gridWidth * gridHeight);
		}
		if (i.isKeyPressed(Input.KEY_RIGHT)) {
			selector = Utilities.limitTo(selector + 1, 0, gridWidth * gridHeight);
		}
		if (i.isKeyPressed(Input.KEY_UP)) {
			selector = Utilities.limitTo(selector - gridWidth, 0, gridWidth * gridHeight);
		}
		if (i.isKeyPressed(Input.KEY_DOWN)) {
			selector = Utilities.limitTo(selector + gridWidth, 0, gridWidth * gridHeight);
		}
		
		fromLastSelect+=delta;
		
		if (i.getMouseX() > this.getAbsoluteX()
				&& i.getMouseY() > this.getAbsoluteY()
				&& i.getMouseX() < this.getAbsoluteX() + this.getWidth()
				&& i.getMouseY() < this.getAbsoluteY() + this.getHeight()) {
			int rx = (i.getMouseX() - this.getAbsoluteX() - externalBorder)
					/ (DeadReckoningGame.tileSize + internalBorder);
			int ry = (i.getMouseY() - this.getAbsoluteY() - externalBorder)
					/ (DeadReckoningGame.tileSize + internalBorder);
			
			int k =rx + ry * gridWidth;
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

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		super.render(gc, sbg, g);
		for (int xm = 0; xm < gridWidth; xm++) {
			for (int ym = 0; ym < gridHeight; ym++) {
				g.drawImage(tileimage, getAbsoluteX() + externalBorder + xm
						* (DeadReckoningGame.tileSize + internalBorder), getAbsoluteY()
						+ externalBorder + ym
						* (DeadReckoningGame.tileSize + internalBorder));
				if (xm + ym * gridWidth < contents.size()) {
					contents.get(xm + ym * gridWidth)
							.render(g,
									getAbsoluteX()
											+ externalBorder
											+ xm
											* (DeadReckoningGame.tileSize + internalBorder),
									getAbsoluteY()
											+ externalBorder
											+ ym
											* (DeadReckoningGame.tileSize + internalBorder));
				}
				if (selector == xm + ym * gridWidth) {
					g.drawRect(
							getAbsoluteX()
									+ externalBorder
									+ xm
									* (DeadReckoningGame.tileSize + internalBorder),
							getAbsoluteY()
									+ externalBorder
									+ ym
									* (DeadReckoningGame.tileSize + internalBorder),
							DeadReckoningGame.tileSize,
							DeadReckoningGame.tileSize);
				}
			}
		}
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
