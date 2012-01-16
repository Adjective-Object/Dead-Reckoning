package net.plaidypus.deadreckoning.status;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Status {
	
	public Image tileImage;
	public String description;
	int statusID;
	
	static final int STATUS_ONFIRE = 1;
	
	public Status(Image tileImage, String description){
		this.description=description;
		this.tileImage=tileImage;
	}
	
	public abstract void applyToEntity(LivingEntity target);
	
	public abstract void update(LivingEntity target, int delta);
	
	public abstract void advanceTurnEffects(LivingEntity target);
	
	public abstract void removeFromEntity( LivingEntity target);
	
	public abstract void render(Graphics g, int x, int y);
	
	public abstract boolean isFinished();
}
