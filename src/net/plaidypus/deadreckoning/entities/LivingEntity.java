package net.plaidypus.deadreckoning.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.Items.*;
import net.plaidypus.deadreckoning.actions.Action;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class LivingEntity extends Entity {

	public int maxHP, maxMP, STR, INT, AGI;
	public int HP, MP, MOV, VIS;
	public Animation stand, basicAttack, walking, damageFront, damageBack,
			death;
	public Animation currentAnimation;
	public ArrayList<Animation> animations;
	int currentAnimationID;
	public boolean animating;
	public static final int ANIMATION_STAND = 0, ANIMATION_ATTACK = 1,
			ANIMATION_WALK = 2, ANIMATION_FLINCH_FRONT = 3,
			ANIMATION_FLINCH_BACK = 4, ANIMATION_DEATH = 5;
	
	ArrayList<Item> inventory;
	ArrayList<Equip> epuips;
	
	/**
	 * subclass of entity with some basic stats for damage calulcation and
	 * health, etc. also animations
	 * 
	 * @param entityfile
	 */
	public LivingEntity(String entityfile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					entityfile)));
			System.out.println(this);
			loadFromFile(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		currentAnimation = stand;

		setFacing(false);

		animating = false;

		animations = new ArrayList<Animation>(0);
		animations.add(stand);
		animations.add(basicAttack);
		animations.add(walking);
		animations.add(damageFront);
		animations.add(damageBack);
		animations.add(death);
	}

	/**
	 * updates the entity (updates the animation)
	 */
	public void update(GameContainer gc, int delta) {
		currentAnimation.update(delta);
		if (this.currentAnimation.isStopped()) {
			this.setCurrentAnimation(ANIMATION_STAND);
			this.animating = false;
		}
	}

	/**
	 * still abstract because different livingEntities will have differing AIs
	 */
	public abstract Action chooseAction(GameContainer gc, int delta);

	/**
	 * deals the entity some amount of physical damage. This method takes the
	 * raw damage, and applies the physical defense of the livingentity to it
	 * (STAT MANAGEMENT)
	 * 
	 * @param damage
	 *            the damage to deal
	 */
	public void damagePhysical(int damage) {// TODO math for damage reduction
											// magic
		this.HP -= damage;
	}

	/**
	 * deals the entity some amount of magical damage. This method takes the raw
	 * damage, and applies the magical defense of the livingentity to it (STAT
	 * MANAGEMENT)
	 * 
	 * @param damage
	 *            the damage to deal
	 */
	public void damageMagical(int damage) {// TODO math for damage reduction
											// magic
		this.HP -= damage;
	}

	/**
	 * checks to see if this entity is prepared to accept a command
	 */
	public boolean isIdle() {
		return super.isIdle()
				&& (this.nextAction == null || this.nextAction.completed)
				&& !this.animating;
	}

	/**
	 * renders the entity at an x and y (of the tile XY system)
	 */
	public void render(Graphics g, float x, float y) {
		g.drawImage(
				currentAnimation.getCurrentFrame().getFlippedCopy(getFacing(),
						false), x * DeadReckoningGame.tileSize + relativeX, y
						* DeadReckoningGame.tileSize + relativeY);
	}

	/**
	 * changes the current animation to one determined by a static int
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
	 * returns the current animation ID
	 * 
	 * @return the animation
	 */
	public int getCurrentAnimationID() {
		return currentAnimationID;
	}

	/**
	 * gets the speed the entity can move at (STAT MANAGEMENT)
	 * 
	 * @return the number of tiles it can move per turn
	 */
	public int getMovementSpeed() {
		return MOV;
	}

	// STAT referencing

	/**
	 * gets the range the entity can attack with its default attack
	 * (STAT MANAGEMENT)
	 * 
	 * @return the number of tiles away the entity can target
	 */
	public int getAttackRange() {
		return 1;
	}

	/**
	 * checks to see if a living entity can see a certain entity
	 * @param e the entity to check against
	 * @return if the entity can see it
	 */
	public boolean canSee(Entity e) {
		return canSee(e.getLocation());
	}

	/**
	 * checks to see if a living entity can see a certain tile
	 * @param t the tile to check against
	 * @return if the entity can see it
	 */
	public boolean canSee(Tile t) {
		return Utilities.getDistance(getLocation(), t) <= this.VIS && this.getParent().isLineOfSight(getLocation(), t);
	}

	/**
	 * Loads an entity from a text file. SO UGLY IT HURTS, but it didn't make
	 * sense to break into subroutines
	 * 
	 * @param reader
	 *            the bufferedreader of the text file, on the first line of the
	 *            textfile
	 */
	public void loadFromFile(BufferedReader reader) throws IOException,
			SlickException {
		String ParsingMode = "";

		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		HashMap<String, SpriteSheet> images = new HashMap<String, SpriteSheet>();
		HashMap<String, Animation> animations = new HashMap<String, Animation>();

		String in = "";

		System.out.println("Parsing Entity....");
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
					if (ParsingMode.equals(":STATS:")) {
						String[] tostat = in.replaceAll(" ", "").split("=");
						stats.put(tostat[0], Integer.parseInt(tostat[1]));
					} else if (ParsingMode.equals(":IMAGES:")) {
						String[] toimage = in.split("=");
						toimage[0] = toimage[0].replaceAll(" ", "");

						String[] toimageB = toimage[1].split("\"");

						SpriteSheet p = new SpriteSheet(toimageB[1],
								DeadReckoningGame.tileSize,
								DeadReckoningGame.tileSize, new Color(255, 255,
										255));

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

		maxHP = stats.get("HP");
		maxMP = stats.get("MP");
		STR = stats.get("STR");
		INT = stats.get("INT");
		AGI = stats.get("AGI");
		MOV = stats.get("MOV");
		VIS = stats.get("VIS");

		HP = maxHP;
		MP = maxMP;

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

}
