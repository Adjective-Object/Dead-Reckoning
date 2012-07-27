package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public class AnimatedProjectileEffect extends GridEffect {

	Tile destination;
	int travelMillis, elapsedMillis;
	int distanceX, distanceY;
	Animation projectileAnimation;
	
	public AnimatedProjectileEffect(Animation animation, Tile location, Tile destination, int travelMillis) {
		super(location);
		this.destination=destination;
		this.travelMillis = travelMillis;
		this.projectileAnimation = animation;
		elapsedMillis = 0;
	}

	@Override
	public void update(int delta) {
		if(this.elapsedMillis>=travelMillis){
			this.setComplete(true);
		}
		this.elapsedMillis+=delta;
		if(elapsedMillis>travelMillis){
			elapsedMillis=travelMillis;
		}
		this.projectileAnimation.update(delta);
		distanceX=(int)(DeadReckoningGame.tileSize*(destination.getX()-location.getX())*((float)(elapsedMillis)/travelMillis));
		distanceY=(int)(DeadReckoningGame.tileSize*(destination.getY()-location.getY())*((float)(elapsedMillis)/travelMillis));
	}

	@Override
	public void render(Graphics g, float xoff, float yoff) {
		g.drawImage(this.projectileAnimation.getCurrentFrame(),
				location.getX()*DeadReckoningGame.tileSize +distanceX +xoff+DeadReckoningGame.tileSize/2-this.projectileAnimation.getWidth()/2,
				location.getY()*DeadReckoningGame.tileSize +distanceY +yoff+DeadReckoningGame.tileSize/2-this.projectileAnimation.getHeight()/2);
	}

}
