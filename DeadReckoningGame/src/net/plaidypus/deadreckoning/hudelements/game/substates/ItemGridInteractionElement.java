package net.plaidypus.deadreckoning.hudelements.game.substates;

import net.plaidypus.deadreckoning.entities.InteractiveEntity;
import net.plaidypus.deadreckoning.hudelements.HudElement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ItemGridInteractionElement extends HudElement {

	ItemGridElement a, b;

	public ItemGridInteractionElement(ItemGridElement a, ItemGridElement b) {
		super(0, 0, HudElement.TOP_LEFT, false);
		this.a=a;
		this.b=b;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			if (a.hasFocus && a.selector < a.contents.size()) {
				b.contents.add(a.contents.remove(a.selector));
			} else if (b.hasFocus && b.selector < b.contents.size()) {
				a.contents.add(b.contents.remove(b.selector));
			}
		}
		if (gc.getInput().isKeyPressed(Input.KEY_A)) {
			if (a.hasFocus) {
				a.contents.addAll(b.contents);
				b.contents.clear();
			} else if (b.hasFocus) {
				b.contents.addAll(a.contents);
				a.contents.clear();
			}
		}
		InteractiveEntity.collapseItemArray(a.contents);
		InteractiveEntity.collapseItemArray(b.contents);
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
	}
}
