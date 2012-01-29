package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;


public class PreBakedMove extends Movement{

	int xoff, yoff;
	
	public PreBakedMove(LivingEntity l, int xoff, int yoff) {
		super(l);
		this.xoff=xoff;
		this.yoff=yoff;
	}
	
	public Action makeAction(Tile target) {
		if( source.getLocation().getRelativeTo(xoff,yoff).isOpen()){
			return new MoveAction(source.getLocation(), source.getLocation().getRelativeTo(xoff,yoff));
		}
		return new WaitAction(source.getLocation());
	}
	
	public boolean isInstant(){
		return true;
	}

}
