package net.plaidypus.deadreckoning.hudelements.game;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.statmaster.Profession;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class SkillMonitorElement.
 */
public class SkillMonitorElement extends HudElement { // TODO turn into a
														// subclass of panel to
														// allow for clicking ->
														// use skill.

	/** The to monitor. */
	Profession toMonitor;

	/** The target game. */
	GameplayElement targetGame;

	/**
	 * Instantiates a new skill monitor element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @param targetGame
	 *            the target game
	 */
	public SkillMonitorElement(int x, int y, int bindMethod,
			GameplayElement targetGame) {
		super(x, y, bindMethod, false);
		this.targetGame = targetGame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (this.toMonitor == null) {
			this.toMonitor = targetGame.player.getProfession();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#makeFrom(java.lang
	 * .Object)
	 */
	@Override
	public void makeFrom(Object o) {
		this.toMonitor = targetGame.player.getProfession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#init(org.newdawn.slick
	 * .GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getWidth()
	 */
	@Override
	public int getWidth() {
		return 12 * 35 + 10;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.hudelements.HudElement#getHeight()
	 */
	@Override
	public int getHeight() {
		return 42;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#render(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame,
	 * org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		ArrayList<Skill> skills = this.toMonitor.getSkillList();
		for (int i = 0; i < skills.size(); i++) {
			g.drawImage(skills.get(i).getImage(), this.getAbsoluteX() + 5 + i * 35,
					this.getAbsoluteY() + 5);
			if (skills.get(i).getCooldown() > 0
					|| skills.get(i).getLevel() == 0) {
				g.setColor(DeadReckoningGame.skillInvalidColor);
				g.fillRect(this.getAbsoluteX() + 5 + i * 35, this.getAbsoluteY() + 5, 32, 32);
			}
			if (skills.get(i).getCooldown() > 0) {
				g.setColor(DeadReckoningGame.menuTextColor);
				g.setFont(DeadReckoningGame.menuFont);
				g.drawString(
						Integer.toString(skills.get(i).getCooldown()),
						this.getAbsoluteX()
								+ 21
								+ i
								* 35
								- g.getFont().getWidth(
										Integer.toString(skills.get(i)
												.getCooldown())) / 2,
						this.getAbsoluteY()
								+ 21
								- g.getFont().getHeight(
										Integer.toString(skills.get(i)
												.getCooldown())) / 2);
			}
		}
	}

}
