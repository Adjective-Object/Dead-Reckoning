package core.skills;

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
import net.plaidypus.deadreckoning.skills.OffensiveSkill;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.statuses.OnFire;

// TODO: Auto-generated Javadoc
/**
 * The Class Fireball.
 */
public class Fireball extends OffensiveSkill {

	/** The fireball. */
	static SpriteSheet fireball;
	static Image image;
	
	public Fireball(){
		this(-1);
	}
	 
	public Fireball(int sourceID) {
		super(sourceID);
		this.setIcon(image);
		this.setName("fireball");
		this.setDescriptor("sets target living entity on fire, dealing damage over time");
	}

	/**
	 * Inits the.
	 * 
	 * @throws SlickException
	 *             the slick exception
	 */
	@Override
	public void init() throws SlickException {
		Fireball.fireball = new SpriteSheet(ModLoader.loadImage("core/res/FIREBURST.png"),
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
		Fireball.image = ModLoader.loadImage("core/res/ONFIREICON.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#makeAction(net.plaidypus.
	 * deadreckoning.board.Tile)
	 */
	@Override
	public Action makeAction(Tile target) {
		ArrayList<Action> toRet = new ArrayList<Action>(0);
		Animation an = new Animation(fireball, 40);
		an.setLooping(false);
		toRet.add(new ApplyStatusAction(sourceID, target.getEntity(Tile.LAYER_ACTIVE),
				new OnFire(this.sourceID, 2, 2 * this.level)));
		AttackAction attack = new AttackAction(sourceID, (LivingEntity)target.getEntity(Tile.LAYER_ACTIVE), 2, true, true, 300);
		attack.setGridEffects( null, null, new AnimationEffect(target, an), null);
		toRet.add(attack);
		
		this.setCooldown(10);
		return new ActionSpawner(sourceID, toRet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#updateSkill()
	 */
	@Override
	public void updateSkill() {
		super.updateSkill();
		this.levelcap = (this.getSource().getStatMaster().getLevel() - 1) * 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.skills.Skill#canBeCast()
	 */
	@Override
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
	@Override
	public void highlightRange(GameBoard board) {
		highlightRadial(board, 2);
	}

}
