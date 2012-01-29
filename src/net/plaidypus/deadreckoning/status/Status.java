package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class Status {
	
	public Image tileImage;
	public String description;
	int statusID;
	public InteractiveEntity source;
	
	static final int STATUS_ONFIRE = 1;
	
	public Status(InteractiveEntity source,Image tileImage, String description){
		this.description=description;
		this.tileImage=tileImage;
		this.source=source;
	}
	
	public abstract void applyToEntity(LivingEntity target);
	
	public abstract void update(LivingEntity target, int delta);
	
	public abstract ArrayList<Action> advanceTurnEffects(LivingEntity target);
	
	public abstract void removeFromEntity( LivingEntity target);
	
	public abstract void render(Graphics g, int x, int y);
	
	public abstract boolean isFinished();

	public abstract String getName();
}
