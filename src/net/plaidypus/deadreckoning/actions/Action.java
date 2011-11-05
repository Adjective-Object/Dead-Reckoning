package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Action {
	
	public LivingEntity source;
	public LivingEntity target;
	
	public Action(LivingEntity source, LivingEntity target){
		this.source=source;
		this.target=target;
	}
	
	public abstract void apply();
	
	public abstract int calculateRange(LivingEntity source);
}
