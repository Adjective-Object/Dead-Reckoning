package core.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/**
 * The Class Torch.
 */
public class Torch extends Entity {

	/** The light. */
	int light;

	/** The img. */
	static SpriteSheet img;

	/** The ani. */
	Animation ani;

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new torch.
	 */
	public Torch() {
	}

	/**
	 * Instantiates a new torch.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 * @param areaofLight
	 *            the areaof light
	 */
	public Torch(Tile t, int layer, int areaofLight) {
		super(t, layer);
		this.light = areaofLight;
		ani = new Animation(img, new int[] { 0, 0, 1, 0, 2, 0, 3, 0 },
				new int[] { 60, 60, 60, 60 });
		this.description = "the fire flickers mesmerizingly";
		this.isTerrain = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	public void init() throws SlickException {
		img = new SpriteSheet(ModLoader.loadImage("core/res/torch.png"), 32, 32);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.
	 * GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
		this.ani.update(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn
	 * .slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
		this.getParent().lightInRadius(getLocation(), this.light);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#chooseAction(org.newdawn.
	 * slick.GameContainer, int)
	 */
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
		return new Torch(g.getTileAt(Integer.parseInt(toload[1]),
				Integer.parseInt(toload[2])), Integer.parseInt(toload[3]),
				Integer.parseInt(toload[4]));
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
