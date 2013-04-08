package net.plaidypus.deadreckoning.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public abstract class NPC extends LivingEntity{
	
	public NPC(){
		super();
		this.makesActions=false;
	}
	
	public NPC(String parentMod, String entityFile,
			StatMaster statMaster, int allignment) throws SlickException {
		super();
		try {
			InputStream entityReader = ModLoader.getModpackLoader(parentMod)
					.getResourceAsStream(parentMod + "/" + entityFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entityReader));
			this.parentMod = parentMod;
			loadFromFile(reader);
			reader.close();
		} catch (IOException e) {
			throw new SlickException("Cannot load Entity "
					+ this.getClass().getSimpleName()
					+ " from livingentityfile");
		}

		currentAnimationID = LivingEntity.ANIMATION_STAND;
		this.statMaster = statMaster;

		this.entityFile = entityFile;

		setFacing(false);

		this.setAllignment(allignment);

		this.HP = this.statMaster.getMaxHP();
		this.MP = this.statMaster.getMaxMP();
	}
	
	@Override
	protected Action decideNextAction(GameContainer gc, int delta) {
		return null;
	}

	public abstract void resetSpeechTree();
	public abstract void advanceChat(int response);
	
	@Override
	public Action onInteract(Entity e) {
		this.resetSpeechTree();
		DeadReckoningGame.npcSpeechState.makeFrom(this);
		return new NPCTalkAction(this);
	}

	@Override
	public Entity makeFromString(GameBoard target, String[] attributes)
			throws SlickException {
		return null;
	}

	@Override
	public String saveToString() {
		return super.getGenericSave();
	}

	@Override
	public abstract void init() throws SlickException ;

}
