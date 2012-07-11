package core.statuses;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.skills.thief.DesperationSkill;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.AttackAction;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.status.Status;

public class DesperationStatus extends Status{
	
	int selfDamage, weaponAttack;
	
	public DesperationStatus(){}
	
	public DesperationStatus(int sourceID, int weaponAttack,
			int selfDamage, int duration) {
		super(sourceID, DesperationSkill.icon,
				 "You are desperate, gaining weapon attack (+"+weaponAttack+") and taking "+selfDamage+" damage each turn", "Desperation");
		this.selfDamage = selfDamage;
		this.setDuration(duration);
		this.weaponAttack=weaponAttack;
		this.name="Desperate";
	}

	@Override
	public void update(LivingEntity target, int delta) {
	}

	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target) {
		ArrayList<Action> actions = super.advanceTurnEffects(target);
		actions.add(new AttackAction(sourceID, getSource().getLocation(), selfDamage, false, false));
		return actions;
	}
	
	@Override
	public void onActionProduce(Action a) {	}

	@Override
	public void onActionReceive(Action a) {	}

	@Override
	public void render(Graphics g, int x, int y) {	}

	@Override
	public void alterStatMaster(StatMaster statMaster) {
		statMaster.editAtt(this.weaponAttack);
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status loadFromString(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
