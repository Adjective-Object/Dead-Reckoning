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

public class PoisonBladesSkill extends Skill{

	public static Image icon;
	
	public PoisonBladesSkill(){
		this(-1);
	}
	
	public PoisonBladesSkill(int sourceID){
		super(sourceID);
		this.setIcon(icon);
		this.setName("Poison Blades");
		this.setDescriptor(this.makeDescription());
		this.levelcap=4;
		this.levelReq=0;
		this.instant=true;
	}
	
	@Override
	public Action makeAction(Tile target) {
		this.cooldown=2;
		return new ApplyStatusAction(//TODO the status for Poison Blades
				sourceID,
				getSource(),
				new FocusedMindStatus(
						this.sourceID,
						calculatePoisonDamage(level),
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
		return "Taint your Blades with poison\nto deal a DOT poison effect on normal attacks\nfor the next "+calculateDuration(level)+"turns";
	}
	
	@Override
	public boolean canTargetTile(Tile t) {
		return false;
	}

	@Override
	public void highlightRange(GameBoard board) {}

	@Override
	public void init() throws SlickException {
		icon = ModLoader.loadImage("core/res/POISONBLADES.png");
	}

}
