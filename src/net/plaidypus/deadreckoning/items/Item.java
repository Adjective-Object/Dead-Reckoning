package net.plaidypus.deadreckoning.items;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Item {

	int itemID;
	Image image;
	String name, description;

	public Item(int itemID) {
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
	}

	protected abstract void parseItem(String path) throws IOException, SlickException;

	public Image getImage() {
		return image;
	}
}
