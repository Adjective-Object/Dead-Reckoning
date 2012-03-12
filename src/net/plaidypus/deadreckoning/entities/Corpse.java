package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

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
		return null;
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDeath() {}

	@Override
	public void init() throws SlickException {}

}
