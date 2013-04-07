package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.utilities.Utilities;

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
	
	public static final int
		SLOT_HEAD=0,
		SLOT_BODY=1,
		SLOT_HAND_LEFT=2,
		SLOT_HAND_RIGHT=3,
		SLOT_PANTS=4,
		SLOT_BOOTS=5,
		SLOT_RING=6,
		SLOT_AMULET=7;
	;

	/** The class compatability. */
	int[] classCompatability;

	/**
	 * Instantiates a new equip, loaded from the itemID.
	 * 
	 * @param itemID
	 *            the item id
	 */
	public Equip(String parentMod, Integer itemID) throws SlickException {
		super(parentMod, itemID, Item.ITEM_EQUIP);
	}

	// type-modpack-itemnumber
	public static Item loadFromString(String[] s) throws NumberFormatException,
			SlickException {
		Utilities.logArray(s);
		return new Equip( s[1], Integer.parseInt(s[2]) );
	}

	/**
	 * /* (non-Javadoc)
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
		
		equipSlot = Integer.parseInt(reader.readLine());
		STR = Integer.parseInt(reader.readLine());
		INT = Integer.parseInt(reader.readLine());
		DEX = Integer.parseInt(reader.readLine());
		LUK = Integer.parseInt(reader.readLine());
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

	public int getSlot() {
		return this.equipSlot;
	}

	@Override
	public String getMouseoverText() {
		String toRet = super.getMouseoverText() + "\n";
		int[] ppp = new int[] { this.HP, this.MP, this.STR, this.INT, this.DEX,
				this.LUK, this.WAtt, this.WDef, this.MAtt, this.MDef };
		String[] names = new String[] { "HP", "MP", "STR", "INT", "DEX", "LUK",
				"Weapon Attack", "Armor", "Magic Attack", "Magic Defense" };
		for (int i = 0; i < ppp.length; i++) {
			if (ppp[i] != 0) {
				toRet += names[i] + " +" + ppp[i] + "\n";
			}
		}
		return toRet;
	}

	@Override
	public Item getSingleCopy() throws SlickException {
		return this;
	}

}
