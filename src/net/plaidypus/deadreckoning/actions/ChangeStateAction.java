package net.plaidypus.deadreckoning.actions;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

public class ChangeStateAction extends Action{
	
	int state;
	Object[] args;
	
	public ChangeStateAction(Entity source, Tile target, int state, Object[] args) {
		super(source, source.getLocation());
		takesTurn= false;
		this.state=state;
		this.args = args;
	}
	
	public ChangeStateAction(Entity source, Tile target, int state){
		this(source,target,state, new Object[] {GameplayElement.getImage(),null,(InteractiveEntity)(source)});
	}

	@Override
	protected boolean apply(int delta) {
		System.out.println("Moving to State:"+ state);
		DeadReckoningGame.instance.getHudState(state).makeFrom(args);
		DeadReckoningGame.instance.enterState(state);
		return true;
	}

	@Override
	protected boolean isNoticed() {return false;}

}
