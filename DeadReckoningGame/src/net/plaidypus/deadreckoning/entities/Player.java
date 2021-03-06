package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.skills.Attack;
import net.plaidypus.deadreckoning.skills.ChangeState;
import net.plaidypus.deadreckoning.skills.Interacter;
import net.plaidypus.deadreckoning.skills.Loot;
import net.plaidypus.deadreckoning.skills.PreBakedMove;
import net.plaidypus.deadreckoning.skills.ViewSkills;
import net.plaidypus.deadreckoning.skills.Wait;
import net.plaidypus.deadreckoning.statmaster.Profession;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

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

	/** The current skill. */
	public int currentSkill;

	/** The EXP. */
	public int EXP;

	/** The profession. */
	public Profession profession;

	/**
	 * The epuips. 0 = hat 1 = top / shirt 2 = bottom / pants 3 = feet / shoes
	 * 4 = weapon hand 1 5 = weapon hand 2 6 = accesory 1 7 = accesory 2
	 * */
	ArrayList<Equip> equips;

	static protected SpriteSheet levelUp;

	/**
	 * Instantiates a new player.
	 */
	public Player() {
	}

	/**
	 * subclass of living entity that is controlled by user input.
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 * @param p
	 *            the p
	 * @param i
	 *            the input object returned by GameContainer.getInput()
	 * @throws SlickException
	 *             the slick exception
	 */
	public Player(Profession p, Input in)
			throws SlickException {
		super(p.getParentMod(), p.getEntityFile(), p,
				Entity.ALLIGN_FRIENDLY);
		this.input = in;

		this.profession = p;
		this.profession.parentTo(this);

		keyBinds = new int[] { Input.KEY_A, Input.KEY_D, Input.KEY_W,
				Input.KEY_S, Input.KEY_Q, Input.KEY_1, Input.KEY_2,
				Input.KEY_3, Input.KEY_4, Input.KEY_5, Input.KEY_6,
				Input.KEY_7, Input.KEY_8, Input.KEY_9, Input.KEY_0,
				Input.KEY_MINUS, Input.KEY_EQUALS, Input.KEY_T, Input.KEY_L,
				Input.KEY_I, Input.KEY_M, Input.KEY_K, Input.KEY_E};
		skills.add( new PreBakedMove(entityID, -1, 0));
		skills.add( new PreBakedMove(entityID, 1, 0));
		skills.add( new PreBakedMove(entityID, 0, -1));
		skills.add( new PreBakedMove(entityID, 0, 1));
		skills.add( new Attack(entityID));

		skills.add( p.getTrees()[0].getSkills()[0]);
		skills.add( p.getTrees()[0].getSkills()[1]);
		skills.add( p.getTrees()[0].getSkills()[2]);
		skills.add( p.getTrees()[0].getSkills()[3]);
		skills.add( p.getTrees()[1].getSkills()[0]);
		skills.add( p.getTrees()[1].getSkills()[1]);
		skills.add( p.getTrees()[1].getSkills()[2]);
		skills.add( p.getTrees()[1].getSkills()[3]);
		skills.add( p.getTrees()[2].getSkills()[0]);
		skills.add( p.getTrees()[2].getSkills()[1]);
		skills.add( p.getTrees()[2].getSkills()[2]);
		skills.add( p.getTrees()[2].getSkills()[3]);

		skills.add(new Wait(entityID));
		skills.add(new Loot(entityID));
		skills.add(new ChangeState(entityID, DeadReckoningGame.INVENTORYSTATE));
		skills.add(new ChangeState(entityID,DeadReckoningGame.MAPSTATE));
		skills.add(new ViewSkills(entityID));
		skills.add(new Interacter(entityID));

		this.skills.addAll(p.getSkillList());
		this.equips = new ArrayList<Equip>(9);
		for (int i = 0; i < 9; i++) {
			this.equips.add(null);
		}
		
		this.HP=this.profession.getMaxHP();
		this.MP=this.profession.getMaxMP();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.LivingEntity#canSee(net.plaidypus
	 * .deadreckoning.board.Tile)
	 */
	@Override
	public boolean canSee(Tile t) {
		return this.getLocation().isVisible();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.LivingEntity#update(org.newdawn.
	 * slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		if (gc.getInput().isKeyDown(Input.KEY_RBRACKET)) {
			this.EXP += delta;
		}
		if (gc.getInput().isKeyPressed(Input.KEY_LBRACKET)) {
			this.setStealthed(!this.isStealthed());
		}
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		ArrayList<Action> c = super.advanceTurn();
		return c;
	}
	
	@Override
	public void recalculateStatBonuses(){
		super.recalculateStatBonuses();
		for (int i = 0; i < 8; i++) {// updating the stat alterations based on
			if (this.equips.get(i) != null) {
			Equip e = this.equips.get(i);
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
	}
	
	public Equip equipItem(Equip item){
		Equip retVal = this.equips.get(item.getSlot());
		this.equips.set(item.getSlot(),item);
		recalculateStatBonuses();
		return retVal;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects
	 * (org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void updateBoardEffects(GameContainer gc) {
		super.updateBoardEffects(gc);
		this.getParent().lightInRadius(getLocation(), 2);// TODO visibility
															// light radius
															// thing?
		if (this.EXP >= this.getEXPforLevel()) {
			this.EXP -= this.getEXPforLevel();
			this.profession.levelUp();
			Log.info(this.getName() + " leveled up!");
			this.MP = this.profession.getMaxMP();
			this.HP = this.profession.getMaxHP();
			Animation levelUp = new Animation(Player.levelUp, 100);// TODO
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
		
		if (!this.isAlive()){
			DeadReckoningGame.instance.enterState(DeadReckoningGame.DEATHSTATE);
		}
	}

	/**
	 * action choosing for the player (returns null often because the player
	 * takes time to decide/input).
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @return the action
	 */
	@Override
	public Action decideNextAction(GameContainer gc, int delta) {
		for (int i = 0; i < keyBinds.length; i++) {
			if (input.isKeyPressed(keyBinds[i])) {
				this.currentSkill = i;

				if (skills.get(i).isInstant() && skills.get(i).canBeCast()) {
					return skills.get(i).makeAction(this
							.getLocation());
				}

				else if (skills.get(i).canBeCast()) {
					skills.get(i).highlightRange(this.getParent());
					if (this.getParent().getPrimairyHighlight() == null) {
						this.getParent().setPrimairyHighlight(
								this.getLocation());
					} else if (canTarget()) {
						return skills.get(i).makeAction(this
								.getParent().getPrimairyHighlight());
					}
				}
			}
		}

		if ((input.isKeyPressed(Input.KEY_ENTER) || input
				.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				&& this.getParent().getPrimairyHighlight() != null
				&& canTarget() && skills.get(currentSkill).canBeCast()) {
			return skills.get(currentSkill).makeAction(this.getParent()
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
		return this.profession.getMaxHP();
	}

	/**
	 * Gets the max mp.
	 * 
	 * @return the max mp
	 */
	public int getMaxMP() {
		return this.profession.getMaxMP();
	}

	/**
	 * Gets the iNT.
	 * 
	 * @return the iNT
	 */
	public int getINT() {
		return this.profession.getINT();
	}

	/**
	 * Gets the lUK.
	 * 
	 * @return the lUK
	 */
	public int getLUK() {
		return this.profession.getLUK();
	}

	/**
	 * Gets the sTR.
	 * 
	 * @return the sTR
	 */
	public int getSTR() {
		return this.profession.getSTR();
	}

	/**
	 * Gets the dEX.
	 * 
	 * @return the dEX
	 */
	public int getDEX() {
		return this.profession.getDEX();
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
	 * @param value
	 *            the value
	 */
	public void addExp(int value) {
		this.EXP += value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus
	 * .deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		Log.error("PLAYER.SAVETOSTRING() METHOD NOT MEANT TO BE CALLED");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		Player.levelUp = new SpriteSheet( new Image("res/levelUp.png"), 32, 32);
	}

	/**
	 * Sets the input.
	 * 
	 * @param input
	 *            the new input
	 */
	public void setInput(Input input) {
		this.input = input;
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
		// TODO this should never be called
		return null;
	}

	public ArrayList<Equip> getEquips() {
		return this.equips;
	}
}