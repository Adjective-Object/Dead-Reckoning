package core.npc;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.SpawnEntityAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Monster;
import net.plaidypus.deadreckoning.entities.NPC;
import net.plaidypus.deadreckoning.state.NPCSpeechState;
import net.plaidypus.deadreckoning.state.substates.StoreState;
import net.plaidypus.deadreckoning.statmaster.StatMaster;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class ShopNPC extends NPC{
	
	int stepsInConverstation = 0;
	
	public ShopNPC() throws SlickException{
		super("core", "core/livingEntities/merchant.entity", new StatMaster(15, 15, 4, 4, 4, 4, 1), Entity.ALLIGN_NEUTRAL);  
	}
	
	@Override
	public void resetSpeechTree() {
		this.stepsInConverstation = 0;
	}
	
	@Override
	//loading save
	public Entity makeFromString(GameBoard g, String[] toload)
			throws SlickException {
		ShopNPC toRet = new ShopNPC();
		super.loadGenericSave(g, toload, toRet);
		return toRet;
	}
	
	@Override
	public void advanceChat(NPCSpeechState parent) {
		switch(this.stepsInConverstation){
			case 0:
				parent.NPCText.setText("Whattr ya Boyin?");
				break;
			case 1:
				StoreState s = (StoreState)(DeadReckoningGame.instance.getState(DeadReckoningGame.STORESTATE));
				s.makeFrom(this.inventory);
				DeadReckoningGame.instance.enterState(DeadReckoningGame.STORESTATE);
				break;
			case 2:
				parent.NPCText.setText("Good luck Sourvyvin'");
				break;
		}
		this.stepsInConverstation++;
		
	}
	public Action decideNextAction(GameContainer gc, int delta) throws SlickException {
		if(this.HP<this.statMaster.getMaxHP()){
			return new SpawnEntityAction(
					this.getID(), this.getLocation(), this.getLayer(), 
						new Monster(
							"core", "core/livingEntities/merchant.entity", this.statMaster,
							Entity.ALLIGN_HOSTILE
						)
					);
		} else{
			return new WaitAction(this.getID());
		}
	}
	@Override
	public void init() throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
