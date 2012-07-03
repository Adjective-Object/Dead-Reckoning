package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class EtcDrop.
 */
public class EtcDrop extends Item {

	/** The number. */
	int number;

	/**
	 * Instantiates a new etc drop.
	 * 
	 * @param itemID
	 *            the item id
	 * @param number
	 *            the number
	 */
	public EtcDrop(String parentMod, int itemID, int number) throws SlickException{
		super(parentMod, itemID, Item.ITEM_ETC);
		this.number = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#parseItem(java.lang.String)
	 */
	protected void parseItem(InputStream in) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		name = reader.readLine();
		description = reader.readLine();
		image = ModLoader.loadImage(reader.readLine());
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
	public void render(Graphics g, int x, int y) {
		super.render(g, x, y);
		g.drawString(Integer.toString(this.number), x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#combineWith(net.plaidypus.
	 * deadreckoning.items.Item)
	 */
	@Override
	public Item combineWith(Item item){
		EtcDrop drop = (EtcDrop) item;
		this.number +=drop.number;
		return this;
	}

}
