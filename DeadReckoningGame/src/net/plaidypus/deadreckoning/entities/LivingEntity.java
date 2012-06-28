package net.plaidypus.deadreckoning.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.items.Equip;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.professions.StatMaster;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.status.Status;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class LivingEntity.
 * 
 * A subclass of entity, used to handle monsters, the player, and (in the future) neutral mobs and pets
 */
public abstract class LivingEntity extends InteractiveEntity {

	/** The height. */
	public int HP, MP, width, height;

	/** The death. */
	public Animation stand, basicAttack, walking, damageFront, damageBack,
			death;

	/** The entity file. */
	public String parentMod, entityFile;

	/** The current animation. */
	public Animation currentAnimation;

	/** The animations. */
	public ArrayList<Animation> animations;

	/** The statuses. */
	public ArrayList<Status> statuses;

	/** The skills. */
	public ArrayList<Skill> skills = new ArrayList<Skill>(0);

	/** The id of the current animation. */
	int currentAnimationID;

	/** The Constant ANIMATION_DEATH. */
	public static final int ANIMATION_STAND = 0, ANIMATION_ATTACK = 1,
			ANIMATION_WALK = 2, ANIMATION_FLINCH_FRONT = 3,
			ANIMATION_FLINCH_BACK = 4, ANIMATION_DEATH = 5;

	/** The stat master, used to handle the (somewhat) unchanging stats of the entity */
	protected StatMaster statMaster;

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new living entity.
	 */
	public LivingEntity() {
	}

