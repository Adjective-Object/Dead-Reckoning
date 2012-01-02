package net.plaidypus.deadreckoning.status;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Status {
	
	public Image tileImage;
	public String description;
	
	public Status(Image tileImage, String description){
		this.description=description;
		this.tileImage=tileImage;
	}
	
	public abstract void applyToEntity(LivingEntity target);
	
	public abstract void update(LivingEntity target, int delta);
	
	public abstract void advanceTurnEffects(LivingEntity target);
	
	public abstract void removeFromEntity( LivingEntity target);
	
	public abstract void render(Graphics g, LivingEntity target, int xOff, int yOff);
	
	public abstract boolean isFinished();
}
