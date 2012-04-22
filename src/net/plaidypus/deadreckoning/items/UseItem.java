package net.plaidypus.deadreckoning.items;

import java.io.IOException;

import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.SlickException;

public class UseItem extends Item {

	public UseItem(int itemID) {
		super(itemID, Item.ITEM_USE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseItem(String path) throws IOException, SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean stacksWith(Item item) {
		if (item.classification == ITEM_USE) {
			return item.itemID == this.itemID;
		}
		return false;
	}

	public Skill getSkill() {
		return null;
	}

	@Override
	public Item combineWith(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

}
