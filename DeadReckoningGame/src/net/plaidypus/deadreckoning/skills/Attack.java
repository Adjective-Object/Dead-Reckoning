package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Attack.
 */
public class Attack extends Skill {

	/**
	 * Instantiates a new attack.
	 */
	public Attack() {
		super();
	}

	/**
	 * Instantiates a new attack.
	 *
	 * @param source the source
	 */
	public Attack(LivingEntity source) {
		super(source);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new AttackAction(source, target,
				source.getStatMaster().getSTR(), false);
	}

	/**
	 * says (in essence) that any occupied tile that is not the same tile as the
	 * source's tile can be passed.
	 *
	 * @param t the t
	 * @return true, if successful
	 */
	public boolean canTargetTile(Tile t) {
		if (!t.isOpen(Tile.LAYER_ACTIVE)
				&& !(t.getX() == source.getX() && t.getY() == source.getY())) {
			return t.getEntity(Tile.LAYER_ACTIVE).isInteractive();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus.deadreckoning.board.GameBoard)
	 */
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, source.getAttackRange());
	}

}
