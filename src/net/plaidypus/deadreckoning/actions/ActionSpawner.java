package net.plaidypus.deadreckoning.actions;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Tile;

public class ActionSpawner extends Action{
	
	ArrayList<Action> actions;
	
	public ActionSpawner(Tile source, ArrayList<Action> actions) {
		super(source, source);
		this.actions=actions;
	}
	
	protected boolean apply(int delta) {
		for(int i=0; i<actions.size(); i++){
			source.getParent().getGame().addAction(actions.get(i));
		}
		return true;
	}

}
