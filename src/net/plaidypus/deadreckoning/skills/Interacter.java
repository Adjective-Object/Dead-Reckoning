package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.Interact;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class Interacter extends Skill {

	public Interacter(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new Interact(this.source, target);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return t.isOpen();
	}

	@Override
	public void highlightRange(GameBoard board) {
		board.highlightSquare(source.getX() - 1, source.getY());
		board.highlightSquare(source.getX() + 1, source.getY());
		board.highlightSquare(source.getX(), source.getY() - 1);
		board.highlightSquare(source.getX(), source.getY() + 1);
	}

}
