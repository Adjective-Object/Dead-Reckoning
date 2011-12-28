package net.plaidypus.deadreckoning.skills;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;

public class Fireball extends Skill{
	
	static SpriteSheet fireball;
	
	public Fireball(LivingEntity source) {
		super(source);
	}
	
	public static void init() throws SlickException{
		fireball = new SpriteSheet("res/FireBurst.png",DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
	}
	
	public Action makeAction(Tile target) {
		Animation an = new Animation(fireball,40);
		an.setLooping(false);
		return new AttackAction(source.getLocation(), target, 100, true, null,null,new AnimationEffect(target,an),null);
	}

	public boolean canTargetTile(Tile t) {
		if( !t.isOpen() && !(t.getX() == source.getX() && t.getY() == source.getY())){
			return  t.getEntity().isInteractive();
		}
		return false;
	}

	public void highlightRange(GameBoard board) {
		highlightRadial(board, 8);
	}

}
