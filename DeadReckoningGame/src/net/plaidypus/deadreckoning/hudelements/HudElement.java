package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class HudElement.
 */
public abstract class HudElement {

	/** The Constant cursorSize. */
	static final int cursorSize = 10;

	/** The bind method. */
	public int xoff, yoff, bindMethod, personalBindMethod=TOP_LEFT;

	/** The has focus. */
	public boolean hasFocus;

	/** The needs focus. */
	public boolean needsFocus;

	/** The mouseover text. */
	String mouseoverText = null;

	/** The Constant BOTTOM_RIGHT. */
	public static final int TOP_LEFT = 0, TOP_CENTER = 1, TOP_RIGHT = 2,
			CENTER_LEFT = 3, CENTER_CENTER = 4, CENTER_RIGHT = 5,
			BOTTOM_LEFT = 6, BOTTOM_CENTER = 7, BOTTOM_RIGHT = 8;

	/** The offsets. */
	static int[][] offsets;

	protected HudElementContainer container;
	
	/**
	 * Instantiates a new hud element.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param bindMethod
	 *            the bind method
	 * @param needFoc
	 *            the need foc
	 */
	public HudElement(int x, int y, int bindMethod, boolean needFoc) {
		this.xoff = x;
		this.yoff = y;
		this.bindMethod = bindMethod;
		this.needsFocus = needFoc;
		this.setContainer(HudElementContainer.defaultContainer);
	}

	/**
	 * Update.
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @param delta
	 *            the delta
	 * @throws SlickException
	 *             the slick exception
	 */
	public abstract void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException;

	
	public HudElementContainer getContainer() {
		return this.container;
	}
	
	public void setContainer(HudElementContainer container){
		this.container=container;
	}

	/**
	 * changes some contents of this HudElement based on the value of Object o.
	 * this is meant to be called by the HudLayersState.makeFrom(Objet[])
	 * function, right before a state is shifted into, in order to shift around
	 * a few values. this is faster than say, rebuilding the whole state.
	 * 
	 * Ex: LootStates are passed two inventories in the makeFrom, in order to
	 * change the contents of the two inventory display-ers without the need for
	 * a rebuild of all HudElements.
	 * 
	 * @param o
	 *            the o
	 */
	public abstract void makeFrom(Object o);

	/**
	 * Sets the focus.
	 * 
	 * @param b
	 *            the new focus
	 */
	public void setFocus(boolean b) {
		this.hasFocus = b;
	}

	/**
	 * Calculate offsets.
	 * 
	 * @param gc
	 *            the gc
	 */
	public static void calculateOffsets(GameContainer gc) {
		offsets = new int[9][2];
		for (int i = 0; i < 3; i++) {
			for (int p = 0; p < 3; p++) {
				offsets[p + i * 3][0] = (int) (gc.getWidth() / 2.0 * p);
				offsets[p + i * 3][1] = (int) (gc.getHeight() / 2.0 * i);
			}
		}
	}

	/**
	 * Sets the mouseover text.
	 * 
	 * @param text
	 *            the new mouseover text
	 */
	public void setMouseoverText(String text) {
		this.mouseoverText = text;
	}

	/**
	 * Inits the.
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @throws SlickException
	 *             the slick exception
	 */
	public abstract void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException;

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public abstract int getWidth();

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public abstract int getHeight();

	/**
	 * Render.
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @param g
	 *            the g
	 * @throws SlickException
	 *             the slick exception
	 */
	public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException;

	/**
	 * Render mouse over.
	 * 
	 * @param gc
	 *            the gc
	 * @param sbg
	 *            the sbg
	 * @param g
	 *            the g
	 * @throws SlickException
	 *             the slick exception
	 */
	public void renderMouseOver(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		int mx = gc.getInput().getMouseX(), my = gc.getInput().getMouseY();

		g.setFont(DeadReckoningGame.menuSmallFont);

		if (mx > getAbsoluteX() && mx < getAbsoluteX() + getWidth() && my > getAbsoluteY()
				&& my < getAbsoluteY() + getHeight() && mouseoverText != null) {
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
	
	public int[] getPersonalOffsets(int bindmethod){
		int[] offsets = new int[2];
		offsets[0] = (int) (this.getWidth()  / 2.0 * ( bindmethod % 3 ));
		offsets[1] = (int) (this.getHeight() / 2.0 * ( bindmethod / 3 ));
		return offsets;
	}
	
	public int getX(){
		return xoff + offsets[bindMethod][0] - this.getPersonalOffsets(this.personalBindMethod)[0];
	}
	
	public int getY(){
		return yoff + offsets[bindMethod][1] - this.getPersonalOffsets(this.personalBindMethod)[1];
	}
	
	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public int getAbsoluteX() {
		return xoff + offsets[bindMethod][0] + this.container.getX() - this.getPersonalOffsets(this.personalBindMethod)[0];
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public int getAbsoluteY() {
		return yoff + offsets[bindMethod][1] + this.container.getY() - this.getPersonalOffsets(this.personalBindMethod)[1];
	}
	
	public void onEnter(GameContainer container, StateBasedGame game)throws SlickException{}

}
