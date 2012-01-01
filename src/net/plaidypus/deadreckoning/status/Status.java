package net.plaidypus.deadreckoning.status;

import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Status {
	
	public abstract void applyToEntity(LivingEntity target);
	
	public abstract void update(LivingEntity target, int delta);
	
	public abstract void updateEntityEffects(LivingEntity target, int delta);
	
	public abstract void removeFromEntity( LivingEntity target);
	
	public abstract void render(Graphics g, int xOff, int yOff);
	
}
