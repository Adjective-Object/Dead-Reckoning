package core.skills.thief;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.statuses.DesperationStatus;
import core.statuses.FocusedMindStatus;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ApplyStatusAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;

public class DesperationSkill extends Skill{

	public static Image icon;
	
	public DesperationSkill(){
		this(-1);
	}
	
	public DesperationSkill(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Desperation");
		this.setDescriptor(makeDescriptor());
		this.levelcap=4;
		this.levelReq=0;
		this.instant=true;
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=2;
		return new ApplyStatusAction(
				sourceID,
				getSource(),
				new DesperationStatus(
						this.sourceID,
						calculateWepAtk(level),
						calculateSelfDamage(level),
						calculateDuration(level)
						)
				);
	}
	
	public int calculateWepAtk(int level){
		return 20+level*5;
	}
	
	public int calculateSelfDamage(int level){
		return 4*level;
	}
	
	public int calculateDuration(int level){
		return 5+level*2;
	}
	
	public String makeDescriptor(){
		return "With a last ditch effort,\n you gain weapon attack (+"+calculateWepAtk(level)+")\n for "+calculateDuration(level)+" turns";
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
		icon = ModLoader.loadImage("core/res/DESPERATION.png");
	}

}
