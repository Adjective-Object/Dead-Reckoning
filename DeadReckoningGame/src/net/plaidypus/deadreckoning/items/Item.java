package net.plaidypus.deadreckoning.items;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

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
	public int classification;

	/** The Constants for defining item type */
	public static final int ITEM_ETC = 0, ITEM_USE = 1, ITEM_EQUIP = 2;
	public static final Class<? extends Item>[] itemTypes = new Class[] {EtcDrop.class, UseItem.class, Equip.class};
	
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
	public Item(String parentMod, Integer itemID, int classification) throws SlickException{
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

	public String toItemString(){
		return this.classification+"-"+this.parentMod+"-"+this.itemID;
	}
	
	//itemtype, sourcemod, itemID
	public static Item loadFromString(String[] spl){
		try {
			return itemTypes[Integer.parseInt(spl[0])].getConstructor(String.class, Integer.class).newInstance(spl[1],Integer.parseInt(spl[2]));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Image getImage(){
		return image;
	}

	public String getMouseoverText() {
		return this.name+"\n"+
				this.description;
	}

}
