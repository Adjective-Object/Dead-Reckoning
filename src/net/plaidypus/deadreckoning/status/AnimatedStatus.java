package net.plaidypus.deadreckoning.status;

import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class AnimatedStatus extends Status{
	
	Animation animation;
	
	public AnimatedStatus(Image tile, String description, Animation effectAnimation){
		super(tile,description);
		this.animation=effectAnimation;
	}

	@Override
	public void update(LivingEntity target, int delta) {
		animation.update(delta);
	}
	@Override
	public void render(Graphics g, LivingEntity target, int xOff, int yOff) {
		g.drawImage(animation.getCurrentFrame(),target.getAbsoluteX()-xOff,target.getAbsoluteY()-yOff);
	}

}