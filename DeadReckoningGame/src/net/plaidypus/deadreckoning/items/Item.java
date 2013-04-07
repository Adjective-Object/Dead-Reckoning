package net.plaidypus.deadreckoning.items;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class Item.
 */
public abstract class Item {

	/** The classification. */
	public int classification;

	/** The Constants for defining item type */
	public static final int ITEM_ETC = 0, ITEM_USE = 1, ITEM_EQUIP = 2;
	public static final Class<? extends Item>[] itemTypes = new Class[] {
			EtcDrop.class, UseItem.class, Equip.class };

	/** The item id. */
	int itemID;
	int goldvalue;
	public int stacks =1;

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
	protected Item(String parentMod, Integer itemID, int classification)
			throws SlickException {
		this.itemID = itemID;
		try {
			parseItem(ModLoader.getLoaderFor(parentMod).getResourceAsStream(
					parentMod + "/items/" + Integer.toString(itemID) + ".item"));
		} catch (IOException e) {
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

	public String toItemString() {
		return this.classification + "-" + this.parentMod + "-" + this.itemID;
	}

	// itemtype, sourcemod, itemID
	public static Item generateItemFromString(String[] spl) throws SlickException {
		try {
			Utilities.logArray(spl);
			return (Item) (itemTypes[Integer.parseInt(spl[0])].getMethod(
					"loadFromString", String[].class).invoke( null , new Object[] {spl} ));
		} catch (NumberFormatException e) {
			throw new SlickException("bad num format", e);
		} catch (IllegalArgumentException e) {
			throw new SlickException("illegal args", e);
		} catch (SecurityException e) {
			throw new SlickException("se. except", e);
		} catch (IllegalAccessException e) {
			throw new SlickException("illegal access", e);
		} catch (InvocationTargetException e) {
			throw new SlickException(Utilities.getArrayString(spl), e);
		} catch (NoSuchMethodException e) {
			throw new SlickException("method no se existe", e);
		}
	}

	public Image getImage() {
		return image;
	}

	/**
	 * Collapse item array.
	 * 
	 * @param in
	 *            the in
	 * @return the array list
	 */
	public static ArrayList<Item> collapseItemArray(ArrayList<Item> in) {
		for (int i = 1; i < in.size(); i++) {
			for (int x = 0; x < i; x++) {
				Log.debug(i+" "+x);
				if (in.get(i).stacksWith(in.get(x))) {
					in.set(x, in.get(x).combineWith(in.get(i)));
					in.remove(i);
					i-=1;
					x-=1;
				}
			}
		}
		return in;
	}

	public String getMouseoverText() {
		return this.name + "\n" + this.description;
	}
	
	public abstract Item getSingleCopy() throws SlickException;

}
