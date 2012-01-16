package net.plaidypus.deadreckoning.grideffects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;

public class FadeoutEffect extends GridEffect{
	
	float alpha;
	Image image;
	
	public FadeoutEffect(Tile location, Image image) {
		super(location);
		this.image=image;
		alpha=255;
	}

	@Override
	public void update(int delta) {
		alpha-=(delta/4);
		if(alpha<=0){
			this.setComplete(true);
		}
	}

	@Override
	public void render(Graphics g, float xOff, float yOff) {
		image.setAlpha(alpha/255);
		g.drawImage(image,location.getX()*DeadReckoningGame.tileSize + xOff, location.getY()*DeadReckoningGame.tileSize + yOff);
		image.setAlpha(1);
	}

}
