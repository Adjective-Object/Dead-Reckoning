package core.skills.thief;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.statuses.Stealthed;

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

public class SmokeBomb extends Skill{

	public static Image icon;
	public static SpriteSheet smokeBomb;
	
	
	public SmokeBomb(){
		this(-1);
	}
	
	public SmokeBomb(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Smoke Bomb");
		this.setDescriptor(makeDescriptor());
		this.levelcap=4;
		this.levelReq=0;
		this.instant=true; 
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=14;
		ArrayList<Action> toRet = new ArrayList<Action>(0);
		
		toRet.add( new ApplyStatusAction(
				sourceID,
				getSource(),
				new Stealthed(
						this.sourceID,
						calculateDuration(level)
						)
				));
		
		Tile[] targetTiles = {
				getSource().getLocation().getRelativeTo(-1, 0),
				getSource().getLocation().getRelativeTo(1, 0),
				getSource().getLocation().getRelativeTo(0, 1),
				getSource().getLocation().getRelativeTo(0, -1)
		};
		
		for(int i=0; i<targetTiles.length; i++){
			if(targetTiles[i].isOpen(Tile.LAYER_ACTIVE)){
				AttackAction a = new AttackAction(sourceID, (LivingEntity)targetTiles[i].getEntity(Tile.LAYER_ACTIVE), calculateDamage(this.level), false);
				a.setGridEffects(null, null, new AnimationEffect(targetTiles[i],new Animation(smokeBomb,60)), null);
				toRet.add(a);
			}
		}
		return new ActionSpawner(sourceID,toRet);
	}
	
	public int calculateDamage(int level){
		return (int)(getSource().getStatMaster().getDEX()*0.8F);
	}
	
	public int calculateDuration(int level){
		return 5+level*2;
	}
	
	private String makeDescriptor(){
		return "Throws a smoke bomb, stealthing you, and dealing magic damage to entities around you.";
	}
	
	@Override
	public void setLevel(int level){
		super.setLevel(level);
		this.setDescriptor(makeDescriptor());		
	}
	
	@Override
	public boolean canTargetTile(Tile t) {
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {}

	@Override
	public void init() throws SlickException {
		icon = ModLoader.loadImage("core/res/SMOKEBOMB.png");
		smokeBomb = new SpriteSheet(ModLoader.loadImage("core/res/smokeBombExplosion.png"),32,32);
	}

}
