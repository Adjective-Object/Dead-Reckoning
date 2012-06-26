package net.plaidypus.deadreckoning.hudelements.button;

import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class Button.
 */
public abstract class Button extends HudElement {

	/**
	 * Instantiates a new button.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 */
	public Button(int x, int y, int bindMethod) {
		super(x, y, bindMethod, true);
	}

	/** The moused. */
	boolean pressed, moused;

	/**
	 * Checks if is pressed.
	 * 
	 * @return true, if is pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.hudelements.HudElement#update(org.newdawn
	 * .slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();
		boolean overlap = mx > getAbsoluteX() && mx < getAbsoluteX() + getWidth()
				&& my > getAbsoluteY() && my < getAbsoluteY() + getHeight();

		if (overlap) {
			moused = true;
		} else {
			moused = false;
		}

		if (this.hasFocus && gc.getInput().isKeyPressed(Input.KEY_ENTER)
				|| overlap
				&& gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			pressed = true;
		} else {
			pressed = false;
		}
	}

}
