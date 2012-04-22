package net.plaidypus.deadreckoning.entities;


import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.skills.*;
import net.plaidypus.deadreckoning.status.OnFire;

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
	public static Skill[] inputSkills;

	public int currentSkill;
	public int EXP;
	
	public Profession profession;
	
	ArrayList<Equip> epuips;
	
	public Player(){}
	
	/**
	 * subclass of living entity that is controlled by user input
	 * 
	 * @param i
	 *            the input object returned by GameContainer.getInput()
	 * @throws SlickException
	 */
	public Player(Tile targetTile, int layer, Profession p, Input i) throws SlickException {
		super(targetTile,layer,p.getEntityFile(),p,Entity.ALLIGN_FRIENDLY);
		this.input = i;

		this.profession=p;
		this.profession.parentTo(this);
		
		keyBinds = new int[] { Input.KEY_A, Input.KEY_D, Input.KEY_W, Input.KEY_S, Input.KEY_Q,
				Input.KEY_F1,Input.KEY_F2,Input.KEY_F3,Input.KEY_F4,Input.KEY_F5,Input.KEY_F6,Input.KEY_F7,Input.KEY_F8,Input.KEY_F9,Input.KEY_F10,Input.KEY_F11,Input.KEY_F12,
				Input.KEY_T, Input.KEY_P , Input.KEY_T, Input.KEY_C, Input.KEY_L, Input.KEY_I, Input.KEY_M, Input.KEY_K, Input.KEY_E};
		inputSkills = new Skill[] { new PreBakedMove(this,-1,0),
					new PreBakedMove(this,1,0),new PreBakedMove(this,0,-1),
					new PreBakedMove(this,0,1),new Attack(this),
					
					p.getTrees()[0].getSkills()[0],p.getTrees()[0].getSkills()[1],p.getTrees()[0].getSkills()[2],p.getTrees()[0].getSkills()[3],
					p.getTrees()[1].getSkills()[0],p.getTrees()[1].getSkills()[1],p.getTrees()[1].getSkills()[2],p.getTrees()[1].getSkills()[3],
					p.getTrees()[2].getSkills()[0],p.getTrees()[2].getSkills()[1],p.getTrees()[2].getSkills()[2],p.getTrees()[2].getSkills()[3],
				
					new Wait(this), new PlaceWall(this),
					new PlaceChest(this), new Loot(this), new Fireball(this), new CheckInventory(this), new ViewMap(this), new ViewSkills(this), new Interacter(this)};
		
		this.skills.addAll(p.getSkillList());
		
	}
	public boolean canSee(Tile t){
		return this.getLocation().isVisible();
	}
	
	public void update(GameContainer gc, int delta){
		super.update(gc,delta);
		if(gc.getInput().isKeyDown(Input.KEY_SCROLL)){
			this.EXP+=delta;
		}
	}
	
	public void updateBoardEffects(GameContainer gc, int delta){
		super.updateBoardEffects(gc, delta);
		this.getParent().lightInRadius(getLocation(), 2);//TODO visibility light radius thing?
		if(this.EXP>=this.getEXPforLevel()){
			this.EXP-=this.getEXPforLevel();
			this.profession.levelUp();
			this.MP=this.profession.getMaxMP();
			this.HP=this.profession.getMaxHP();
			Animation levelUp=new Animation(Fireball.fireball,100);//TODO actual level up animation
			levelUp.setLooping(false);
			this.getParent().addEffectOver(this.getLocation(),new AnimationEffect(this.getLocation(),levelUp));//TODO Level up Animation
		}
	}
	
	/**
	 * action choosing for the player (returns null often because the player takes time to decide/input)
	 */
	public Action decideNextAction(GameContainer gc, int delta) {
		for (int i = 0; i < keyBinds.length; i++) {
			if (input.isKeyPressed(keyBinds[i])) {
				this.currentSkill = i;

				if (inputSkills[i].isInstant() && inputSkills[i].canBeCast()){
					return inputSkills[currentSkill].makeAction(this.getLocation());
				}
				
				else if (inputSkills[i].canBeCast()){
					inputSkills[currentSkill].highlightRange(this.getParent());
					if (this.getParent().getPrimairyHighlight() == null) {
						this.getParent().setPrimairyHighlight(this.getLocation());
					}
					else if (canTarget()){
						return inputSkills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
					}
				}
			}
		}
		
		if (( input.isKeyPressed(Input.KEY_ENTER) || input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) && this.getParent().getPrimairyHighlight()!=null
				&& canTarget() && inputSkills[currentSkill].canBeCast()) {
			return inputSkills[currentSkill].makeAction(this.getParent().getPrimairyHighlight());
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
		return this.profession.getLevel()*100;
	}
	
	public int getMaxHP(){ return (int) this.profession.getMaxHP(); }
	public int getMaxMP(){ return (int) this.profession.getMaxMP(); }
	public int getINT(){ return (int) this.profession.getINT(); }
	public int  getLUK(){ return (int) this.profession.getLUK(); }
	public int getSTR(){ return (int) this.profession.getSTR(); }
	public int getDEX(){ return (int) this.profession.getDEX(); }
	
	public Profession getProfession(){
		return this.profession;
	}

	public void addExp(int value) {
		this.EXP+=value;
	}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {return null;}

	@Override
	public String saveToString() {
		return "LOLGONNAPUTSOMETHINGHERELATER";
	}
	
	@Override
	public boolean isInteractive(){
		return true;
	}
	
	@Override
	public void init() throws SlickException {
		OnFire.init();
		Fireball.init();
	}

	public void setInput(Input input) {
		this.input=input;
	}
	@Override
	public Action onInteract(Entity e) {
		// TODO this should never be called
		return null;
	}
	
	public String toString(){
		return "Player["+this.getX()+","+this.getY()+"]";
	}


}