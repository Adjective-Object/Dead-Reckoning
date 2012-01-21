package net.plaidypus.deadreckoning.skills;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.*;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.items.Item;

public class PlaceChest extends Skill{

	public PlaceChest(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		ArrayList<Item> inv = new ArrayList<Item>(0);
		return new ChangeMapAction(source.getLocation(),target,new Chest(target, inv));
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen();
	}

	@Override
	public void highlightRange(GameBoard board) {
		for(int y=0; y<board.getWidth(); y++){
			for(int x=0; x<board.getWidth(); x++){
				board.highlightSquare(x, y);
			}
		}
	}
	
}
