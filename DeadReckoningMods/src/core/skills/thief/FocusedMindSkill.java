package core.skills.thief;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.statuses.FocusedMindStatus;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ApplyStatusAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.Skill;

public class FocusedMindSkill extends Skill{

	public static Image icon;
	
	public FocusedMindSkill(){
		this(-1);
	}
	
	public FocusedMindSkill(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Focused Mind");
		this.setDescriptor("Focus your mind to gain additional critical strike chance \r\n(+"+calculateCritChance(level)+") for "+calculateDuration(level)+" turns");
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
				new FocusedMindStatus(
						this.sourceID,
						calculateCritChance(level),
						calculateDuration(level)
						)
				);
	}
	
	public int calculateCritChance(int level){
		return 20+level*5;
	}
	
	public int calculateDuration(int level){
		return 5+level*2;
	}
	
	@Override
	public void setLevel(int level){
		super.setLevel(level);
		this.setDescriptor("Focus your mind to gain\nadditional critical strike chance (+"+calculateCritChance(level)+")\n for "+calculateDuration(level)+" turns");		
	}
	
	@Override
	public boolean canTargetTile(Tile t) {
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {}

	@Override
	public void init() throws SlickException {
		icon = ModLoader.loadImage("core/res/FOCUSEDMIND.png");
	}

}
