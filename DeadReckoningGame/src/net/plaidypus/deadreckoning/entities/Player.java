package net.plaidypus.deadreckoning.entities;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.Save;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.professions.Profession;
import net.plaidypus.deadreckoning.skills.Attack;
import net.plaidypus.deadreckoning.skills.CheckInventory;
import net.plaidypus.deadreckoning.skills.Interacter;
import net.plaidypus.deadreckoning.skills.Loot;
import net.plaidypus.deadreckoning.skills.PreBakedMove;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.skills.ViewMap;
import net.plaidypus.deadreckoning.skills.ViewSkills;
import net.plaidypus.deadreckoning.skills.Wait;
import net.plaidypus.deadreckoning.status.OnFire;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/*

 Lezbihonest here, Max is the only one who's been coding this for a while.
 Created: 11/4/2011

 */

/**
 * The Class Player.
 */
public class Player extends LivingEntity {

	/** The input. */
	Input input;

	/** The key binds. */
	public static int[] keyBinds;
	
	/** The input skills. */
	public static Skill[] inputSkills;

	/** The current skill. */
	public int currentSkill;
	
	/** The EXP. */
	public int EXP;

	/** The profession. */
	public Profession profession;

	/** The epuips.
	 * 0 = hat
	 * 1 = top / shirt
	 * 2 = bottom / pants
	 * 3 = feet / shoes
	 * 5 = weapon hand 1
	 * 6 = weapon hand 2
	 * 7 = accesory 1
	 * 8 = accesory 2
	 * */
	protected ArrayList<Equip> equips; 
	
	static protected SpriteSheet levelUp;
	
	/**
	 * Instantiates a new player.
	 */
	public Player() {
	}

