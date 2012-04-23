package net.plaidypus.deadreckoning.items;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Item.
 */
public abstract class Item {

	/** The classification. */
	int classification;
	
	/** The Constant ITEM_EQUIP. */
	public static final int ITEM_ETC = 0, ITEM_USE = 1, ITEM_EQUIP = 2;
	
	/** The item id. */
	int itemID;
	
	/** The image. */
	Image image;
	
	/** The description. */
	String name, description;

	/**
	 * Instantiates a new item.
	 *
	 * @param itemID the item id
	 * @param classification the classification
	 */
	public Item(int itemID, int classification) {
		this.itemID = itemID;
		try {
			parseItem("res/" + Integer.toString(itemID) + ".item");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.classification = classification;
	}

	/**
	 * Parses the item.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SlickException the slick exception
	 */
	protected abstract void parseItem(String path) throws IOException,
			SlickException;

	/**
	 * Render.
	 *
	 * @param g the g
	 * @param x the x
	 * @param y the y
	 */
	public void render(Graphics g, int x, int y) {
		g.drawImage(this.image, x, y);
	}

	/**
	 * Stacks with.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	public abstract boolean stacksWith(Item item);

	/**
	 * Combine with.
	 *
	 * @param item the item
	 * @return the item
	 */
	public abstract Item combineWith(Item item);

}
