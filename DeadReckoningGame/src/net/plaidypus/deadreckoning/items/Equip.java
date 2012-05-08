package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Equip.
 */
public class Equip extends Item {

	/** The VIS. */
	int STR, WIS, DEX, HP, MP, VIS;

	/** The equip slot. */
	int equipSlot;
	
	/** The class compatability. */
	int[] classCompatability;

	/**
	 * Instantiates a new equip.
	 *
	 * @param itemID the item id
	 */
	public Equip(int itemID) {
		super(itemID, Item.ITEM_EQUIP);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.items.Item#parseItem(java.lang.String)
	 */
	protected void parseItem(String path) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		name = reader.readLine();
		description = reader.readLine();
		image = new Image(reader.readLine());

		equipSlot = Integer.parseInt(reader.readLine());
		STR = Integer.parseInt(reader.readLine());
		WIS = Integer.parseInt(reader.readLine());
		DEX = Integer.parseInt(reader.readLine());
		HP = Integer.parseInt(reader.readLine());
		MP = Integer.parseInt(reader.readLine());
		VIS = Integer.parseInt(reader.readLine());
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.items.Item#stacksWith(net.plaidypus.deadreckoning.items.Item)
	 */
	@Override
	public boolean stacksWith(Item item) {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.items.Item#combineWith(net.plaidypus.deadreckoning.items.Item)
	 */
	@Override
	public Item combineWith(Item item) {
		return null;
	}

}
