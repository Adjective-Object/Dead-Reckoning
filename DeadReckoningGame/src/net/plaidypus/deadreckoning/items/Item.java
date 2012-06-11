package net.plaidypus.deadreckoning.items;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.plaidypus.deadreckoning.modloader.ModLoader;

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
	String name, description, parentMod;

	/**
	 * Instantiates a new item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param classification
	 *            the classification
	 */
	public Item(String parentMod, int itemID, int classification) {
		this.itemID = itemID;
		try {
			System.out.println("ITEM AT " + parentMod + "/items/"
					+ Integer.toString(itemID) + ".item");
			parseItem(ModLoader.getLoaderFor(parentMod).getResourceAsStream(
					parentMod + "/items/" + Integer.toString(itemID) + ".item"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.classification = classification;
		this.parentMod = parentMod;
	}

	/**
	 * Parses the item.
	 * 
	 * @param path
	 *            the path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SlickException
	 *             the slick exception
	 */
	protected abstract void parseItem(InputStream i) throws IOException,
			SlickException;

	/**
	 * Render.
	 * 
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void render(Graphics g, int x, int y) {
		g.drawImage(this.image, x, y);
	}

	/**
	 * Stacks with.
	 * 
	 * @param item
	 *            the item
	 * @return true, if successful
	 */
	public abstract boolean stacksWith(Item item);

	/**
	 * Combine with.
	 * 
	 * @param item
	 *            the item
	 * @return the item
	 */
	public abstract Item combineWith(Item item);

}
