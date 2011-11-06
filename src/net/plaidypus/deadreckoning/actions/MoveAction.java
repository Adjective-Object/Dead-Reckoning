package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class MoveAction extends Action{
	
	public MoveAction(Tile source, Tile destination){
		super(source,destination);
	}
	
	public boolean apply(int delta) {
		
		Entity e = source.getEntity();
		
		
		float xAbsoluteLocation = e.relativeX+(source.getX()*DeadReckoningGame.tileSize);
		float yAbsoluteLocation = e.relativeY+(source.getY()*DeadReckoningGame.tileSize);
		
		if(target.getX()>source.getX()){
			e.setFacing(true);
		}
		else if(target.getX()<source.getX()){
			e.setFacing(false);
		}
			
		if( Math.abs(xAbsoluteLocation - target.getX()*DeadReckoningGame.tileSize) <1 && Math.abs(yAbsoluteLocation - target.getY()*DeadReckoningGame.tileSize) <1){
			e.relativeX=0;
			e.relativeY=0;
			source.removeEntity();
			target.placeEntity(e);
			return true;
		}
		else{
			e.relativeX=(float) ((target.getX()*DeadReckoningGame.tileSize + xAbsoluteLocation)/2 - source.getX()*DeadReckoningGame.tileSize);
			e.relativeY=(float) ((target.getY()*DeadReckoningGame.tileSize + yAbsoluteLocation)/2 - source.getY()*DeadReckoningGame.tileSize);
		}
		return false;
	}
	
	public int calculateRange(LivingEntity source){
		return source.getMovementSpeed();
	}
	
}
