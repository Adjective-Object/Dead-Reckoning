package net.plaidypus.deadreckoning.hudelements.game.substates;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

public class StatDisplayElement extends HudElement {

	LivingEntity target;

	static SpriteSheet statIcons;

	public StatDisplayElement(int x, int y, int bindMethod) {
		super(x, y, bindMethod, false);
		this.setMouseoverText("STATS");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	@Override
	public void makeFrom(Object o) {
		this.target = (LivingEntity) o;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		statIcons = new SpriteSheet(new Image("res/statIcons.png"), 16, 16);
	}

	@Override
	public int getWidth() {
		return 141;
	}

	@Override
	public int getHeight() {
		return 81;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setFont(DeadReckoningGame.menuSmallFont);
		g.setColor(DeadReckoningGame.menuTextColor);
		g.drawImage(statIcons.getSprite(0, 0), getX(), getY());
		g.drawString(Integer.toString(target.getStatMaster().getRawMaxHP())+"+("+ Integer.toString(target.getStatMaster().getBonusMaxHP()) +")",
				getX() + 21, getY());
		g.drawImage(statIcons.getSprite(1, 0), getX(), getY() + 20);
		g.drawString(Integer.toString(target.getStatMaster().getRawMaxMP())+"+("+ Integer.toString(target.getStatMaster().getBonusMaxMP()) +")",
				getX() + 21, getY() + 20);

		g.drawImage(statIcons.getSprite(2, 0), getX(), getY() + 45);
		g.drawString(Integer.toString(target.getStatMaster().getRawSTR())+"+("+ Integer.toString(target.getStatMaster().getBonusSTR()) +")",
				getX() + 20, getY() + 45);
		g.drawImage(statIcons.getSprite(3, 0), getX(), getY() + 65);
		g.drawString(Integer.toString(target.getStatMaster().getRawDEX())+"+("+ Integer.toString(target.getStatMaster().getBonusDEX()) +")",
				getX() + 20, getY() + 65);

		g.drawImage(statIcons.getSprite(4, 0), getX() + 80, getY() + 45);
		g.drawString(Integer.toString(target.getStatMaster().getRawINT())+"+("+ Integer.toString(target.getStatMaster().getBonusINT()) +")",
				getX() + 101, getY() + 45);
		g.drawImage(statIcons.getSprite(5, 0), getX() + 80, getY() + 65);
		g.drawString(Integer.toString(target.getStatMaster().getRawLUK())+"+("+ Integer.toString(target.getStatMaster().getBonusLUK()) +")",
				getX() + 101, getY() + 65);
	}

}
