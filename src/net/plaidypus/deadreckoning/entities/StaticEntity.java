package net.plaidypus.deadreckoning.entities;


import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class StaticEntity extends Entity{
	
	Image sprite;
	
	public StaticEntity(String imageRef) throws SlickException
	{
		sprite = new Image(imageRef);
	}
	
	public Image render()
	{
		return sprite;
	}
	
}
