package core.skills.thief;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.grideffects.AnimatedProjectileEffect;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.OffensiveSkill;

public class DaggerToss extends OffensiveSkill{

	static SpriteSheet spinningDagger;
	static Image daggerIcon;
	
	public DaggerToss(){
		this(-1);
	}
	
	public DaggerToss(int sourceID){
		super(sourceID);
		this.setIcon(daggerIcon);
		this.levelcap=4;
		this.levelReq=0;
		this.setName("Dagger Toss");
		this.setDescriptor("Throws a Dagger, dealing\ndamage equal to normal\nattack damage");
	}
	
	@Override
	public Action makeAction(Tile target) {
		target.getParent().addEffectOver(
				new AnimatedProjectileEffect(new Animation(spinningDagger,20),this.getSource().getLocation(),target,200)
				);
		return new AttackAction(this.sourceID,(LivingEntity)target.getEntity(Tile.LAYER_ACTIVE),
				this.getSource().getStatMaster().getPhysicalDamageFrom(),true,false,200);
	}

	@Override
	public void highlightRange(GameBoard board) {
		this.highlightRadial(board, 1+this.level);
	}

	@Override
	public void init() throws SlickException {
		spinningDagger=new SpriteSheet(ModLoader.loadImage("core/res/daggerProjectile.png"),12,12);
		daggerIcon=ModLoader.loadImage("core/res/DAGGERTOSS.png");
	}

}
