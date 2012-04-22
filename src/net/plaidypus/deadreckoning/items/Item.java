package net.plaidypus.deadreckoning.items;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Item {

	int classification;
	public static final int ITEM_ETC = 0, ITEM_USE = 1, ITEM_EQUIP = 2;
	int itemID;
	Image image;
	String name, description;

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

	protected abstract void parseItem(String path) throws IOException,
			SlickException;

	public void render(Graphics g, int x, int y) {
		g.drawImage(this.image, x, y);
	}

	public abstract boolean stacksWith(Item item);

	public abstract Item combineWith(Item item);

}
