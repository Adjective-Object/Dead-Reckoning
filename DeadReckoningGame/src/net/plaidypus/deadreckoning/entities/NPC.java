package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ChangeStateAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.state.NPCSpeechState;
import net.plaidypus.deadreckoning.statmaster.StatMaster;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public abstract class NPC extends LivingEntity{
	
	public NPC(){
		super();
		this.makesActions=false;
	}
	
	public NPC(String parentMod, String entityFile,
			StatMaster statMaster, int allignment) throws SlickException {
		super( parentMod,  entityFile, statMaster,  allignment);
	}
	
	@Override
	protected Action decideNextAction(GameContainer gc, int delta) throws SlickException{
		return null;
	}

	public abstract void resetSpeechTree();
	public abstract void advanceChat(NPCSpeechState parent);
	
	@Override
	public Action onInteract(Entity e) {
		this.resetSpeechTree();
		NPCSpeechState n = (NPCSpeechState)(DeadReckoningGame.instance.getState(DeadReckoningGame.NPCSPEECHSTATE));
		n.makeFrom(this);
		return new ChangeStateAction(this.getID(), DeadReckoningGame.NPCSPEECHSTATE);
	}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes)
			throws SlickException {
		return null;
	}

	@Override
	public String saveToString() {
		return super.getGenericSave();
	}

	@Override
	public abstract void init() throws SlickException ;

}
