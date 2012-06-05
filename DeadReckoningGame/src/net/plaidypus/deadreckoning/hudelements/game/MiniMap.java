package net.plaidypus.deadreckoning.hudelements.game;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.hudelements.HudElement;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MiniMap extends HudElement {

	int scale;
	GameBoard target;
	GameplayElement hookState;

	public MiniMap(int x, int y, int bindMethod, int scale, GameplayElement hookState) {
		super(x, y, bindMethod, false);
		this.target = hookState.getBoard();
		this.scale=scale;
		this.hookState=hookState;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		this.target = hookState.getBoard();
	}

	@Override
	public void makeFrom(Object o) {}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public int getWidth() {
		return target.getWidth()*scale;
	}

	@Override
	public int getHeight() {
		return target.getHeight()*scale;
	}

	private void renderTo(Graphics g, int xo, int yo) {
		g.setColor(Color.white);
		g.fillRect(xo, yo, target.getWidth() * scale + 1, target.getHeight()
				* scale + 1);
		g.setColor(Color.black);
		g.fillRect(xo + 1, yo + 1, target.getWidth() * scale - 1,
				target.getHeight() * scale - 1);

		for (int x = 0; x < target.getWidth(); x++) {
			for (int y = 0; y < target.getHeight(); y++) {
				if (target.getTileAt(x, y).explored) {

					if (!target.getTileAt(x, y).isOpen(Tile.LAYER_ACTIVE) && target.getTileAt(x, y).canBeSeen() && target.getTileAt(x, y).getEntity(Tile.LAYER_ACTIVE).isInteractive()) {
						g.setColor(Color.green);
					} else if ( (!target.getTileAt(x, y).isOpen(Tile.LAYER_PASSIVE_MAP) && target.getTileAt(x, y).getEntity(Tile.LAYER_PASSIVE_MAP).isTerrain()) ||
							(!target.getTileAt(x, y).isOpen(Tile.LAYER_ACTIVE) && target.getTileAt(x, y).getEntity(Tile.LAYER_ACTIVE).isTerrain())){ 
						g.setColor(Color.gray);
					} else if (target.getTileAt(x, y).getTileFace() != Tile.TILE_NULL) {
						g.setColor(Color.lightGray);
					} else {
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
		renderTo(g, getX() - target.getWidth() * scale, getY());

	}

}
