package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.grideffects.FailedMoveEntityEffect;
import net.plaidypus.deadreckoning.grideffects.MoveEntityEffect;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveAction.
 */
public class MoveAction extends Action {

	/** The animate. */
	boolean animate;

	/** The dest layer. */
	int destLayer;
	
	MoveEntityEffect displayedAnim = null;

	/**
	 * Instantiates a new move action.
	 * 
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @param destLayer
	 *            the dest layer
	 */
	public MoveAction(int sourceID, Tile destination, int destLayer) {
		super(sourceID, destination);
		animate = false;
		if (destination.isVisible() || GameBoard.getEntity(this.sourceID).isVisible()) {
			animate = true;
		}
		this.destLayer = destLayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#apply(int)
	 */
	@Override
	public boolean apply(int delta) {
		if(displayedAnim==null){
			Entity source = GameBoard.getEntity(this.sourceID);
			if (getTargetTile().getX() < source.getX()) {
				source.setFacing(false);
			} else if (getTargetTile().getX() > source.getX()) {
				source.setFacing(true);
			}
			
			if(getTargetTile().isOpen(destLayer)){
				displayedAnim = new MoveEntityEffect(this.getSourceTile(), this.sourceID, this.getTargetTile());
				source.getParent().moveEntity(source, getTargetTile(), destLayer);
			}
			else{
				displayedAnim = new FailedMoveEntityEffect(this.getSourceTile(), this.sourceID, this.getTargetTile());
			}
			
			getTargetTile().getParent().addEffectOver(displayedAnim);
		}
		return displayedAnim.isComplete();
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.actions.Action#isNoticed()
	 */
	@Override
	protected boolean isNoticed() {
		return true;
	}

}
