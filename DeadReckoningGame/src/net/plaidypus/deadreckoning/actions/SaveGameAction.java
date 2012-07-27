package net.plaidypus.deadreckoning.actions;

import java.io.IOException;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.save.Save;

import org.newdawn.slick.util.Log;

public class SaveGameAction extends Action{

	public SaveGameAction(int sourceID) {
		super(sourceID);
		this.takesTurn = false;
	}

	@Override
	protected boolean isNoticed() {
		return false;
	}

	@Override
	protected boolean apply(int delta) {
		try {
			Save.updateSave(DeadReckoningGame.instance.getGameElement().saveLocation,
					(Player)GameBoard.getEntity(this.sourceID),
					GameBoard.getEntity(this.sourceID).getParent());
			Log.info("Saving Game...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
