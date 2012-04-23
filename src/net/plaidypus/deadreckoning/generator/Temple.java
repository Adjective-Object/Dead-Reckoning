package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Torch;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Temple.
 */
public class Temple extends Biome {

	/**
	 * Instantiates a new temple.
	 */
	public Temple() {
		requiredClasses = new Class[] { Torch.class, Stair.class };
	}

	/* (non-Javadoc)
	 * @see net.plaidypus.deadreckoning.generator.Biome#makeBoard(int, java.util.ArrayList)
	 */
	@Override
	public GameBoard makeBoard(int depth, ArrayList<Stair> linkedLevels)
			throws SlickException {
		GameBoard gb = new GameBoard(Utilities.randInt(10, 20),
				Utilities.randInt(10, 20));

		gb.depth = depth;

		for (int i = 0; i < gb.width; i++) {
			gb.getTileAt(i, 0).setTileFace(Tile.TILE_WALL_DOWN);
			gb.getTileAt(i, gb.height - 1).setTileFace(Tile.TILE_WALL_UP);
		}
		for (int i = 0; i < gb.height; i++) {
			gb.getTileAt(0, i).setTileFace(Tile.TILE_WALL_LEFT);
			gb.getTileAt(gb.width - 1, i).setTileFace(Tile.TILE_WALL_RIGHT);
		}

		gb.getTileAt(0, 0).setTileFace(Tile.TILE_WALL_UP_LEFT);
		gb.getTileAt(0, gb.getHeight() - 1).setTileFace(
				Tile.TILE_WALL_DOWN_LEFT);
		gb.getTileAt(gb.getWidth() - 1, 0).setTileFace(Tile.TILE_WALL_UP_RIGHT);
		gb.getTileAt(gb.getWidth() - 1, gb.getHeight() - 1).setTileFace(
				Tile.TILE_WALL_DOWN_RIGHT);

		new Torch().init();

		for (int x = 0; x < gb.getWidth(); x++) {
			for (int y = 0; y < gb.getHeight(); y++) {
				if (Utilities.randFloat() <= 0.01) {
					new Torch(gb.getTileAt(x, y), Tile.LAYER_PASSIVE_MAP,
							Utilities.randInt(2, 7));
				}
			}
		}

		int i = 0;
		while (i < linkedLevels.size()) {
			int x = Utilities.randInt(1, gb.getWidth()), y = Utilities.randInt(
					0, gb.getHeight());
			if (gb.getTileAt(x, y).isOpen(Tile.LAYER_PASSIVE_MAP)
					&& gb.getTileAt(x - 1, y).isOpen(Tile.LAYER_PASSIVE_MAP)) {
				gb.placeEntity(gb.getTileAt(x, y), linkedLevels.get(i),
						Tile.LAYER_PASSIVE_MAP);
				gb.placeEntity(gb.getTileAt(x - 1, y),
						new LandingPad(null, Tile.LAYER_PASSIVE_MAP,
								linkedLevels.get(i).targetFloor),
						Tile.LAYER_PASSIVE_MAP);
				i++;
			}
		}

		return gb;
	}

}
