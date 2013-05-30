package core.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TemplePedestal extends Entity{
	
	/** The light. */
	int light;

	/** The img. */
	static SpriteSheet img;

	/** The ani. */
	Animation ani;
	
	public TemplePedestal(){}
	
	public TemplePedestal(int area){
		super();
		this.light = area;
		this.isTerrain = true;
		this.ani = new Animation(img,
				new int[] { 0,0, 1,0, 2,0, 1,0 },
				new int[] { 1000, 500, 500, 500 });
	}
	
	public void init() throws SlickException {
		img = new SpriteSheet(ModLoader.loadImage("core/res/bossroom/pedastal.png"), 32, 32);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		this.ani.update(delta);
	}

	@Override
	public void updateBoardEffects(GameContainer gc) {
		this.getParent().lightInRadius(getLocation(), this.light);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#chooseAction(org.newdawn.
	 * slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#forceRender(org.newdawn.slick
	 * .Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(ani.getCurrentFrame(), x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus
	 * .deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		TemplePedestal t = new TemplePedestal(Integer.parseInt(toload[4]));
		t.setLocation(
				g.getTileAt(
						Integer.parseInt(toload[1]),
						Integer.parseInt(toload[2])),
				Integer.parseInt(toload[3]));
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return this.getGenericSave() + ":" + this.light;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean makesActions() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.
	 * deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity e) {
		// TODO nothing doing here
		return null;
	}
	
}
