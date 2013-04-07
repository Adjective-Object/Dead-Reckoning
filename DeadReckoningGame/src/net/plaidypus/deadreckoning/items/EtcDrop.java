package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class EtcDrop.
 */
public class EtcDrop extends Item {

	/**
	 * Instantiates a new etc drop.
	 * 
	 * @param itemID
	 *            the item id
	 * @param number
	 *            the number
	 */
	public EtcDrop(String parentMod, int itemID, int numInStack)
			throws SlickException {
		super(parentMod, itemID, Item.ITEM_ETC);
		this.stacks = numInStack;
	}
	
	// type-modpack-itemnumber-stacksmin-stacksmax
	public static Item loadFromString(String[] s) throws NumberFormatException,
			SlickException {
		return new EtcDrop( s[1], Integer.parseInt(s[2]),  Utilities.randInt(Integer.parseInt(s[3]) , Integer.parseInt(s[4]) ));
	}
	
	@Override
	public String toItemString() {
		return this.classification + "-" + this.parentMod + "-" + this.itemID + "-" + this.stacks + "-" + this.stacks;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#parseItem(java.lang.String)
	 */
	@Override
	protected void parseItem(InputStream in) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		name = reader.readLine();
		description = Utilities.collapseNewlines(reader.readLine());
		image = ModLoader.loadImage(reader.readLine());
		goldvalue = Integer.parseInt(reader.readLine());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.items.Item#stacksWith(net.plaidypus.deadreckoning
	 * .items.Item)
	 */
	@Override
	public boolean stacksWith(Item item) {
		if (item.classification == ITEM_ETC) {
			return item.itemID == this.itemID;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.items.Item#render(org.newdawn.slick.Graphics,
	 * int, int)
	 */
	@Override
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		g.setFont(DeadReckoningGame.menuSmallFont);
		g.drawString(Integer.toString(this.stacks),
				x+32-DeadReckoningGame.menuSmallFont.getWidth(Integer.toString(this.stacks)),
				y+32-DeadReckoningGame.menuSmallFont.getHeight(Integer.toString(this.stacks)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#combineWith(net.plaidypus.
	 * deadreckoning.items.Item)
	 */
	@Override
	public Item combineWith(Item item) {
		EtcDrop drop = (EtcDrop) item;
		this.stacks += drop.stacks;
		return this;
	}
	
	@Override
	public Item getSingleCopy() throws SlickException {
		return new EtcDrop(this.parentMod, this.itemID, 1) ;
	}

}
