package net.plaidypus.deadreckoning.skills;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ActionSpawner;
import net.plaidypus.deadreckoning.actions.ApplyStatusAction;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.status.OnFire;

public class Fireball extends Skill{
	
	public static SpriteSheet fireball;
	
	private static Image image;
	
	public Fireball(){
		super(image);
		this.setName("Fireball");
		this.setDescriptor("sets target living entity\non fire, dealing damage\nover time");
	}
	
	public Fireball(LivingEntity source) {
		super(source);
		this.setName("fireball");
		this.setDescriptor("sets target living entity on fire, dealing damage over time");
	}
	
	public static void init() throws SlickException{
		fireball = new SpriteSheet("res/FireBurst.png",DeadReckoningGame.tileSize,DeadReckoningGame.tileSize);
		image = new Image("res/onFireIcon.png");
	}
	
	public Action makeAction(Tile target) {
		ArrayList<Action> toRet = new ArrayList<Action>(0);
		Animation an = new Animation(fireball,40);
		an.setLooping(false);
		toRet.add(new ApplyStatusAction(source, target, Tile.LAYER_ACTIVE, new OnFire(this.source, 2, 2*this.level)));
		toRet.add(new AttackAction(source, target, 2, true, true, null,null,new AnimationEffect(target,an),null));
		return new ActionSpawner(source, toRet);
	}

	public boolean canTargetTile(Tile t) {
		if( !t.isOpen(Tile.LAYER_ACTIVE) && !(t.getX() == source.getX() && t.getY() == source.getY())){
			return  t.getEntity(Tile.LAYER_ACTIVE).isInteractive();
		}
		return false;
	}

	public void highlightRange(GameBoard board) {
		highlightRadial(board, 2);
	}

}
