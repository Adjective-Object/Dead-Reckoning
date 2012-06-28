package net.plaidypus.deadreckoning.actions;

import java.io.IOException;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.save.Save;

public class SaveGameAction extends Action{

	public SaveGameAction(Entity source, Tile target) {
		super(source, target);
		this.takesTurn = false;
	}

	@Override
	protected boolean isNoticed() {
		return false;
	}

	@Override
	protected boolean apply(int delta) {
		try {
			Save.updateSave(this.source.getParent().getGame().saveLocation, (Player)this.source, this.source.getParent());
			Action.sendMessage("Saving Game...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
