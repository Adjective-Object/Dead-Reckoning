package core.entities;

import java.util.ArrayList;
import java.util.Random;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.StaticImageEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Statue.
 */
public class TemplePillar extends StaticImageEntity {

	/** The i. */
	static Image ia, ib;

	/**
	 * Instantiates a new statue.
	 * 
	 * @param t
	 *            the t
	 * @param layer
	 *            the layer
	 */
	public TemplePillar() {
		super(ia);
		if(new Random().nextBoolean()){
			this.draw = ib;
		}
		this.description = "A column";
		this.isTerrain=true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	@Override
	public void init() throws SlickException {
		ia = ModLoader.loadImage("core/res/bossroom/column.png");
		ib = ModLoader.loadImage("core/res/bossroom/columnsmaller.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#update(org.newdawn.slick.
	 * GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#updateBoardEffects(org.newdawn
	 * .slick.GameContainer, int)
	 */
	@Override
	public void updateBoardEffects(GameContainer gc) {
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
		return new WaitAction(this.getID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.StaticImageEntity#forceRender(org
	 * .newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		super.forceRender(g, x, y);
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
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus
	 * .deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard target, String[] attributes) {
		TemplePillar s = new TemplePillar();
		s.setLocation(
				target.getTileAt(
						Integer.parseInt(attributes[1]),
						Integer.parseInt(attributes[2])),
				Integer.parseInt(attributes[3]));
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		return getGenericSave();
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
		// TODO nothing doing
		return null;
	}

}
