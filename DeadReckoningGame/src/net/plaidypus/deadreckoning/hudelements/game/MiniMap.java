package net.plaidypus.deadreckoning.hudelements.game;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MiniMap extends HudElement {

	int scale, width, height;
	GameplayElement hookState;

	public MiniMap(int x, int y, int bindMethod, int scale, int width,
			int height, GameplayElement hookState) {
		super(x, y, bindMethod, false);
		this.scale = scale;
		this.width = width;
		this.height = height;
		this.hookState = hookState;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	@Override
	public void makeFrom(Object o) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return this.getWidth();
	}

	@Override
	public int getHeight() {
		return this.getHeight();
	}

	private void renderTo(Graphics g, int xo, int yo) {
		GameBoard target = hookState.getBoard();
		Player p = hookState.player;

		g.setColor(Color.white);
		g.drawRect(xo, yo, width * scale + 1, height * scale + 1);
		g.setColor(Color.black);
		g.fillRect(xo + 1, yo + 1, width * scale, height * scale);

		int px = p.getX(), py = p.getY();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int ax = px - width / 2 + x, ay = py - height / 2 + y;

				if (ax > 0 && ax < target.getWidth() && ay > 0
						&& ay < target.getHeight()
						&& target.getTileAt(ax, ay).explored) {
					Tile til = target.getTileAt(ax, ay);

					if (!til.isOpen(Tile.LAYER_ACTIVE) && til.canBeSeen()
							&& til.getEntity(Tile.LAYER_ACTIVE).isInteractive()) {
						if (til.getEntity(Tile.LAYER_ACTIVE).allignmnet == Entity.ALLIGN_HOSTILE) {
							g.setColor(Color.green);
						} else if (til.getEntity(Tile.LAYER_ACTIVE) == p) {
							g.setColor(Color.blue);
						} else {
							g.setColor(Color.green);
						}
					} else if (til.blocking){
						g.setColor(Color.lightGray);
					}else if ((!til.isOpen(Tile.LAYER_PASSIVE_MAP) && til
					
									.getEntity(Tile.LAYER_PASSIVE_MAP)
									.isTerrain())
							|| (!til.isOpen(Tile.LAYER_ACTIVE) && til
									.getEntity(Tile.LAYER_ACTIVE).isTerrain())) {
						g.setColor(Color.gray);
					} else{
						g.setColor(Color.white);
					}
					g.fillRect((xo + x * scale) + 1, (yo + y * scale) + 1,
							scale, scale);
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderTo(g, getX() - width * scale, getY());

	}

}
