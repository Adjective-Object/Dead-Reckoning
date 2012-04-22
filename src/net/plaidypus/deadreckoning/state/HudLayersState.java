package net.plaidypus.deadreckoning.state;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HudLayersState extends BasicGameState {

	int stateID;
	DeadReckoningGame parent;

	ArrayList<HudElement> HudElements;

	public HudLayersState(int stateID, ArrayList<HudElement> elements) {
		this.stateID = stateID;
		this.HudElements = elements;
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).setParent(this);
		}
	}

	public HudLayersState(int stateID, HudElement[] elementsarr) {
		this.stateID = stateID;
		HudElements = new ArrayList<HudElement>(0);
		System.out.println(stateID + " " + elementsarr.length);
		for (int i = 0; i < elementsarr.length; i++) {
			HudElements.add(elementsarr[i]);
			HudElements.get(i).setParent(this);
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		for (int i = 0; i < this.HudElements.size(); i++) {
			HudElements.get(i).init(container, game);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).update(container, game, delta);
		}
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		if (gc.getInput().isKeyDown(Input.KEY_0)) {
			g.setColor(Color.green);
		}
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).render(gc, game, g);
		}
		for (int i = 0; i < HudElements.size(); i++) {
			HudElements.get(i).renderMouseOver(gc, game, g);
		}
	}

	public void makeFrom(Object[] objects) {
		System.out.println(stateID);
		for (int i = 0; i < objects.length; i++) {
			this.HudElements.get(i % HudElements.size()).makeFrom(objects[i]);
		}
	}

	public int getID() {
		return this.stateID;
	}

	public ArrayList<HudElement> getElements() {
		return HudElements;
	}

	public HudElement getElement(int index) {
		return HudElements.get(index);
	}

	public DeadReckoningGame getParent() {
		return this.parent;
	}

}
