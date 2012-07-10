package core.skills;

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

	static Image icon;
	
	public FocusedMindSkill(){
		this(-1);
	}
	
	public FocusedMindSkill(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Focused Mind");
		this.setDescriptor("Focus your mind to gain additional critical strike chance");
		this.levelcap=4;
		this.levelReq=0;
		this.instant=true;
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=2;
		return new ApplyStatusAction(
				sourceID,
				getSource().getLocation(),
				getSource().getLayer(),
				new FocusedMindStatus(
						this.sourceID,
						20+this.level*5,
						5+this.level*2
						)
				);
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
