package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.particles.GridEffect;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GameBoard {

	public ArrayList<Entity> ingameEntities;

	Tile[][] board;
	Tile primaryHighlight;
	int width, height;

	ArrayList<GridEffect> overEffects, underEffects;

	static final Color primaryHighlightColor = new Color(255, 75, 23);

	public GameBoard(int width, int height) {
		board = new Tile[width][height];
		this.width = width;
		this.height = height;
	}

	public void placeEntity(Tile t, Entity e) {
		placeEntity(t.getX(), t.getY(), e);
	}

	public void placeEntity(int x, int y, Entity e) {
		board[x][y].setEntity(e);
		ingameEntities.add(e);
	}

	public void clearTile(int x, int y) {
		ingameEntities.remove(board[x][y].getEntity());
		board[x][y].disconnectEntity();
	}

	public void moveEntity(Tile source, Tile target) {
		target.setEntity(source.getEntity());
		source.disconnectEntity();
	}

	public void init() throws SlickException {
		ingameEntities = new ArrayList<Entity>(0);
		overEffects = new ArrayList<GridEffect>(0);
		underEffects = new ArrayList<GridEffect>(0);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = new Tile(this, x, y);
			}
		}
	}

	public Tile getTileAt(int x, int y) {
		return board[x][y];
	}

	public void render(Graphics g, float xoff, float yoff) {
		for (int x = 0; x < 25; x++) {
			for (int y = 0; y < 25; y++) {
				if (board[x][y].visible) {
					board[x][y].render(g,
							x + xoff / DeadReckoningGame.tileSize, y + yoff
									/ DeadReckoningGame.tileSize);
				}
			}
		}

		if (primaryHighlight != null) {
			g.setColor(primaryHighlightColor);
			g.drawRect(primaryHighlight.getX() * DeadReckoningGame.tileSize
					+ (int) xoff, primaryHighlight.getY()
					* DeadReckoningGame.tileSize + (int) yoff,
					DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
		}

		for (int i = 0; i < underEffects.size(); i++) {
			underEffects.get(i).render(g, xoff, yoff);
		}

		for (int x = 0; x < 25; x++) {
			for (int y = 0; y < 25; y++) {
				if (!board[x][y].isOpen() && board[x][y].lightLevel > 1) {
					board[x][y].getEntity().render(g,
							x + xoff / DeadReckoningGame.tileSize,
							y + yoff / DeadReckoningGame.tileSize);
				}
			}
		}

		for (int i = 0; i < overEffects.size(); i++) {
			overEffects.get(i).render(g, xoff, yoff);
		}
	}

	public void updateSelctor(Input i, float xOff, float yOff) {

		if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			primaryHighlight = board[Utilities.limitTo(
					(i.getMouseX() - (int) xOff) / DeadReckoningGame.tileSize,
					0, this.getWidth())][Utilities.limitTo(
					(i.getMouseY() - (int) yOff) / DeadReckoningGame.tileSize,
					0, this.getHeight())];
		}

		if (primaryHighlight != null) {
			if (i.isKeyPressed(Input.KEY_LEFT)) {
				primaryHighlight = primaryHighlight.getToLeft();
			}
			if (i.isKeyPressed(Input.KEY_RIGHT)) {
				primaryHighlight = primaryHighlight.getToRight();
			}
			if (i.isKeyPressed(Input.KEY_UP)) {
				primaryHighlight = primaryHighlight.getToUp();
			}
			if (i.isKeyPressed(Input.KEY_DOWN)) {
				primaryHighlight = primaryHighlight.getToDown();
			}
		}
	}

	public void updateAllTiles(GameContainer gc, int delta) {

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (!board[x][y].isOpen()) {
					board[x][y].getEntity().update(gc, delta);
				}
			}
		}

		for (int i = 0; i < underEffects.size(); i++) {
			underEffects.get(i).update(delta);
			if (underEffects.get(i).kill) {
				underEffects.remove(i);
				i -= 1;
			}
		}

		for (int i = 0; i < overEffects.size(); i++) {
			overEffects.get(i).update(delta);
			if (overEffects.get(i).kill) {
				overEffects.remove(i);
				i -= 1;
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void addEffectUnder(GridEffect g) {
		this.underEffects.add(g);
	}

	public void addEffectOver(GridEffect g) {
		this.overEffects.add(g);
	}

	public void highlightSquare(int x, int y) {
		board[x][y].setHighlighted(1);
	}

	public boolean isTileHighlighted(int x, int y) {
		return board[x][y].getHighlighted() == 1;
	}

	public void setPrimairyHighlight(int x, int y) {
		this.primaryHighlight = board[x][y];
	}

	public void setPrimairyHighlight(Tile t) {
		this.primaryHighlight = t;
	}

	public Tile getPrimairyHighlight() {
		return primaryHighlight;
	}

	public void clearPrimaryHighlight() {
		this.primaryHighlight = null;
	}

	public void clearHighlightedSquares() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[x][y].setHighlighted(0);
			}
		}
	}

	public void revealInRadius(Tile location, int VIS) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				int dist = (int) Utilities.getDistance(location, board[x][y]);
				if (dist <= VIS) {
					int level = Utilities.limitTo(VIS + 1 - dist, 1,
							Tile.numLightLevels);
					if (level > board[x][y].lightLevel) {
						board[x][y].lightLevel = level;
						board[x][y].explored = true;
					}
				} else if (board[x][y].explored && board[x][y].lightLevel == 0) {
					board[x][y].lightLevel = 1;
				}
			}
		}
	}

	public void revealFromEntity(LivingEntity e) {
		revealInRadius(e.getLocation(), e.VIS);
	}

	public void HideAll() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y].lightLevel = 0;
				// aboard[x][y].visible=false; //TODO visibility
			}
		}
	}

	public boolean isIdle() {
		for (int i = 0; i < this.ingameEntities.size(); i++) {
			if (!ingameEntities.get(i).isIdle()) {
				return false;
			}
		}
		return true;
	}

	public boolean isLineOfSight(Tile a, Tile b) {

		return true;
	}

}
