package net.plaidypus.deadreckoning.hudelements.simple;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class TextEntryBox extends HudElement {

	int[] inputValues = new int[] { Input.KEY_A, Input.KEY_B, Input.KEY_C,
			Input.KEY_D, Input.KEY_E, Input.KEY_F, Input.KEY_G, Input.KEY_H,
			Input.KEY_I, Input.KEY_J, Input.KEY_K, Input.KEY_L, Input.KEY_M,
			Input.KEY_N, Input.KEY_O, Input.KEY_P, Input.KEY_Q, Input.KEY_R,
			Input.KEY_S, Input.KEY_T, Input.KEY_U, Input.KEY_V, Input.KEY_W,
			Input.KEY_X, Input.KEY_Y, Input.KEY_Z, };

	String content = "";

	int width, height;

	public TextEntryBox(int x, int y, int bindMethod, int width, int height) {
		super(x, y, bindMethod, true);
		this.width = width;
		this.height = height;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		for (int i = 0; i < inputValues.length; i++) {
			if (gc.getInput().isKeyPressed(inputValues[i])) {
				int q = 97;
				if (gc.getInput().isKeyDown(Input.KEY_LSHIFT)
						|| gc.getInput().isKeyDown(Input.KEY_RSHIFT)) {
					q = 65;
				}
				this.content = this.content + (char) (q + i);// TODO ugh
			}
		}
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			this.content += " ";
		}
		if (gc.getInput().isKeyPressed(Input.KEY_BACK) && content.length() > 0) {
			this.content = this.content.substring(0, this.content.length() - 1);
		}
		if (gc.getInput().isKeyPressed(Input.KEY_DELETE)) {
			this.content = "";
		}
	}

	@Override
	public void makeFrom(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setClip(getX(), getY(), getWidth(), getHeight());
		g.setColor(DeadReckoningGame.menuTextBackgroundColor);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setFont(DeadReckoningGame.menuFont);
		g.setColor(DeadReckoningGame.menuTextColor);
		g.drawString(
				this.content,
				getX() + getWidth()
						- DeadReckoningGame.menuFont.getWidth(content),
				getY() + this.getHeight() / 2
						- DeadReckoningGame.menuFont.getHeight(content) / 2);
		g.clearClip();
	}

	public String getContent() {
		return this.content;
	}

}
