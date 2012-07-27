package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

public class FailedMoveEntityEffect extends MoveEntityEffect{

	int turnback=1;
	
	public FailedMoveEntityEffect(Tile location, int entityID,
			Tile targetLocation) {
		super(location, entityID, targetLocation);
	}
	
	@Override
	public void update(int delta) {
		this.xoff-=turnback*a*(speed/delta)/hypotenuse;
		this.yoff-=turnback*b*(speed/delta)/hypotenuse;
		distravelled+=turnback*(speed/delta)/DeadReckoningGame.tileSize;
		if(distravelled>=hypotenuse/2){
			turnback=-1;
		}
		if( !destination.canBeSeen() || (turnback==-1 && distravelled<=0) ){
			this.setComplete(true);
			GameBoard.getEntity(entityID).setVisible(true);
		}
	}

}
