package core.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.LootAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.grideffects.FadeoutEffect;
import net.plaidypus.deadreckoning.items.EtcDrop;
import net.plaidypus.deadreckoning.items.Item;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Chest.
 */
public class Chest extends InteractiveEntity {

	/** The chest. */
	static Image chest;

	// Exists only for the purpose of referencing methods that should be static,
	// but need to be abstract, because fuck Java
	/**
	 * Instantiates a new chest.
	 */
	public Chest() {
	}

	/**
	 * Instantiates a new chest.
	 *
	 * @param t the t
	 * @param layer the layer
	 * @param items the items
	 */
	public Chest(Tile t, int layer, ArrayList<Item> items) {
		super(t, layer);
		this.setLocation(t);
		this.setVisible(true);
		this.inventory.addAll(items);
		this.setName("a chest");
		this.description = "A creaky old chest.";
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#init()
	 */
	public void init() throws SlickException {
		Chest.chest = new Image("res/chest.png");
		System.out.println(chest);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer gc, int delta) {
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#updateBoardEffects(org.newdawn.slick.GameContainer, int)
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
		if (this.inventory.isEmpty()) {
			this.kill();
		}
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#chooseAction(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.InteractiveEntity#forceRender(org.newdawn.slick.Graphics, float, float)
	 */
	@Override
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(chest, x, y);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#makeFromString(net.plaidypus.deadreckoning.board.GameBoard, java.lang.String[])
	 */
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		ArrayList<Item> content = new ArrayList<Item>(0);
		for (int i = 4; i < toload.length; i++) {
			content.add(new EtcDrop(Integer.parseInt(toload[i]), 1));// TODO
																		// equip
																		// parsing
		}
		return new Chest(g.getTileAt(Integer.parseInt(toload[1]),
				Integer.parseInt(toload[2])), Integer.parseInt(toload[3]),
				content);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#saveToString()
	 */
	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#advanceTurn()
	 */
	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#isInteractive()
	 */
	@Override
	public boolean isInteractive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onDeath()
	 */
	@Override
	public void onDeath() {
		this.getParent().addEffectOver(
				new FadeoutEffect(this.getLocation(), chest));
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.entities.Entity#onInteract(net.plaidypus.deadreckoning.entities.Entity)
	 */
	@Override
	public Action onInteract(Entity observer) {
		return new LootAction(observer, this.getLocation(), this.getLayer());
	}

}
