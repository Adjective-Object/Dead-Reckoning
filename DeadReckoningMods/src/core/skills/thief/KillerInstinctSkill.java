package core.skills.thief;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.statuses.KillerInstinctStatus;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ApplyStatusAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;

public class KillerInstinctSkill extends Skill{

	public static Image icon;
	
	public KillerInstinctSkill(){
		this(-1);
	}
	
	public KillerInstinctSkill(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Killer Instinct");
		this.setDescriptor(this.makeDescription());
		this.levelcap=1;
		this.levelReq=0;
		this.instant=true;
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=2;
		return new ApplyStatusAction(
				sourceID,//TODO the status for killer instinct
				getSource(),
				new KillerInstinctStatus(
						this.sourceID,
						calculateDuration(level)
						)
				);
	}
	
	public int calculatePoisonDamage(int level){
		return level*5;
	}
	
	public int calculateDuration(int level){
		return 5+level*2;
	}
	
	@Override
	public void setLevel(int level){
		super.setLevel(level);
		this.setDescriptor(makeDescription());		
	}
	
	public String makeDescription(){
		return "Dodge all incoming attacks for "+calculateDuration(level)+"turns. At the end of the duration, lose 1/2 of your remaining life.";
	}
	
	@Override
	public boolean canTargetTile(Tile t) {
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {}

	@Override
	public void init() throws SlickException {
		icon = ModLoader.loadImage("core/res/KILLERINSTINCT.png");
	}

}
