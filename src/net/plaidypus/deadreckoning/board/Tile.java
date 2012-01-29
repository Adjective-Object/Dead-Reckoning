package net.plaidypus.deadreckoning.board;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.entities.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/*

 Griffin Brodman & Jeffrey Tao
 and Maaaaaaxxxxxxxx Huang-Hobbs.
 Created: 11/4/2011

 */

public class Tile {
	private Entity containedEntity;
	private GameBoard parent;
	public boolean isEmpty;
	private int x, y;
	private int tileFace;
	public int highlighted;

	public static final int numLightLevels = 5;
	public boolean explored;
	public boolean visibility;
	public float lightLevel;

	static final Color[] highlightColors = new Color[] { new Color(0, 0, 0, 0),
			new Color(255, 75, 23, 100), new Color(252, 125, 73, 100) };

	static final float brightness = (float) (1.0);
	public static final int HIGHLIGHT_NULL = 0, HIGHLIGHT_CONFIRM = 1,
			HIGHLIGHT_DENY = 2;

	static SpriteSheet tileTextures;

	public static final int TILE_EMPTY = 4, TILE_WALL = 9;

	Tile(GameBoard parent, int x, int y, int tileFace) throws SlickException {
		this.parent = parent;
		this.y = y;
		this.x = x;
		isEmpty = true;
		highlighted = 0;
		lightLevel = 5;
		explored = false;
		visibility = true;
		setTileFace(tileFace);
	}
	
	public static void init(String mapImage) throws SlickException {
		tileTextures = new SpriteSheet("res/wallTiles.png",
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
	}

	public void setEntity(Entity e) {
		containedEntity = e;
		containedEntity.setLocation(this);
		isEmpty = false;
	}

	public void disconnectEntity() {
		containedEntity = null;
	}

	public Entity getEntity() {
		return containedEntity;
	}

	public boolean isOpen() {
		return this.containedEntity==null;
	}

	public void clearTile(){
		this.containedEntity=null;
	}

	public void setHighlighted(int h) {
		this.highlighted = h;
	}

	public int getHighlighted() {
		return highlighted;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if (!this.isEmpty) {
			this.getEntity().update(gc, delta);
		}
	}

	/**
	 * renders to Graphics
	 * 
	 * @param gc
	 * @param sbg
	 * @param g
	 */
	public void render(Graphics g, float x, float y) {
		if (explored) {
			
			float renderLight = this.lightLevel;
			if (renderLight == 0 || !this.isVisible()) {
				renderLight = (float) 0.5;
			}

			Image toDraw = tileTextures.getSprite(
					tileFace % tileTextures.getHorizontalCount(), tileFace
							/ tileTextures.getHorizontalCount());
			toDraw.setAlpha(1 - (float) (numLightLevels - renderLight)
					* brightness / numLightLevels);
			g.drawImage(toDraw, x, y);

			if (this.highlighted != HIGHLIGHT_NULL) {
				g.setColor(highlightColors[highlighted]);
				g.fillRect(x, y, DeadReckoningGame.tileSize,
						DeadReckoningGame.tileSize);
			}
		}

	}

	public boolean isVisible() {
		return visibility;
	}

	public Tile getToLeft() {
		return parent.getTileAt(
				Utilities.limitTo(getX() - 1, 0, this.parent.getWidth()),
				getY());
	}

	public Tile getToRight() {
		return parent.getTileAt(
				Utilities.limitTo(getX() + 1, 0, this.parent.getWidth()),
				getY());
	}

	public Tile getToUp() {
		return parent.getTileAt(getX(),
				Utilities.limitTo(getY() - 1, 0, this.parent.getHeight()));
	}

	public Tile getToDown() {
		return parent.getTileAt(getX(),
				Utilities.limitTo(getY() + 1, 0, this.parent.getHeight()));
	}
	
	public Tile getRelativeTo(int xoff, int yoff) {
		return parent.getTileAt(
				Utilities.limitTo(getX() + xoff, 0, this.parent.getWidth()),
				Utilities.limitTo(getY() + yoff, 0, this.parent.getHeight()));
	}


	public GameBoard getParent() {
		return this.parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setTileFace(int tileFace) {
		this.tileFace = tileFace;
	}

	public int getTileFace() {
		return tileFace;
	}

	public boolean isTransparent() {
		if(!isOpen()){
			return this.getEntity().isTransparent();
		}
		return true;
	}

	public String toString() {
		return "Tile[" + x + "," + y + "]";
	}
}