	/**
	 * subclass of living entity that is controlled by user input.
	 *
	 * @param targetTile the target tile
	 * @param layer the layer
	 * @param p the p
	 * @param i the input object returned by GameContainer.getInput()
	 * @throws SlickException the slick exception
	 */
	public Player(Tile targetTile, int layer, Profession p, Input in)
			throws SlickException {
		super(targetTile, layer, p.getParentMod(), p.getEntityFile(), p, Entity.ALLIGN_FRIENDLY);
		this.input = in;

		this.profession = p;
		this.profession.parentTo(this);

		keyBinds = new int[] { Input.KEY_A, Input.KEY_D, Input.KEY_W,
				Input.KEY_S, Input.KEY_Q,
				Input.KEY_F1, Input.KEY_F2,
				Input.KEY_F3, Input.KEY_F4,
				Input.KEY_F5, Input.KEY_F6,
				Input.KEY_F7, Input.KEY_F8,
				Input.KEY_F9, Input.KEY_F10,
				Input.KEY_F11, Input.KEY_F12,
				Input.KEY_T,
				Input.KEY_L, Input.KEY_I,
				Input.KEY_M, Input.KEY_K, Input.KEY_E };
		inputSkills = new Skill[] { new PreBakedMove(this, -1, 0),
				new PreBakedMove(this, 1, 0), new PreBakedMove(this, 0, -1),
				new PreBakedMove(this, 0, 1), new Attack(this),

				p.getTrees()[0].getSkills()[0], p.getTrees()[0].getSkills()[1],
				p.getTrees()[0].getSkills()[2], p.getTrees()[0].getSkills()[3],
				p.getTrees()[1].getSkills()[0], p.getTrees()[1].getSkills()[1],
				p.getTrees()[1].getSkills()[2], p.getTrees()[1].getSkills()[3],
				p.getTrees()[2].getSkills()[0], p.getTrees()[2].getSkills()[1],
				p.getTrees()[2].getSkills()[2], p.getTrees()[2].getSkills()[3],

				new Wait(this),
				new Loot(this), new CheckInventory(this),
				new ViewMap(this), new ViewSkills(this), new Interacter(this) };

		this.skills.addAll(p.getSkillList());
		this.equips = new ArrayList<Equip>(9);
		for(int i=0; i<9 ;i++){
			this.equips.add(null);
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.LivingEntity#canSee(net.plaidypus.deadreckoning.board.Tile)
	 */
	public boolean canSee(Tile t) {
		return this.getLocation().isVisible();
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.LivingEntity#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		if (gc.getInput().isKeyDown(Input.KEY_SCROLL)) {
			this.EXP += delta;
		}
		if (gc.getInput().isKeyPressed(Input.KEY_PAUSE)) {
			this.equips.set(0, new Equip("core",0));
		}
		if (gc.getInput().isKeyPressed(Input.KEY_F5)) {
			try {
				Save.updateSave(this.getGame().saveLocation, this, this.getParent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Action> advanceTurn(){
		ArrayList<Action> c = super.advanceTurn();
		for(int i=0; i<8; i++){//updating the stat alterations based on equips
			if(this.equips.get(i)!=null){
				Equip e= this.equips.get(i);
				this.profession.editHP(e.HP);
				this.profession.editMP(e.MP);
				this.profession.editINT(e.INT);
				this.profession.editLUK(e.LUK);
				this.profession.editDEX(e.DEX);
				this.profession.editSTR(e.STR);
				this.profession.editAtt(e.WAtt);
				this.profession.editDef(e.WDef);
				this.profession.editMAtt(e.MAtt);
				this.profession.editMDef(e.MDef);
			}
		}
		return c;
	}
	
	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects(org.newdawn.slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
		super.updateBoardEffects(gc, delta);
		this.getParent().lightInRadius(getLocation(), 2);// TODO visibility
															// light radius
															// thing?
		if (this.EXP >= this.getEXPforLevel()) {
			this.EXP -= this.getEXPforLevel();
			this.profession.levelUp();
			Action.sendMessage(this.getName() + " leveled up!");
			this.MP = this.profession.getMaxMP();
			this.HP = this.profession.getMaxHP();
			Animation levelUp = new Animation(this.levelUp, 100);// TODO
																		// actual
																		// level
																		// up
																		// animation
			levelUp.setLooping(false);
			this.getParent().addEffectOver(this.getLocation(),
					new AnimationEffect(this.getLocation(), levelUp));// TODO
																		// Level
																		// up
																		// Animation
		}
	}

	/**
	 * action choosing for the player (returns null often because the player
	 * takes time to decide/input).
	 *
	 * @param gc the gc
	 * @param delta the delta
	 * @return the action
	 */
	public Action decideNextAction(GameContainer gc, int delta) {
		for (int i = 0; i < keyBinds.length; i++) {
			if (input.isKeyPressed(keyBinds[i])) {
				this.currentSkill = i;

				if (inputSkills[i].isInstant() && inputSkills[i].canBeCast()) {
					return inputSkills[currentSkill].makeAction(this
							.getLocation());
				}

				else if (inputSkills[i].canBeCast()) {
					inputSkills[currentSkill].highlightRange(this.getParent());
					if (this.getParent().getPrimairyHighlight() == null) {
						this.getParent().setPrimairyHighlight(
								this.getLocation());
					} else if (canTarget()) {
						return inputSkills[currentSkill].makeAction(this
								.getParent().getPrimairyHighlight());
					}
				}
			}
		}

		if ((input.isKeyPressed(Input.KEY_ENTER) || input
				.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				&& this.getParent().getPrimairyHighlight() != null
				&& canTarget() && inputSkills[currentSkill].canBeCast()) {
			return inputSkills[currentSkill].makeAction(this.getParent()
					.getPrimairyHighlight());
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.getLocation().getParent().clearHighlightedSquares();
			this.getLocation().getParent().clearPrimaryHighlight();
		}
		return null;
	}

	/**
	 * Can target.
	 *
	 * @return true, if successful
	 */
	private boolean canTarget() {
		return this.getParent().getPrimairyHighlight().highlighted == Tile.HIGHLIGHT_CONFIRM
				&& this.canSee(this.getParent().getPrimairyHighlight());
	}

	/**
	 * Gets the eX pfor level.
	 *
	 * @return the eX pfor level
	 */
	public int getEXPforLevel() {
		return (int) (10 * Math.pow(2, this.profession.getLevel()));
	}

	/**
	 * Gets the max hp.
	 *
	 * @return the max hp
	 */
	public int getMaxHP() {
		return (int) this.profession.getMaxHP();
	}

	/**
	 * Gets the max mp.
	 *
	 * @return the max mp
	 */
	public int getMaxMP() {
		return (int) this.profession.getMaxMP();
	}

	/**
	 * Gets the iNT.
	 *
	 * @return the iNT
	 */
	public int getINT() {
		return (int) this.profession.getINT();
	}

	/**
	 * Gets the lUK.
	 *
	 * @return the lUK
	 */
	public int getLUK() {
		return (int) this.profession.getLUK();
	}

	/**
	 * Gets the sTR.
	 *
	 * @return the sTR
	 */
	public int getSTR() {
		return (int) this.profession.getSTR();
	}

	/**
	 * Gets the dEX.
	 *
	 * @return the dEX
	 */
	public int getDEX() {
		return (int) this.profession.getDEX();
	}

	/**
	 * Gets the profession.
	 *
	 * @return the profession
	 */
	public Profession getProfession() {
		return this.profession;
	}

	/**
	 * Adds the exp.
	 *
	 * @param value the value
	 */
	public void addExp(int value) {
		this.EXP += value;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus.deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return "LOLGONNAPUTSOMETHINGHERELATER";//TODO implement player position saving
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.LivingEntity#isInteractive()
	 */
	@Override
	public boolean isInteractive() {
		return true;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		OnFire.init();
		this.levelUp=new SpriteSheet(new Image("res/fireBurst.png"),32,32);
	}

	/**
	 * Sets the input.
	 *
	 * @param input the new input
	 */
	public void setInput(Input input) {
		this.input = input;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		// TODO this should never be called
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Player[" + this.getX() + "," + this.getY() + "]";
	}

}