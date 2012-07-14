package core.statuses;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.skills.thief.KillerInstinctSkill;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.status.Status;

public class KillerInstinctStatus extends Status{
	
	public KillerInstinctStatus(){}
	
	public KillerInstinctStatus(int sourceID, int duration){
		super(sourceID,
				KillerInstinctSkill.icon,
				"This entity is stealthed",
				"Stealthed");
		this.name = "Killer Instinct";
		this.setDuration(duration);
	}
	
	@Override
	public void update(LivingEntity target, int delta) {}

	@Override
	public void onActionProduce(Action a) {	}
	
	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target){
		ArrayList<Action> e = super.advanceTurnEffects(target);
		if(this.getDuration()<=0){
			e.add(new AttackAction(sourceID, this.getSource().getLocation(),this.getSource().HP/2, false, false));//TODO raw damage, not magical damage
		}
		return e;
	}

	@Override
	public String saveToString() {
		return this.getGenericSave();
	}

	@Override
	public Status loadFromString(String[] args) {
		return new Stealthed(Integer.parseInt(args[1]), Integer.parseInt(args[3]));
	}

	@Override
	public void init() throws SlickException {
	}

	@Override
	public void onActionReceive(Action a) {
	}

	@Override
	public void render(Graphics g, int x, int y) {
	}

	@Override
	public void alterStatMaster(StatMaster statMaster) {
		statMaster.setDodge(100);
	}

}
