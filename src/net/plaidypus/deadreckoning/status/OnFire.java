package net.plaidypus.deadreckoning.status;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
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
		super(source, image, details, "OnFire", new Animation(sprite, 60));
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
	public ArrayList<Action> advanceTurnEffects(LivingEntity target) {
		ArrayList<Action> actions = new ArrayList<Action>(0);
		actions.add(new AttackAction(source,target.getLocation(),power,false,false,
				null, null, null, null));
		duration--;
		System.out.println(actions);
		return actions;
	}

	@Override
	public void removeFromEntity(LivingEntity target) {
		
	}
	
	
	@Override
	public boolean isFinished() {
		return duration<=0;
	}

	@Override
	public String getName() {
		return "On Fire!";
	}

}
