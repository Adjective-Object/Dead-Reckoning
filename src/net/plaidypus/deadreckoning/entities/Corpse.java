package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.loader.EntityLoader;

public class Corpse extends InteractiveEntity{
	
	LivingEntity entity;
	
	public Corpse(Tile t,int layer,LivingEntity e) {
		super(t, layer);
		this.entity = e;
		this.inventory=entity.getInventory();
	}
	
	public void update(GameContainer gc, int delta) {
	}
	
	public void updateBoardEffects(GameContainer gc, int delta) {
	}

	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(entity.getAnimation(LivingEntity.ANIMATION_DEATH).getImage(entity.getAnimation(LivingEntity.ANIMATION_DEATH).getFrameCount()-1),x,y);
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		String[] subattr = new String[attributes.length-5];
		for(int i=5; i< attributes.length; i++){
			subattr[i-5]=attributes[i];
		}
		return new Corpse(target.getTileAt(
				Integer.parseInt(attributes[1]),
				Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]),
				(LivingEntity)EntityLoader.makerArray.get(Integer.parseInt(attributes[4])).makeFromString(target, subattr)
				);
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDeath() {}

}
