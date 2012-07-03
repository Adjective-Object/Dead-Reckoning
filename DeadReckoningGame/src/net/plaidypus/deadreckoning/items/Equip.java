package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Equip.
 */
public class Equip extends Item {

	/** The VIS. */
	public int STR, INT, DEX, LUK, HP, MP, MDef, MAtt, WDef, WAtt;

	/** The equip slot. */
	int equipSlot;

	/** The class compatability. */
	int[] classCompatability;

	/**
	 * Instantiates a new equip, loaded from the itemID.
	 * 
	 * @param itemID
	 *            the item id
	 */
	public Equip(String parentMod, Integer itemID) throws SlickException{
		super(parentMod, itemID, Item.ITEM_EQUIP);
	}

	/**
	 * /* (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#parseItem(java.lang.String)
	 */
	protected void parseItem(InputStream in) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		name = reader.readLine();
		description = reader.readLine();
		image = ModLoader.loadImage(reader.readLine());

		equipSlot = Integer.parseInt(reader.readLine());
		STR = Integer.parseInt(reader.readLine());
		INT = Integer.parseInt(reader.readLine());
		DEX = Integer.parseInt(reader.readLine());
		DEX = Integer.parseInt(reader.readLine());
		HP = Integer.parseInt(reader.readLine());
		MP = Integer.parseInt(reader.readLine());
		WAtt = Integer.parseInt(reader.readLine());
		WDef = Integer.parseInt(reader.readLine());
		MAtt = Integer.parseInt(reader.readLine());
		MDef = Integer.parseInt(reader.readLine());
		
		reader.close();
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
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#combineWith(net.plaidypus.
	 * deadreckoning.items.Item)
	 */
	@Override
	public Item combineWith(Item item) {
		return null;
	}

}
