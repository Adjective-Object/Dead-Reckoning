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
import net.plaidypus.deadreckoning.grideffects.AnimationEffect;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;
import net.plaidypus.deadreckoning.utilities.Utilities;

public class DeadlyDash extends Skill{

	public static Image icon;
	public static SpriteSheet smokeBomb;
	
	
	public DeadlyDash(){
		this(-1);
	}
	
	public DeadlyDash(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Deadly Dash");
		this.setDescriptor(makeDescriptor());
		this.levelcap=4;
		this.levelReq=0;
		this.instant=true; 
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=14;
		ArrayList<Action> toRet = new ArrayList<Action>(0);
		
		Tile[] targetTiles = new Tile[calculateRange(this.level)];
		
		
		
		return new ActionSpawner(sourceID,toRet);
	}
	
	public int calculateDamage(int level){
		return (int)(getSource().getStatMaster().getDEX()*0.8F);
	}
	
	public int calculateRange(int level){
		return 5+level*2;
	}
	
	private String makeDescriptor(){
		return "Dashes along a line to distant square\nup to "+this.calculateRange(level)+" squares away,\ndamaging enemies along the path.";
	}
	
	@Override
	public void setLevel(int level){
		super.setLevel(level);
		this.setDescriptor(makeDescriptor());		
	}
	
	@Override
	public boolean canTargetTile(Tile t) {
		return true;
	}

	@Override
	public void highlightRange(GameBoard board) {
		for(int i=-calculateRange(this.level); i<calculateRange(this.level); i++){
			Tile target = board.getTileAt(getSource().getX(), Utilities.limitTo(getSource().getY()+i,0,board.getHeight()));
			if(target.blocking){
				break;
			}
			else{
				target.setHighlighted(Tile.HIGHLIGHT_CONFIRM);
			}
		}
		
		for(int i=-calculateRange(this.level); i<calculateRange(this.level); i++){
			Tile target = board.getTileAt(Utilities.limitTo(getSource().getX()+i,0,board.getWidth()), getSource().getY());
			if(target.blocking){
				break;
			}
			else{
				target.setHighlighted(Tile.HIGHLIGHT_CONFIRM);
			}
		}
	}

	@Override
	public void init() throws SlickException {
		icon = ModLoader.loadImage("core/res/SMOKEBOMB.png");
		smokeBomb = new SpriteSheet(ModLoader.loadImage("core/res/smokeBombExplosion.png"),32,32);
	}

}
