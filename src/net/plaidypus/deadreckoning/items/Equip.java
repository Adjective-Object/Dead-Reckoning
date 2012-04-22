package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Equip extends Item {

	int STR, WIS, DEX, HP, MP, VIS;

	int equipSlot;
	int[] classCompatability;

	public Equip(int itemID) {
		super(itemID, Item.ITEM_EQUIP);
	}

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

	@Override
	public boolean stacksWith(Item item) {
		return false;
	}

	@Override
	public Item combineWith(Item item) {
		return null;
	}

}
