package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class MoveAction extends Action{
	
	int destinationX,destinationY;
	
	public MoveAction(LivingEntity source, int destinationX, int destinationY){
		super(source,source);
		this.destinationX=destinationX;
		this.destinationY=destinationY;
	}
	
	public void apply(int delta) {
		float xMegaLocation = target.relativeX+(target.getLocation().getX()*DeadReckoningGame.tileSize);
		float yMegaLocation = target.relativeY+(target.getLocation().getY()*DeadReckoningGame.tileSize);
		
		if(destinationX>source.getLocation().getX()){
			source.facing = true;
		}
		else if(destinationX<source.getLocation().getX()){
			source.facing = false;
		}
			
		
		if( Math.abs(xMegaLocation - destinationX*DeadReckoningGame.tileSize) <1 && Math.abs(yMegaLocation - destinationY*DeadReckoningGame.tileSize) <1){
			completed=true;
			target.relativeX=0;
			target.relativeY=0;
			target.getLocation().removeEntity();
			target.getLocation().getParent().getTileAt(destinationX, destinationY).placeEntity(target);
			target.setCurrentAnimation(LivingEntity.ANIMATION_STAND);
		}
		else{
			target.relativeX=(float) ((destinationX*DeadReckoningGame.tileSize + xMegaLocation)/2 - target.getLocation().getX()*DeadReckoningGame.tileSize);
			target.relativeY=(float) ((destinationY*DeadReckoningGame.tileSize + yMegaLocation)/2 - target.getLocation().getY()*DeadReckoningGame.tileSize);
			target.setCurrentAnimation(LivingEntity.ANIMATION_WALK);
		}
		
	}
	
	public int calculateRange(LivingEntity source){
		return source.getMovementSpeed();
	}
	
}
