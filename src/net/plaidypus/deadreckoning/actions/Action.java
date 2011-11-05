package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Action {
	
	public LivingEntity source;
	public LivingEntity target;
	
	public boolean completed;
	
	public Action(LivingEntity source, LivingEntity target){
		this.source=source;
		this.target=target;
		completed=false;
	}
	
	protected abstract void apply(int delta);
	
	public void applyAction(int delta){
		if(!completed){
			apply(delta);
		}
	}
	
	public abstract int calculateRange(LivingEntity source);
}