	/**
	 * subclass of entity with some basic stats for damage calulcation and
	 * health, etc. also animations
	 * 
	 * @param targetTile
	 *            the target tile
	 * @param layer
	 *            the layer
	 * @param entityFile
	 *            the entity file
	 * @param statMaster
	 *            the stat master
	 * @param allignment
	 *            the allignment
	 */
	public LivingEntity(Tile targetTile, int layer, String parentMod,
			String entityFile, StatMaster statMaster, int allignment) {
		super(targetTile, layer);
		try {
			InputStream entityReader = ModLoader.getModpackLoader(parentMod)
					.getResourceAsStream(parentMod + "/" + entityFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entityReader));
			this.parentMod = parentMod;
			loadFromFile(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		currentAnimation = stand;
		this.statMaster = statMaster;

		this.entityFile = entityFile;

		setFacing(false);

		this.setAllignment(allignment);
		this.statuses = new ArrayList<Status>(0);

		animations = new ArrayList<Animation>(0);
		animations.add(stand);
		animations.add(basicAttack);
		animations.add(walking);
		animations.add(damageFront);
		animations.add(damageBack);
		animations.add(death);

		this.HP = this.statMaster.getMaxHP();
		this.MP = this.statMaster.getMaxMP();
	}

	/**
	 * updates the entity (updates the animation).
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 */
	public void update(GameContainer gc, int delta) {
		currentAnimation.update(delta);
		if (this.currentAnimation.isStopped()) {
			this.setCurrentAnimation(ANIMATION_STAND);
		}

		for (int i = 0; i < this.statuses.size(); i++) {
			statuses.get(i).update(this, delta);
		}

		if (this.HP <= 0) {
			this.kill();
		}
	}

	/**
	 * still abstract because different livingEntities will have differing AIs.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @return the action
	 */
	public Action chooseAction(GameContainer gc, int delta) {
		return parseFinishedAction(decideNextAction(gc, delta));
	}

	/**
	 * necessary for the automatic usage of parsefinishedAction.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 * @return the action
	 */
	protected abstract Action decideNextAction(GameContainer gc, int delta);

	/**
	 * enacts something (be default, the updating of skill cooldowns) based on
	 * the return value of a.
	 * 
	 * @param a
	 *            the action to return
	 * @return a
	 */
	protected Action parseFinishedAction(Action a) {
		if (a != null) {
			for (int i = 0; i < this.skills.size(); i++) {
				this.skills.get(i).updateSkill();
			}
		}
		return a;
	}

	/**
	 * deals the entity some amount of physical damage. This method takes the
	 * raw damage, and applies the physical defense of the livingentity to it
	 * 
	 * @param damage
	 *            the damage to deal
	 */
	public void damagePhysical(int damage) {
		this.HP -= this.statMaster.getPhysicalDamage(damage);
	}

	/**
	 * deals the entity some amount of magical damage. This method takes the raw
	 * damage, and applies the magical defense of the livingentity to it 
	 * 
	 * @param damage
	 *            the damage to deal
	 */
	public void damageMagical(int damage) {
		this.HP -= this.statMaster.getMagicalDamage(damage);
	}

	/**
	 * renders the entity at an x and y (of the tile XY system).
	 * 
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(
				currentAnimation.getCurrentFrame().getFlippedCopy(getFacing(),
						false), (int) x + DeadReckoningGame.tileSize / 2
						- this.width / 2, (int) y + DeadReckoningGame.tileSize
						- this.height);
		for (int i = 0; i < statuses.size(); i++) {
			statuses.get(i).render(g, (int) x, (int) y);
		}
	}

	/**
	 * changes the current animation to one determined by a static int.
	 * 
	 * @param id
	 *            the animation id (LivingEntity.ANIMATION_BLAW)
	 */
	public void setCurrentAnimation(int id) {
		this.currentAnimationID = id;
		this.currentAnimation.restart();
		this.currentAnimation = this.animations.get(id);
	}

	/**
	 * Gets the current antimation.
	 * 
	 * @return the current antimation
	 */
	public Animation getCurrentAntimation() {
		return this.animations.get(currentAnimationID);
	}

	/**
	 * Gets the animation.
	 * 
	 * @param ID
	 *            the iD
	 * @return the animation
	 */
	public Animation getAnimation(int ID) {
		return animations.get(ID);
	}

	/**
	 * returns the current animation ID.
	 * 
	 * @return the animation
	 */
	public int getCurrentAnimationID() {
		return currentAnimationID;
	}

	/**
	 * checks to see if a living entity can see a certain entity.
	 * 
	 * @param e
	 *            the entity to check against
	 * @return if the entity can see it
	 */
	public boolean canSee(Entity e) {
		return canSee(e.getLocation());
	}

	/**
	 * checks to see if a living entity can see a certain tile.
	 * 
	 * @param t
	 *            the tile to check against
	 * @return if the entity can see it
	 */
	public boolean canSee(Tile t) {
		return t.lightLevel < 0
				&& this.getParent().isLineofSight(this.getLocation(), t);
	}

	// STAT referencing

	/**
	 * gets the range the entity can attack with its default attack (STAT
	 * MANAGEMENT).
	 * 
	 * @return the number of tiles away the entity can target
	 */
	public int getAttackRange() {
		return 1;
	}

	/**
	 * is the entity still alive?.
	 * 
	 * @return if the HP>0
	 */
	public boolean isAlive() {
		return this.HP > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	public void onDeath() {
		this.getParent().removeEntity(this);
		this.getParent().placeEntityNear(this.getX(), this.getY(),
				new Corpse(this.getLocation(), Tile.LAYER_PASSIVE_PLAY, this),
				Tile.LAYER_PASSIVE_PLAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	public boolean makesActions() {
		return this.isAlive();
	}

	/**
	 * Gets the conditions.
	 * 
	 * @return the conditions
	 */
	public ArrayList<Status> getConditions() {
		return this.statuses;
	}

	/**
	 * Adds the condition.
	 * 
	 * @param s
	 *            the s
	 */
	public void addCondition(Status s) {
		int i = 0;
		while (i < this.statuses.size()) {
			if (this.statuses.get(i).identifier.equals(s.identifier)) {
				Status p = s.collapseWithStatus(statuses.get(i));
				p.applyToEntity(this);
				this.statuses.remove(i);
				break;
			}
			i++;
		}
		if (i == this.statuses.size()) {
			this.statuses.add(s);
			s.applyToEntity(this);
		}
	}

	/**
	 * Clear conditions.
	 */
	public void clearConditions() {
		for (int i = 0; i < statuses.size(); i++) {
			statuses.get(i).removeFromEntity(this);
		}
		statuses.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isIdle()
	 */
	public boolean isIdle() {
		return this.getCurrentAnimationID() == LivingEntity.ANIMATION_STAND
				|| this.HP <= 0;
	}

	/**
	 * Loads an entity from a text file. SO UGLY IT HURTS, but it didn't make
	 * sense to break into subroutines
	 * 
	 * @param reader
	 *            the bufferedreader of the text file, on the first line of the
	 *            textfile
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SlickException
	 *             the slick exception
	 */
	public void loadFromFile(BufferedReader reader) throws IOException,
			SlickException {
		String ParsingMode = "";

		HashMap<String, String> info = new HashMap<String, String>();
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		HashMap<String, SpriteSheet> images = new HashMap<String, SpriteSheet>();
		HashMap<String, Animation> animations = new HashMap<String, Animation>();

		String in = "";

		while (!ParsingMode.equals(":EXIT:") && in != null) {
			// OH FUCK MY MIND SHITSHTISHIT
			// THIS IS SO UGLY. BUT IT WORKS
			// WHY DID I FEEL THE NEED TO DO THIS?
			in = reader.readLine();
			if (!in.equals("")) {
				if (in.contains(":")) {
					ParsingMode = in;
				}

				else {
					if (ParsingMode.equals(":INFORMATION:")) {
						String[] tostat = in.split("=");
						info.put(tostat[0], tostat[1]);
					}
					if (ParsingMode.equals(":STATS:")) {
						String[] tostat = in.replaceAll(" ", "").split("=");
						stats.put(tostat[0], Integer.parseInt(tostat[1]));
					} else if (ParsingMode.equals(":IMAGES:")) {
						String[] toimage = in.split("=");
						toimage[0] = toimage[0].replaceAll(" ", "");

						String[] toimageB = toimage[1].split("\"");

						SpriteSheet p = new SpriteSheet(ModLoader.loadImage(toimageB[1]),
								stats.get("TILEX"), stats.get("TILEY"));

						images.put(toimage[0], p);
					} else if (ParsingMode.equals(":SPRITES:")) {
						String[] toAnimation = in.replaceAll(" ", "")
								.split("=");
						String[] toAnimationB = toAnimation[1].replaceAll(" ",
								"").split("_");
						String[] toAnimationC = toAnimationB[2].split(",");

						int[] frames = new int[toAnimationC.length];
						int[] durations = new int[toAnimationC.length];
						for (int i = 0; i < toAnimationC.length; i++) {
							frames[i] = Integer.parseInt(toAnimationC[i]);
							durations[i] = Integer.parseInt(toAnimationB[1]);
						}

						animations.put(toAnimation[0],
								new Animation(images.get(toAnimationB[0]),
										frames, durations));
					}
				}
			}
		}

		setName(info.get("NAME"));

		width = stats.get("TILEX");
		height = stats.get("TILEY");

		stand = animations.get("Stand");
		basicAttack = animations.get("AttackBasic");
		basicAttack.setLooping(false);
		damageBack = animations.get("FlinchFront");
		damageBack.setLooping(false);
		damageFront = animations.get("FlinchBack");
		damageFront.setLooping(false);
		walking = animations.get("Walking");
		death = animations.get("Death");
		death.setLooping(false);
	}

	/**
	 * Gets the game.
	 * 
	 * @return the game
	 */
	public GameplayElement getGame() {
		return this.getParent().getGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	public ArrayList<Action> advanceTurn() {
		ArrayList<Action> actions = new ArrayList<Action>(0);
		this.statMaster.resetStatBonuses();
		for (int i = 0; i < statuses.size(); i++) {
			actions.addAll(statuses.get(i).advanceTurnEffects(this));
			statuses.get(i).alterStatMaster(this.statMaster);
		}
		return actions;
	}

	/**
	 * Gets the stat master.
	 * 
	 * @return the stat master
	 */
	public StatMaster getStatMaster() {
		return this.statMaster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#getName()
	 */
	public String getName() {
		String p = super.getName();
		if (!this.isAlive()) {
			p += "'s corpse";
		}
		return p;
	}

	/**
	 * Calculate exp value.
	 * 
	 * @return the int
	 */
	public int calculateEXPValue() {
		return this.statMaster.calculateEXPValue();
	}

}
