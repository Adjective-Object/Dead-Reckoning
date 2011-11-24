package net.plaidypus.deadreckoning.entities;


import java.util.ArrayList;

import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Items.Equip;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.skills.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/*

 Griffin Brodman & Jeffrey Tao & don't forget Max / PJ
 Created: 11/4/2011

 */

public class Player extends LivingEntity {
	Input input;

	public static int[] keyBinds;
	public static Skill[] skills;

	public int currentSkill;
	
	ArrayList<Equip> epuips;
	
	/**
	 * subclass of living entity that is controlled by user input
	 * 
	 * @param i
	 *            the input object returned by GameContainer.getInput()
	 * @throws SlickException
	 */
	public Player(Tile targetTile, Input i) throws SlickException {
		super("res/player.entity",targetTile);
		this.input = i;

		keyBinds = new int[] { Input.KEY_M, Input.KEY_A, Input.KEY_W, Input.KEY_P , Input.KEY_T, Input.KEY_C, Input.KEY_L};
		skills = new Skill[] { new Movement(this), new Attack(this),
				new Wait(this), new PlaceWall(this), new PlaceTorch(this), new PlaceChest(this), new Loot(this)};
	}
	
	public boolean canSee(Tile t){
		return this.getParent().isLineOfSight(getLocation(), t) && t.lightLevel>1;
	}
	
	public void update(GameContainer gc, int delta){
		super.update(gc,delta);
		this.getParent().revealInRadius(this.getLocation(), this.VIS);
	}
	
	/**
	 * action choosing for the player (returns null often because the player takes time to decide/input)
	 */
	public Action chooseAction(GameContainer gc, int delta) {
		for (int i = 0; i < keyBinds.length; i++) {
			if (input.isKeyPressed(keyBinds[i])) {
				this.currentSkill = i;

				if (skills[i].isInstant()){
					return skills[currentSkill].makeAction(this.getLocation());
				}
				
				else{
					skills[currentSkill].highlightRange(this.getParent());
					if (this.getParent().getPrimairyHighlight() == null) {
						this.getParent().setPrimairyHighlight(this.getLocation());
					}
					else if (this.getParent().getPrimairyHighlight().highlighted==Tile.HIGHLIGHT_CONFIRM){//TODO this always returns it can do it
						return skills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
					}
				}
			}
		}
		
		if (( input.isKeyPressed(Input.KEY_ENTER) || input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON ) )
				&& this.getParent().getPrimairyHighlight() != null
				&& this.getParent().getPrimairyHighlight().getHighlighted() == Tile.HIGHLIGHT_CONFIRM) {
			return skills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		return null;
	}


}