package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class ActionSpawner extends Action{
	
	ArrayList<Action> actions;
	
	public ActionSpawner(Entity source, ArrayList<Action> actions) {
		super(source, source.getLocation());
		this.actions=actions;
	}
	
	protected boolean apply(int delta) {
		for(int i=0; i<actions.size(); i++){
			source.getParent().getGame().addAction(actions.get(i));
		}
		return true;
	}

	@Override
	public String getMessage() {return null;}

}
