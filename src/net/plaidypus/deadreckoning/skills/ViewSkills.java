package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeStateAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;

public class ViewSkills extends Skill {

	public ViewSkills(LivingEntity source) {
		super(source);
	}

	@Override
	public Action makeAction(Tile target) {
		return new ChangeStateAction(source, target,
				DeadReckoningGame.SKILLSTATE, new Object[] {
						GameplayElement.getImage(), source });
	}

	@Override
	public boolean canTargetTile(Tile t) {
		return true;
	}

	@Override
	public void highlightRange(GameBoard board) {
	}

	public boolean isInstant() {
		return true;
	}

}
