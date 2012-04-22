package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.state.HudLayersState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class HudElement {

	static final int cursorSize = 10;

	public int xoff, yoff, bindMethod;
	public boolean hasFocus;
	public boolean needsFocus;
	String mouseoverText = null;

	HudLayersState parentState;

	public static final int TOP_LEFT = 0, TOP_CENTER = 1, TOP_RIGHT = 2,
			CENTER_LEFT = 3, CENTER_CENTER = 4, CENTER_RIGHT = 5,
			BOTTOM_LEFT = 6, BOTTOM_CENTER = 7, BOTTOM_RIGHT = 8;

	static int[][] offsets;

	public HudElement(int x, int y, int bindMethod, boolean needFoc) {
		this.xoff = x;
		this.yoff = y;
		this.bindMethod = bindMethod;
		this.needsFocus = needFoc;
	}

	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException;

	public void update(GameContainer gc, StateBasedGame sbg, int delta,
			boolean hasFocus) throws SlickException {
		this.setFocus(hasFocus);
		this.update(gc, sbg, delta);
	}

	public void setParent(HudLayersState parentState) {
		this.parentState = parentState;
	}

	public abstract void makeFrom(Object o);

	public void setFocus(boolean b) {
		this.hasFocus = b;
	}

	public static void calculateOffsets(GameContainer gc) {
		offsets = new int[9][2];
		for (int i = 0; i < 3; i++) {
			for (int p = 0; p < 3; p++) {
				offsets[p + i * 3][0] = (int) (gc.getWidth() / 2.0 * p);
				offsets[p + i * 3][1] = (int) (gc.getHeight() / 2.0 * i);
			}
		}
	}

	public void setMouseoverText(String text) {
		this.mouseoverText = text;
	}

	public abstract void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException;

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException;

	public void renderMouseOver(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();

		g.setFont(DeadReckoningGame.menuSmallFont);

		if (mx > getX() && mx < getX() + getWidth() && my > getY()
				&& my < getY() + getHeight() && mouseoverText != null) {
			g.setColor(DeadReckoningGame.mouseoverBoxColor);
			g.fillRect(cursorSize + gc.getInput().getMouseX(), cursorSize
					+ gc.getInput().getMouseY(),
					g.getFont().getWidth(mouseoverText) + 10, g.getFont()
							.getHeight(mouseoverText) + 10);
			g.setColor(DeadReckoningGame.mouseoverTextColor);
			g.drawRect(cursorSize + gc.getInput().getMouseX(), cursorSize
					+ gc.getInput().getMouseY(),
					g.getFont().getWidth(mouseoverText) + 10, g.getFont()
							.getHeight(mouseoverText) + 10);
			g.drawString(mouseoverText, cursorSize + 5
					+ gc.getInput().getMouseX(), cursorSize + 5
					+ gc.getInput().getMouseY());
		}
	}

	public int getX() {
		return xoff + offsets[bindMethod][0];
	}

	public int getY() {
		return yoff + offsets[bindMethod][1];
	}

	public HudLayersState getParent() {
		return this.parentState;
	}

}
