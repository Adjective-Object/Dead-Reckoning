package net.plaidypus.deadreckoning.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.MoveAction;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class LivingEntity extends Entity{
	
	public int maxHP,maxMP,STR,INT,AGI;
	public int HP,MP,MOV;
	public Animation stand, basicAttack, walking, damageFront, damageBack;
	public Animation currentAnimation;
	public ArrayList<Animation> animations;
	public static final int ANIMATION_STAND = 0, ANIMATION_ATTACK = 1, ANIMATION_WALK=2,
						ANIMATION_FLINCH_FRONT=3, ANIMATION_FLINCH_BACK=4;
	
	public Action nextAction;
	
	public LivingEntity(String entityfile){
		try {
			BufferedReader reader = new BufferedReader (	new FileReader( new File(entityfile)));
			loadFromFile(reader);
		}
		catch (IOException e) {	e.printStackTrace();	}
		catch (SlickException e) {	e.printStackTrace();	}
		
		currentAnimation = stand;
		
		animations = new ArrayList<Animation>(0);
		animations.add(stand);
		animations.add(basicAttack);
		animations.add(walking);
		animations.add(damageFront);
		animations.add(damageBack);
		
		nextAction = new MoveAction(this,10,10);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		currentAnimation.update(delta);
		if(nextAction!=null){
			nextAction.apply();
			nextAction=null;
		}
	}

	@Override
	public void render(Graphics g,int x, int y) {
		g.drawImage(currentAnimation.getCurrentFrame(),x*DeadReckoningGame.tileSize,y*DeadReckoningGame.tileSize);
	}
	
	public void setCurrentAnimation(int id){
		this.currentAnimation = this.animations.get(id);
	}
	
	public int getMovementSpeed(){
		return MOV;
	}
	
	/**
	 * Loads an entity from a text file.
	 * SO UGLY IT HURTS, but it didn't make sense to break indo subroutines
	 * @param reader the bufferedreader of the text file, on the first line of the textfile
	 */
	public void loadFromFile(BufferedReader reader) throws IOException, SlickException{
		String ParsingMode="";
		
		HashMap<String,Integer> stats = new HashMap<String,Integer>();
		HashMap<String,SpriteSheet> images = new HashMap<String,SpriteSheet>();
		HashMap<String,Animation> animations= new HashMap<String,Animation>();
		
		String in = "";

		System.out.println("Parsing Entity....");
		while(!ParsingMode.equals(":EXIT:") && in!=null){
			//OH FUCK MY MIND SHITSHTISHIT
			//THIS IS SO UGLY. BUT IT WORKS
			//WHY DID I FEEL THE NEED TO DO THIS?
			in = reader.readLine();
			if(!in.equals("")){
				if(in.contains(":")){
					ParsingMode = in;
				}
				
				else{
					if(ParsingMode.equals(":STATS:")){
						String[] tostat = in.replaceAll(" ","").split("=");
						stats.put(tostat[0], Integer.parseInt(tostat[1]) );
					}
					else if(ParsingMode.equals(":IMAGES:")){
						String[] toimage = in.split("=");
						toimage[0]=toimage[0].replaceAll(" ","");
						
						String[] toimageB = toimage[1].split("\"");
						
						SpriteSheet p = new SpriteSheet(new Image(toimageB[1]),
								DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
								
						images.put(toimage[0], p );
					}
					else if(ParsingMode.equals(":SPRITES:")){
						String[] toAnimation = in.replaceAll(" ","").split("=");
						String[] toAnimationB = toAnimation[1].replaceAll(" ","").split("_");
						String[] toAnimationC = toAnimationB[1].split(",");
						
						int[] frames = new int[toAnimationC.length];
						int[] durations = new int[toAnimationC.length];
						for(int i=0;i<toAnimationC.length;i++){
							frames[i]=Integer.parseInt(toAnimationC[i]);
							durations[i]=DeadReckoningGame.animationRate;
						}
						
						
						
						animations.put(toAnimation[0],
								new Animation(	images.get(toAnimationB[0]),
										frames, durations)
							);
					}
				}
			}
		}
		
		maxHP=stats.get("HP");
		maxMP=stats.get("MP");
		STR=stats.get("STR");
		INT=stats.get("INT");
		AGI=stats.get("AGI");
		MOV=stats.get("MOV");
		
		HP=maxHP;
		MP=maxMP;
		
		stand=animations.get("Stand");
		basicAttack=animations.get("AttackBasic");
		damageBack=animations.get("FlinchFront");
		damageFront=animations.get("FlinchBack");
		walking=animations.get("Walking");
	}
	
}
