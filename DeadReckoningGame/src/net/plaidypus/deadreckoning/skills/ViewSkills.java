package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeStateAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewSkills.
 */
public class ViewSkills extends Skill {

	/**
	 * Instantiates a new view skills.
	 * 
	 * @param source
	 *            the source
	 */
	public ViewSkills(LivingEntity source) {
		super(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		return new ChangeStateAction(source, target,
				DeadReckoningGame.SKILLSTATE, new Object[] {
						GameplayElement.getImage(), source });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public boolean canTargetTile(Tile t) {
		return true;
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#isInstant()
	 */
	public boolean isInstant() {
		return true;
	}

	@Override
	public void init() throws SlickException {
	}

}
