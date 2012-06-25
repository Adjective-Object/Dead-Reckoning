package core.skills;

import java.io.IOException;
import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ActionSpawner;
import net.plaidypus.deadreckoning.actions.ApplyStatusAction;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.status.OnFire;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.TextureLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class Fireball.
 */
public class Fireball extends Skill {

	/** The fireball. */
	public static SpriteSheet fireball;

	/** The image. */
	private static Image image;

	/**
	 * Instantiates a new fireball.
	 */
	public Fireball() {
		super(image);
		this.setName("Fireball");
		this.setDescriptor("sets target living entity\non fire, dealing damage\nover time");
	}

	/**
	 * Instantiates a new fireball.
	 * 
	 * @param source
	 *            the source
	 */
	public Fireball(LivingEntity source) {
		super(source);
		this.setName("fireball");
		this.setDescriptor("sets target living entity on fire, dealing damage over time");
	}

	/**
	 * Inits the.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	public void init() throws SlickException {
		Fireball.fireball = new SpriteSheet(ModLoader.loadImage("core/res/FireBurst.png"),
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
		Fireball.image = ModLoader.loadImage("core/res/onFireIcon.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	public Action makeAction(Tile target) {
		ArrayList<Action> toRet = new ArrayList<Action>(0);
		Animation an = new Animation(fireball, 40);
		an.setLooping(false);
		toRet.add(new ApplyStatusAction(source, target, Tile.LAYER_ACTIVE,
				new OnFire(this.source, 2, 2 * this.level)));
		toRet.add(new AttackAction(source, target, 2, true, true, null, null,
				new AnimationEffect(target, an), null));
		this.setCooldown(10);
		return new ActionSpawner(source, toRet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#updateSkill()
	 */
	public void updateSkill() {
		super.updateSkill();
		this.levelcap = (this.source.getStatMaster().getLevel() - 1) * 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#canTargetTile(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	public boolean canTargetTile(Tile t) {
		if (!t.isOpen(Tile.LAYER_ACTIVE)
				&& !(t.getX() == source.getX() && t.getY() == source.getY())) {
			return t.getEntity(Tile.LAYER_ACTIVE).isInteractive();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#canBeCast()
	 */
	public boolean canBeCast() {
		return super.canBeCast() && getLevel() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.skills.Skill#highlightRange(net.plaidypus
	 * .deadreckoning.board.GameBoard)
	 */
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 2);
	}

}
