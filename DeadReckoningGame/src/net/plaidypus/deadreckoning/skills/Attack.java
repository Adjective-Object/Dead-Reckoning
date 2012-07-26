package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Attack.
 */
public class Attack extends OffensiveSkill {
	
	/**
	 * Instantiates a new attack.
	 * 
	 * @param source
	 *            the source
	 */
	public Attack(int sourceID) {
		super(sourceID);
		this.level=1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new AttackAction(sourceID, (LivingEntity)target.getEntity(Tile.LAYER_ACTIVE),
				getSource().getStatMaster().getPhysicalDamageFrom(), false);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus
	 * .deadreckoning.board.GameBoard)
	 */
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, getSource().getAttackRange());
	}

	@Override
	public void init() throws SlickException {
	}

}
