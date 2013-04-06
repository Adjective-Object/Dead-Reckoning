package core.statuses;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.status.Status;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stealthed extends Status{
	
	static Image ghostIcon;
	
	public Stealthed(){}
	
	public Stealthed(int sourceID, int duration){
		super(sourceID,
				ghostIcon,
				"This entity is stealthed",
				"Stealthed");
		this.setDuration(duration);
		this.name="Stealthed";
	}
	
	@Override
	public void update(LivingEntity target, int delta) {}

	@Override
	public void onActionProduce(Action a) {	}
	
	@Override
	public ArrayList<Action> advanceTurnEffects(LivingEntity target){
		this.getSource().setStealthed(true);
		return super.advanceTurnEffects(target);
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
		Stealthed.ghostIcon=ModLoader.loadImage("core/res/STEALTHED.png");
	}

	@Override
	public void onActionReceive(Action a) {
	}

	@Override
	public void render(Graphics g, int x, int y) {
	}

	@Override
	public void alterStatMaster(StatMaster statMaster) {
	}

}
