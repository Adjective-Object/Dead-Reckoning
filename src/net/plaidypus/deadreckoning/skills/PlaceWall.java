package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeMapAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Wall;

public class PlaceWall extends Skill {

	public PlaceWall() {
		super();
	}

	public PlaceWall(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new ChangeMapAction(source, target, Tile.LAYER_ACTIVE, new Wall(
				target, Tile.LAYER_ACTIVE));
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return !t.isOpen(Tile.LAYER_ACTIVE);
	}

	@Override
	public void highlightRange(GameBoard board) {
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				board.highlightSquare(x, y);
			}
		}
	}

}
