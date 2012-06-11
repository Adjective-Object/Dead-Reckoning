package core.biomes;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import core.entities.Door;
import core.entities.Torch;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.Monster;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.generator.RoomBasedBiome;
import net.plaidypus.deadreckoning.professions.StatMaster;

// TODO: Auto-generated Javadoc
/**
 * The Class Hemple.
 */
public class Hemple extends RoomBasedBiome {

	public Hemple() {
		this(20);
	}

	/**
	 * Instantiates a new hemple.
	 * 
	 * @param numRooms
	 *            the num rooms
	 */
	public Hemple(int numRooms) {
		super(numRooms);
	}

	/**
	 * Places walls on tiles that border Tile.TILE_NULL
	 * 
	 * @param g
	 *            the gameboard on which to dump walls
	 */
	public void placeWallsOnNullBorders(GameBoard g) {
		for (int x = 0; x < g.getWidth(); x++) {
			for (int y = 0; y < g.getHeight(); y++) {
				if (g.getTileAt(x, y).getTileFace() == Tile.TILE_NULL
						&& (g.getTileAt(
								Utilities.limitTo(x + 1, 0, g.getWidth()), y)
								.getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										Utilities.limitTo(x - 1, 0,
												g.getWidth()), y).getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										x,
										Utilities.limitTo(y - 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										x,
										Utilities.limitTo(y + 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										Utilities.limitTo(x - 1, 0,
												g.getWidth()),
										Utilities.limitTo(y + 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										Utilities.limitTo(x + 1, 0,
												g.getWidth()),
										Utilities.limitTo(y + 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL
								|| g.getTileAt(
										Utilities.limitTo(x - 1, 0,
												g.getWidth()),
										Utilities.limitTo(y - 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL || g
								.getTileAt(
										Utilities.limitTo(x + 1, 0,
												g.getWidth()),
										Utilities.limitTo(y - 1, 0,
												g.getHeight())).getTileFace() != Tile.TILE_NULL)) {
					g.getTileAt(x, y).blocking = true;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.generator.RoomBasedBiome#populateBoard(net
	 * .plaidypus.deadreckoning.board.GameBoard, java.util.ArrayList,
	 * java.util.ArrayList)
	 */
	public GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms,
			ArrayList<Stair> linkedLevels) {
		super.genericPopulation(target, rooms, linkedLevels);
		placeWallsOnNullBorders(target);

		for (int i = 0; i < rooms.size(); i++) {// TODO this still does not work
			int[] room = rooms.get(i);
			for (int x = room[0]; x < room[0] + room[2]; x++) {
				Tile t = target.getTileAt(x,
						Utilities.limitTo(room[1] - 1, 0, target.getHeight()));
				if (t.getX() != 0 && t.getTileFace() == Tile.TILE_SPECIAL) {
					target.placeEntity(t, new Door(t, Tile.LAYER_ACTIVE),
							Tile.LAYER_ACTIVE);
				}
				t = target.getTileAt(
						x,
						Utilities.limitTo(room[1] + room[3], 0,
								target.getHeight()));
				if (t.getX() != target.getWidth()
						&& t.getTileFace() == Tile.TILE_SPECIAL) {
					target.placeEntity(t, new Door(t, Tile.LAYER_ACTIVE),
							Tile.LAYER_ACTIVE);
				}
			}
			for (int y = room[1]; y < room[1] + room[3]; y++) {
				Tile t = target
						.getTileAt(
								Utilities.limitTo(room[0] - 1, 0,
										target.getWidth()), y);
				if (t.getY() != 0 && t.getTileFace() == Tile.TILE_SPECIAL) {
					target.placeEntity(t, new Door(t, Tile.LAYER_ACTIVE),
							Tile.LAYER_ACTIVE);
				}
				t = target.getTileAt(
						Utilities.limitTo(room[0] + room[2], 0,
								target.getWidth()), y);
				if (t.getY() != target.getHeight()
						&& t.getTileFace() == Tile.TILE_SPECIAL) {
					target.placeEntity(t, new Door(t, Tile.LAYER_ACTIVE),
							Tile.LAYER_ACTIVE);
				}
			}
		}

		for (int i = 0; i < rooms.size(); i++) {
			int s = 0;
			while (s < 1) {
				Tile t = target.getTileAt(
						(int) (rooms.get(i)[0] + rooms.get(i)[2]
								* Utilities.randFloat()),
						(int) (rooms.get(i)[1] + rooms.get(i)[3]
								* Utilities.randFloat()));
				if (t.isOpen(Tile.LAYER_ACTIVE)) {
					s++;
					target.placeEntity(t, new Monster(t, Tile.LAYER_ACTIVE,
							this.parentMod, "livingEntities/goblin.entity",
							new StatMaster(50, 50, 4, 4, 4, 4, 1),
							Entity.ALLIGN_HOSTILE), Tile.LAYER_ACTIVE);
				}
			}

			target.placeEntity(
					target.getTileAt(rooms.get(i)[0] + rooms.get(i)[2] / 2,
							rooms.get(i)[1] + rooms.get(i)[3] / 2),
					new Torch(null, Tile.LAYER_PASSIVE_MAP, Utilities.randInt(
							4, 6)), Tile.LAYER_PASSIVE_MAP);
		}

		return target;
	}

	@Override
	public void init() throws SlickException {
		// TODO loading tileimage and assigning the tile image of the Tile class
		// to the image upon setBoard.
	}

}
