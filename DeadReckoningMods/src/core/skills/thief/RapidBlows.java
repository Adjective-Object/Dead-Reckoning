package core.skills.thief;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.ActionSpawner;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.skills.OffensiveSkill;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RapidBlows extends OffensiveSkill{

	static Image image;
	
	public RapidBlows(){
		this(-1);
	}
	
	public RapidBlows(int statusID){
		super(statusID);
		this.levelcap=4;
		this.levelReq=4;
		this.setIcon(image);
		this.setName("Rapid Blows");
		this.setDescriptor("Attacks a random number of times,\ndealing "+(80+this.level*5)+"% damage each hit");
	}
	
	@Override
	public Action makeAction(Tile target) {
		ArrayList<Action> spawnableActions = new ArrayList<Action>(0);
		
		spawnableActions.add(
				new AttackAction(
						this.sourceID,(LivingEntity)target.getEntity(Tile.LAYER_ACTIVE),
						(int)(this.getSource().getStatMaster().getPhysicalDamageFrom()*.8),
						true,true,0)
				);

		int a = Utilities.randInt(1,this.level+4);
		for(int i=0; i<a;i++){
			spawnableActions.add(
					new AttackAction(
							this.sourceID,(LivingEntity)target.getEntity(Tile.LAYER_ACTIVE),
							(int)(this.getSource().getStatMaster().getPhysicalDamageFrom()*.8),
							true,false,(i+1)*200)
					);
		}
		return new ActionSpawner(sourceID, spawnableActions);
	}

	@Override
	public void highlightRange(GameBoard board) {
		this.highlightRadial(board, 1);
	}

	@Override
	public void init() throws SlickException {
		RapidBlows.image = ModLoader.loadImage("core/res/RAPIDBLOWS.png");
	}
	
	@Override
	public void setLevel(int level){
		super.setLevel(level);
		this.setDescriptor("Attacks a random number of times,\ndealing "+80+this.level*5+"% damage each hit");
	}

}
