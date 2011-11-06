package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Action {
	
	public Tile source;
	public Tile target;
	
	public boolean completed=false;
	
	public Action(Tile source, Tile target){
		this.source=source;
		this.target=target;
		completed=false;
	}
	
	protected abstract boolean apply(int delta);
	
	public void applyAction(int delta){
		if(!completed){
			completed=apply(delta);
		}
	}
	
	public abstract int calculateRange(LivingEntity source);
}
