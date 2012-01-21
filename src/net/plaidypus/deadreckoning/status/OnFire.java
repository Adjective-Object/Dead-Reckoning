package net.plaidypus.deadreckoning.status;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public class OnFire extends AnimatedStatus{
	
	int duration, power;
	static Image image;
	static String details;
	static SpriteSheet sprite;
	
	public OnFire( InteractiveEntity source, int duration, int power){
		super(source, image, details,new Animation(sprite, 60));
		this.duration=duration;
		this.power=power;
		this.statusID=Status.STATUS_ONFIRE;
	}
	
	public static void init() throws SlickException{
		image = new Image("res/onFireIcon.png");
		details = "OH SHIT, YOU'RE ON FIRE";
		sprite = new SpriteSheet("res/fireAnimation.png",DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
	}
	
	@Override
	public void applyToEntity(LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advanceTurnEffects(LivingEntity target) {
		System.out.println("blaw");
		target.getGame().addAction(new AttackAction(source.getLocation(),target.getLocation(),power,false,false,
				null, null, null, null));
		duration--;
	}

	@Override
	public void removeFromEntity(LivingEntity target) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public boolean isFinished() {
		return duration<=0;
	}

	@Override
	public Status makeFromString(LivingEntity target, String[] attributes) {
		target.addCondition(new OnFire(null,Integer.parseInt(attributes[1]),Integer.parseInt(attributes[2])));//TODO entity linking - how do I I don't even? store all entities to a hashmap in GameBoard to reference that way? or just create an array list and assume it will never need to be sorted? but that won't allow for any initiative
		return null;
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
