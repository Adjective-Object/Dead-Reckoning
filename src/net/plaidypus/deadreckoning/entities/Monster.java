package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.skills.Attack;
import net.plaidypus.deadreckoning.skills.Movement;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Monster extends LivingEntity {

	public Skill movement, attack;
	
	//Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	public Monster(){} 
	
	/**
	 * a testing monster class
	 */
	public Monster(Tile targetTile, int layer, String entityFile, int allign) {
		super (targetTile, layer, entityFile, allign);
		movement = new Movement(this);
		attack = new Attack(this);
	}

	/**
	 * if something is blocking its path, it will turn left
	 */
	public Action chooseAction(GameContainer gc, int delta) {
		
		for(int i=-1; i<2; i++){
			for(int q=-1; q<2; q++){
				for(int k=0; k<Tile.numLayers; k++){
					if(!this.getLocation().getRelativeTo(i, q).isOpen(k)){
						Entity n =this.getLocation().getRelativeTo(i, q).getEntity(k);
						if(n.getAllignment()!=this.getAllignment() && n.getAllignment()!=Entity.ALLIGN_NEUTRAL && n.isInteractive() && Utilities.randFloat()<=0.8){
							return attack.makeAction(this.getLocation().getRelativeTo(i, q));
						}
					}
				}
			}
		}
		
		Tile dest = this.getParent().getTileAt(
				Utilities.limitTo(this.getX()+Utilities.randInt(-1, 2),0,getParent().getWidth()),
				Utilities.limitTo(this.getY()+Utilities.randInt(-1, 2),0,getParent().getHeight()));
		if(dest.isOpen(this.getLayer()) && !dest.equals(this.getLocation())){
			return movement.makeAction(dest);
		}
		
		return new WaitAction(this);
	}

	public void updateBoardEffects(GameContainer gc, int delta) {}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void init() throws SlickException {}
	
}
