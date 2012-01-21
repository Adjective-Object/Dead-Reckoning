package net.plaidypus.deadreckoning.grideffects;

import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;

public class MoveEntityEffect extends GridEffect{

	Tile destination;
	float xoff, yoff;
	int moveSpeed;
	double hypotenuse, a, b, speed = 100, distravelled;
	
	public MoveEntityEffect(Tile location, Tile targetLocation) {
		super(location);
		this.destination = targetLocation;
		location.getEntity().setVisible(false);
		a = location.getX()-destination.getX();
		b = location.getY()-destination.getY();
		hypotenuse = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
		distravelled=0;
	}

	
	public void update(int delta) {
		
		Entity e = location.getEntity();
		
		if(destination.getX()>location.getX()){
			e.setFacing(true);
		}
		else if(destination.getX()<location.getX()){
			e.setFacing(false);
		}
			
		if( distravelled>=hypotenuse ){
			//TODO fix finish trigger conditions
			location.getEntity().setVisible(true);
			location.getParent().moveEntity(location,destination);
			this.setComplete(true);
		}
		else{
			this.xoff-=a*(speed/delta)/hypotenuse;
			this.yoff-=b*(speed/delta)/hypotenuse;
			distravelled+=(speed/delta)/DeadReckoningGame.tileSize;
		}
	}
	
	public void render(Graphics g, float xoff, float yoff) {
		if((location.lightLevel>=1 || destination.lightLevel>=1) && (location.isVisible() || location.isVisible())){
			location.getEntity().forceRender(g, xoff+location.getX()*DeadReckoningGame.tileSize+this.xoff,yoff+location.getY()*DeadReckoningGame.tileSize+this.yoff);
		}
	}

}
