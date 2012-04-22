package net.plaidypus.deadreckoning.hudelements.menuItems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.hudelements.HudElement;

public class FairyLights extends HudElement{

	int width, height, numParticles;
	SpriteSheet particleSheet;
	private float[][] particles;
	
	public FairyLights(int x, int y, int bindMethod, int width, int height, int numParticles, SpriteSheet particles){
		super(x, y, bindMethod, false);
		this.width = width;
		this.height = height;
		this.numParticles = numParticles;
		
		this.particleSheet = particles;
		this.particles = new float[numParticles][4];//PARTICLE_ID,X,Y,xMomentum,yMomentum,OPACITY,ROTATION
		
		for(int i=0; i<numParticles; i++){
			this.particles[i] = new float[] {Utilities.randInt(0, particles.getVerticalCount()*particles.getHorizontalCount()),
					Utilities.randFloat()*this.width,Utilities.randFloat()*this.height,
					0F,0F,255F,Utilities.randFloat()*360F};
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for(int i=0; i<particles.length; i++){
			particles[i][3]+=(Utilities.randFloat()-Utilities.randFloat())*delta/1000;
			particles[i][4]+=(Utilities.randFloat()-Utilities.randFloat())*delta/1000;
			
			particles[i][1]+=particles[i][3]*5*delta/1000;
			particles[i][2]+=particles[i][4]*5*delta/1000;
			
			if (particles[i][1]<0 || particles[i][1]>width || particles[i][2]<0 || particles[i][2]>height){
				particles[i][3]=0;
				particles[i][4]=0;
			}
			
			particles[i][1] = Utilities.limitTo(particles[i][1], 0, width);
			particles[i][2] = Utilities.limitTo(particles[i][2], 0, height);
			
			particles[i][6]= (particles[i][2]+Utilities.randFloat()-Utilities.randFloat()*delta/1000)%360;
			
			//particles[i][5]+=Utilities.randFloat()*5*delta/1000-Utilities.randFloat()*5*delta/1000;

		}
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}

	@Override
	public int getWidth() {return width;}

	@Override
	public int getHeight() {return height;}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for(int i=0; i<particles.length; i++){
			this.particleSheet.setAlpha(particles[i][5]);
			Image img = this.particleSheet.getSprite(
					(int)particles[i][0]%this.particleSheet.getHorizontalCount(),
					(int)particles[i][0]/this.particleSheet.getVerticalCount()) .getFlippedCopy(false, false);
			img.rotate(particles[i][6]);
			g.drawImage(img,
					this.getX()+particles[i][1],this.getY()+particles[i][2]);
		}
	}

}
