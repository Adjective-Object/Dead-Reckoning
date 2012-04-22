package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class AnimatedEntity extends Entity {

	Animation sprite;

	public AnimatedEntity(Tile location, int layer, String imageRef)
			throws SlickException {
		super(location, layer);
		int[] frames = new int[] { 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 0, 1, 1, 1, 2,
				1, 3, 1, 4, 1 };
		int[] durations = new int[] { 120, 120, 120, 120, 120, 120, 120, 120,
				120, 120 };

		sprite = new Animation(new SpriteSheet(new Image(imageRef),
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize, 0),
				frames, durations);
		sprite.setAutoUpdate(true);
	}

	public Image render() {
		return sprite.getCurrentFrame();
	}

	public void update(GameContainer gc, int delta) {
		sprite.update(delta);
	}

}
