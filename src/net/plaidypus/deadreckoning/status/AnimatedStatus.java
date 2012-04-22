package net.plaidypus.deadreckoning.status;

import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class AnimatedStatus extends Status {

	Animation animation;

	public AnimatedStatus(InteractiveEntity source, Image tile,
			String description, String identifier, Animation effectAnimation) {
		super(source, tile, description, identifier);
		this.animation = effectAnimation;
	}

	@Override
	public void update(LivingEntity target, int delta) {
		animation.update(delta);
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.drawImage(animation.getCurrentFrame(), x, y);
	}

}
