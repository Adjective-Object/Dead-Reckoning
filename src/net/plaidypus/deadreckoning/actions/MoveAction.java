package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class MoveAction extends Action{
	
	public MoveAction(Tile source, Tile destination){
		super(source,destination);
	}
	
	public void apply(int delta) {
		Entity e = source.getEntity();
		
		float xMegaLocation = e.relativeX+(target.getX()*DeadReckoningGame.tileSize);
		float yMegaLocation = e.relativeY+(target.getY()*DeadReckoningGame.tileSize);
		
		if(target.getX()>source.getX()){
			e.setFacing(true);
		}
		else if(target.getX()<source.getX()){
			e.setFacing(false);
		}
			
		
		if( Math.abs(xMegaLocation - target.getX()*DeadReckoningGame.tileSize) <1 && Math.abs(yMegaLocation - target.getY()*DeadReckoningGame.tileSize) <1){
			completed=true;
			e.relativeX=0;
			e.relativeY=0;
			source.removeEntity();
			target.placeEntity(e);
			//e.setCurrentAnimation(LivingEntity.ANIMATION_STAND);
		}
		else{
			e.relativeX=(float) ((target.getX()*DeadReckoningGame.tileSize + xMegaLocation)/2 - source.getX()*DeadReckoningGame.tileSize);
			e.relativeY=(float) ((target.getY()*DeadReckoningGame.tileSize + yMegaLocation)/2 - source.getY()*DeadReckoningGame.tileSize);
			//e.setCurrentAnimation(LivingEntity.ANIMATION_WALK);
		}
		
	}
	
	public int calculateRange(LivingEntity source){
		return source.getMovementSpeed();
	}
	
}
