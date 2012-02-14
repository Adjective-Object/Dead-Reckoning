package net.plaidypus.deadreckoning.entities;


import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.skills.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/*

 Lezbihonest here, Max is the only one who's been coding this for a while.
 Created: 11/4/2011

 */

public class Player extends LivingEntity {

	Input input;

	public static int[] keyBinds;
	public static Skill[] skills;

	public int currentSkill;
	public int level;
	public int EXP;
	
	public Profession profession;
	
	ArrayList<Equip> epuips;
	
	/**
	 * subclass of living entity that is controlled by user input
	 * 
	 * @param i
	 *            the input object returned by GameContainer.getInput()
	 * @throws SlickException
	 */
	public Player(Tile targetTile, int layer, Profession p, Input i) throws SlickException {
		super(targetTile,layer,p.getEntityFile(),Entity.ALLIGN_FRIENDLY);
		this.input = i;

	keyBinds = new int[] { Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_Q, Input.KEY_T, Input.KEY_P , Input.KEY_T, Input.KEY_C, Input.KEY_L, Input.KEY_F, Input.KEY_I};
		skills = new Skill[] { new PreBakedMove(this,-1,0),
				new PreBakedMove(this,1,0),new PreBakedMove(this,0,-1),
				new PreBakedMove(this,0,1),new Attack(this),
				new Wait(this), new PlaceWall(this), new PlaceTorch(this),
				new PlaceChest(this), new Loot(this), new Fireball(this), new CheckInventory(this)};
		profession = p;
	}
	
	public boolean canSee(Tile t){
		return this.getLocation().isVisible();
	}
	
	public void update(GameContainer gc, int delta){
		super.update(gc,delta);
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
		super.updateBoardEffects(gc, delta);
		this.getParent().lightInRadius(getLocation(), 2);//TODO visibility light radius thing?
		if(this.EXP>=this.getEXPforLevel()){
			this.EXP-=this.getEXPforLevel();
			this.level++;
			Animation levelUp=new Animation(Fireball.fireball,100);//TODO actual level up animation
			levelUp.setLooping(false);
			this.getParent().addEffectOver(this.getLocation(),new AnimationEffect(this.getLocation(),levelUp));//TODO Level up Animation
		}
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
					else if (canTarget()){
						return skills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
					}
				}
			}
		}
		
		if (( input.isKeyPressed(Input.KEY_ENTER) || input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) && this.getParent().getPrimairyHighlight()!=null
				&& canTarget()) {
			return skills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		return null;
	}
	
	private boolean canTarget(){
		return this.getParent().getPrimairyHighlight().highlighted==Tile.HIGHLIGHT_CONFIRM && this.canSee(this.getParent().getPrimairyHighlight());
	}
	
	public int getEXPforLevel() {
		return 100;
	}
	
	public Profession getProfession(){
		return this.profession;
	}

	public void addExp(int value) {
		this.EXP+=value;
	}

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