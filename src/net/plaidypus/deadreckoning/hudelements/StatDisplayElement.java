package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class StatDisplayElement extends HudElement{
	
	LivingEntity target;
	
	static SpriteSheet statIcons;
	
	public StatDisplayElement(int x, int y, int bindMethod){
		super(x,y,bindMethod,false);
	}
		
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {}

	@Override
	public void makeFrom(Object o) {
		this.target=(LivingEntity)o;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		statIcons = new SpriteSheet(new Image("res/statIcons.png"),16,16);
	}

	@Override
	public int getWidth() {
		return 121;
	}

	@Override
	public int getHeight() {
		return 81;
	}

	@Override	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(statIcons.getSprite(0, 0), getX(), getY());
		g.drawString( Integer.toString(target.getStatMaster().getMaxHP()), getX()+ 21, getY());
		g.drawImage(statIcons.getSprite(1, 0), getX(), getY()+20);
		g.drawString( Integer.toString(target.getStatMaster().getMaxMP()), getX()+ 21, getY()+20);		
		
		
		g.drawImage(statIcons.getSprite(2, 0), getX(), getY()+45);
		g.drawString( Integer.toString(target.getStatMaster().getSTR()), getX()+ 20, getY()+45);
		g.drawImage(statIcons.getSprite(3, 0), getX(), getY()+65);
		g.drawString( Integer.toString(target.getStatMaster().getDEX()), getX()+ 20, getY()+65);
		

		g.drawImage(statIcons.getSprite(4, 0), getX()+60, getY()+45);
		g.drawString( Integer.toString(target.getStatMaster().getINT()), getX()+ 81, getY()+45);
		g.drawImage(statIcons.getSprite(5, 0), getX()+60, getY()+65);
		g.drawString( Integer.toString(target.getStatMaster().getLUK()), getX()+ 81, getY()+65);
	}
	
}
