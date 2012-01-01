package net.plaidypus.deadreckoning.status;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class OnFire extends AnimatedStatus{
	
	public OnFire(Image image, String details) throws SlickException{
		super(image,details,new Animation(new SpriteSheet("res/fireAnimation.png",DeadReckoningGame.tileSize,DeadReckoningGame.tileSize), 12));
	}
	
	@Override
	public void applyToEntity(LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEntityEffects(LivingEntity target, int delta) {
		
	}

	@Override
	public void removeFromEntity(LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

}
