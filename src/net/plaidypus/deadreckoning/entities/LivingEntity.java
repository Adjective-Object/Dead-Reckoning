package net.plaidypus.deadreckoning.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;
import net.plaidypus.deadreckoning.status.Status;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public abstract class LivingEntity extends InteractiveEntity {

	private int maxHP, maxMP, STR, INT, DEX, LUK, level;
	public int HP, MP, width, height;
	public Animation stand, basicAttack, walking, damageFront, damageBack,
			death;
	public Animation currentAnimation;
	public ArrayList<Animation> animations;
	public ArrayList<Status> statuses;
	int currentAnimationID;
	public static final int ANIMATION_STAND = 0, ANIMATION_ATTACK = 1,
			ANIMATION_WALK = 2, ANIMATION_FLINCH_FRONT = 3,
			ANIMATION_FLINCH_BACK = 4, ANIMATION_DEATH = 5;
	
	//Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	public LivingEntity(){}
	
	/**
	 * subclass of entity with some basic stats for damage calulcation and
	 * health, etc. also animations
	 * 
	 * @param entityfile
	 */
	public LivingEntity(Tile targetTile, int layer, String entityfile, int allignment) {
		super(targetTile, layer);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					entityfile)));
			loadFromFile(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		currentAnimation = stand;

		setFacing(false);
		
		this.setAllignment(allignment);
		this.statuses=new ArrayList<Status>(0);
		
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
		}
		
		for(int i=0; i<this.statuses.size(); i++){
			statuses.get(i).update(this, delta);
		}
		
		if(this.HP<=0){
			this.kill();
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
	 * renders the entity at an x and y (of the tile XY system)
	 */
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(
				currentAnimation.getCurrentFrame().getFlippedCopy(getFacing(),
						false), (int)x+DeadReckoningGame.tileSize/2-this.width/2, (int)y+DeadReckoningGame.tileSize-this.height);
		for(int i=0; i<statuses.size();i++){
			statuses.get(i).render(g,(int)x, (int)y);
		}
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
	
	public Animation getCurrentAntimation(){
		return this.animations.get(currentAnimationID);
	}
	
	public Animation getAnimation(int ID){
		return animations.get(ID);
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
		return t.lightLevel<0 && this.getParent().isLineofSight(this.getLocation(), t);
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
	 * is the entity still alive?
	 * @return if the HP>0
	 */
	public boolean isAlive() {
		return this.HP>0;
	}
	
	public void onDeath(){
		this.getParent().removeEntity(this);
		for(int i=0; i<this.getLocation().getEntities().length; i++){
		}
		this.getParent().placeEntityNear(this.getX(), this.getY(),new Corpse(this.getLocation(),Tile.LAYER_PASSIVE_PLAY,this), Tile.LAYER_PASSIVE_PLAY);
		for(int i=0; i<this.getLocation().getEntities().length; i++){
		}
	}
	
	public boolean isInteractive(){
		return this.isAlive();
	}
	
	public ArrayList<Status> getConditions(){
		return this.statuses;
	}
	
	public void addCondition(Status s){
		int i=0;
		while(i<this.statuses.size()){
			if(this.statuses.get(i).identifier.equals(s.identifier)){
				Status p = s.collapseWithStatus(statuses.get(i));
				p.applyToEntity(this);
				this.statuses.remove(i);
				break;
			}
			i++;
		}
		if(i==this.statuses.size()){
			this.statuses.add(s);
			s.applyToEntity(this);
		}
	}
	
	public void clearConditions(){
		for(int i=0; i<statuses.size(); i++){
			statuses.get(i).removeFromEntity(this);
		}
		statuses.clear();
	}
	
	public boolean isIdle(){
		return this.getCurrentAnimationID()==LivingEntity.ANIMATION_STAND || this.HP<=0;
	}
	
	//TODO stat afflictions/ buffs from statuses
	public int getDEX(){return this.DEX;}
	public int getLUK(){return this.LUK;}
	public int getSTR(){return this.STR;}
	public int getINT(){return this.INT;}
	
	public int getMagDefense(){return this.INT;}
	public int getWepDefense(){return this.STR;}
	
	public int getMaxHP() {return maxHP;}
	public int getMaxMP() {return maxMP;}
	
	public int getBaseDEX(){return this.DEX;}
	public int getBaseLUK(){return this.LUK;}
	public int getBaseSTR(){return this.STR;}
	public int getBaseINT(){return this.INT;}
	
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

						SpriteSheet p = new SpriteSheet(toimageB[1],
								stats.get("TILEX"), stats.get("TILEY"),
								new Color(255, 255, 255));

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
		
		this.maxHP = (stats.get("HP"));
		this.maxMP =(stats.get("MP"));
		STR = stats.get("STR");
		INT = stats.get("INT");
		DEX = stats.get("DEX");
		LUK = stats.get("LUK");
		
		width = stats.get("TILEX");
		height = stats.get("TILEY");
		
		
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

	public GameplayElement getGame() {
		return this.getParent().getGame();
	}
	
	public ArrayList<Action> advanceTurn(){
		ArrayList<Action> actions = new ArrayList<Action>(0);
		for (int i=0; i<statuses.size(); i++){
			actions.addAll(statuses.get(i).advanceTurnEffects(this));
		}
		return actions;
	}

	public int calculateEXPValue() {
		return this.level*10;//TODO angus
	}
	
	public String getName(){
		String p = super.getName();
		if(!this.isAlive()){
			p+="'s corpse";
		}
		return p;
	}
	
}
