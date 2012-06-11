package net.plaidypus.deadreckoning.items;

import java.io.IOException;
import java.io.InputStream;

import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class UseItem.
 */
public class UseItem extends Item {

	/**
	 * Instantiates a new use item.
	 * 
	 * @param itemID
	 *            the item id
	 */
	public UseItem(String parentMod, int itemID) {
		super(parentMod, itemID, Item.ITEM_USE);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#parseItem(java.lang.String)
	 */
	@Override
	protected void parseItem(InputStream i) throws IOException, SlickException {
		// TODO Auto-generated method stub

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
		if (item.classification == ITEM_USE) {
			return item.itemID == this.itemID;
		}
		return false;
	}

	/**
	 * Gets the skill.
	 * 
	 * @return the skill
	 */
	public Skill getSkill() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.items.Item#combineWith(net.plaidypus.
	 * deadreckoning.items.Item)
	 */
	@Override
	public Item combineWith(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

}
