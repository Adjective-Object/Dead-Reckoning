package core.statuses;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.status.Status;

public class FocusedMindStatus extends Status{

	static Image statusIcon;
	int critChance;
	
	public FocusedMindStatus(){
		
	}
	
	public FocusedMindStatus(Integer sourceID, int critChance, int duration) {
		super(sourceID, statusIcon, "The mind is focused,\ngiving increased\ncritical strike chance (+"+Integer.toString(critChance)+"%)", "FocusedMind");
		this.name="Focused Mind";
		this.critChance=critChance;
		this.duration = duration;
	}

	@Override
	public void update(LivingEntity target, int delta) {
	}

	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target) {
		this.duration--;
		return new ArrayList<Action>(0);
	}

	@Override
	public void onActionProduce(Action a) {
	}

	@Override
	public void onActionReceive(Action a) {
	}

	@Override
	public void render(Graphics g, int x, int y) {
		
	}

	@Override
	public boolean isFinished() {
		return this.getDuration()<=0;
	}

	@Override
	public void alterStatMaster(StatMaster statMaster) {
		statMaster.setCrit(statMaster.getCrit() + this.critChance);
	}

	@Override
	public String saveToString() {
		return super.getGenericSave()+"-"+this.critChance;
	}

	@Override
	public Status loadFromString(String[] args) {
		FocusedMindStatus f = new FocusedMindStatus(Integer.parseInt(args[1]), Integer.parseInt(args[4]), Integer.parseInt(args[3]));
		f.stacks=Integer.parseInt(args[2]);
		return f;
	}

	@Override
	public void init() throws SlickException {
		statusIcon = ModLoader.loadImage("core/res/FOCUSEDMIND.png");
	}

}
