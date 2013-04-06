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
		int mx = gc.getInput().getMouseX()-this.getAbsoluteX(), my =gc.getInput().getMouseY()-this.getAbsoluteY();
		
		if(my>=65){
			if(mx>=80){
				this.setMouseoverText("LUK");
			} else{
				this.setMouseoverText("DEX");
			}
		}
		else if(my>=45){
			if(mx>=80){
				this.setMouseoverText("INT");
			} else{
				this.setMouseoverText("STR");
			}
		}
		else if(my>=20 && mx<80){
			this.setMouseoverText("Mana");
		}
		else if(my>=5 && mx<80){
			this.setMouseoverText("Health");
		}
		else{
			this.setMouseoverText(null);
		}
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
		g.drawImage(statIcons.getSprite(0, 0), getAbsoluteX(), getAbsoluteY());
		g.drawString(
				Integer.toString(target.getStatMaster().getRawMaxHP())
						+ "+("
						+ Integer.toString(target.getStatMaster()
								.getBonusMaxHP()) + ")", getAbsoluteX() + 21, getAbsoluteY());
		g.drawImage(statIcons.getSprite(1, 0), getAbsoluteX(), getAbsoluteY() + 20);
		g.drawString(
				Integer.toString(target.getStatMaster().getRawMaxMP())
						+ "+("
						+ Integer.toString(target.getStatMaster()
								.getBonusMaxMP()) + ")", getAbsoluteX() + 21,
				getAbsoluteY() + 20);

		g.drawImage(statIcons.getSprite(2, 0), getAbsoluteX(), getAbsoluteY() + 45);
		g.drawString(Integer.toString(target.getStatMaster().getRawSTR())
				+ "+(" + Integer.toString(target.getStatMaster().getBonusSTR())
				+ ")", getAbsoluteX() + 20, getAbsoluteY() + 45);
		g.drawImage(statIcons.getSprite(3, 0), getAbsoluteX(), getAbsoluteY() + 65);
		g.drawString(Integer.toString(target.getStatMaster().getRawDEX())
				+ "+(" + Integer.toString(target.getStatMaster().getBonusDEX())
				+ ")", getAbsoluteX() + 20, getAbsoluteY() + 65);

		g.drawImage(statIcons.getSprite(4, 0), getAbsoluteX() + 80, getAbsoluteY() + 45);
		g.drawString(Integer.toString(target.getStatMaster().getRawINT())
				+ "+(" + Integer.toString(target.getStatMaster().getBonusINT())
				+ ")", getAbsoluteX() + 101, getAbsoluteY() + 45);
		g.drawImage(statIcons.getSprite(5, 0), getAbsoluteX() + 80, getAbsoluteY() + 65);
		g.drawString(Integer.toString(target.getStatMaster().getRawLUK())
				+ "+(" + Integer.toString(target.getStatMaster().getBonusLUK())
				+ ")", getAbsoluteX() + 101, getAbsoluteY() + 65);
	}

}
