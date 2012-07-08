package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.items.Item;
import net.plaidypus.deadreckoning.professions.StatMaster;
import net.plaidypus.deadreckoning.skills.Attack;
import net.plaidypus.deadreckoning.skills.Movement;
import net.plaidypus.deadreckoning.skills.Skill;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Monster.
 */
public class Monster extends LivingEntity {

	/** The attack. */
	public Skill movement, attack;

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new monster.
	 */
	public Monster() {
	}

	/**
	 * a testing monster class.
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 * @param entityFile
	 *            the entity file
	 * @param stats
	 *            the stats
	 * @param allign
	 *            the allign
	 */
	public Monster(String parentMod, String entityFile,
			StatMaster stats, int allign) {
		super(parentMod, entityFile, stats, allign);
		movement = new Movement(this.getID());
		attack = new Attack(this.getID());
		this.skills.add(movement);
		this.skills.add(attack);
	}
	
	/**
	 * if something is blocking its path, it will turn left.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @return the action
	 */
	public Action decideNextAction(GameContainer gc, int delta) {
		for (int i = -1; i < 2; i++) {
			for (int q = -1; q < 2; q++) {
				for (int k = 0; k < Tile.numLayers; k++) {
					if (!this.getLocation().getRelativeTo(i, q).isOpen(k)) {
						Entity n = this.getLocation().getRelativeTo(i, q)
								.getEntity(k);
						if (n.getAllignment() != this.getAllignment()
								&& n.getAllignment() != Entity.ALLIGN_NEUTRAL
								&& n.makesActions()
								&& Utilities.randFloat() <= 0.8) {
							return attack.makeAction(this.getLocation()
									.getRelativeTo(i, q));
						}
					}
				}
			}
		}

		Tile dest = this.getParent().getTileAt(
				Utilities.limitTo(this.getX() + Utilities.randInt(-1, 2), 0,
						getParent().getWidth()),
				Utilities.limitTo(this.getY() + Utilities.randInt(-1, 2), 0,
						getParent().getHeight()));
		if (dest.isEmpty(this.getLayer()) && !dest.equals(this.getLocation())) {
			return movement.makeAction(dest);
		}

		return new WaitAction(this.getID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects
	 * (org.newdawn.slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus
	 * .deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	// TODO loading from jarfile
	public Entity makeFromString(GameBoard g, String[] toload) {
		Monster toRet = new Monster(toload[10], toload[11],// parentMod , entityfile
				super.loadStatMaster(toload[8].split(",")),//statmaster
				Integer.parseInt(toload[9]));// Alignment
		toRet.placeAt(
				g.getTileAt(Integer.parseInt(toload[1]), Integer.parseInt(toload[2])),
				Integer.parseInt(toload[3]));
		toRet.inventory.addAll(loadItems(toload[4].split(",")));
		toRet.loadStatuses(toRet, toload[5].split(","));
		toRet.HP=Integer.parseInt(toload[6]);
		toRet.MP=Integer.parseInt(toload[7]);
		return toRet;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Monster[" + this.getX() + "," + this.getY() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.
	 * deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		// TODO I don't know ehat to put here...
		return null;
	}
	
	public ArrayList<Item> getDropItems(){
		ArrayList<Item> toRet = new ArrayList<Item>(0);
		if(info.containsKey("DROPITEMS")){
			String[] itemDefs = this.info.get("DROPITEMS").replaceAll(" ","").split(",");
			for(int i=0; i<itemDefs.length; i++){
				String[] itemDef = itemDefs[i].split("-");
				float chance = Float.parseFloat(itemDef[3])/100F;
				if(Utilities.randFloat()>chance){
					toRet.add(Item.loadFromString(itemDef));
				}
			}
		}
		else{
			System.err.println("NO DROPITEMS: "+info);
		}
		return toRet;
	}

}
