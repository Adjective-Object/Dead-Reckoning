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
	private Entity[] containedEntities;
	public static final int LAYER_ACTIVE = 0, LAYER_PASSIVE_PLAY = 1, LAYER_PASSIVE_MAP = 2;
	public static final int numLayers = 3;
	
	private GameBoard parent;
	private int x, y;
	private int tileFace;
	public int highlighted;

	public static final int numLightLevels = 5;
	public boolean explored;
	public boolean visibility;
	public float lightLevel;

	static final Color[] highlightColors = new Color[] { new Color(0, 0, 0, 0),
			new Color(255, 75, 23, 100), new Color(252, 125, 73, 100) };

	static final float brightness = (float) (0.5);
	public static final int HIGHLIGHT_NULL = 0, HIGHLIGHT_CONFIRM = 1,
			HIGHLIGHT_DENY = 2;

	static SpriteSheet tileTextures;

	public static final int TILE_EMPTY = 4, TILE_WALL_UP = 1, TILE_WALL_DOWN = 7, TILE_WALL_LEFT = 3,
	TILE_WALL_RIGHT = 5, TILE_WALL_UP_RIGHT = 2, TILE_WALL_UP_LEFT = 0, TILE_WALL_DOWN_RIGHT = 8, TILE_WALL_DOWN_LEFT = 6,
	TILE_SPECIAL=9, TILE_NULL=10;

	public Tile(GameBoard parent, int x, int y, int tileFace) throws SlickException {
		this.parent = parent;
		this.y = y;
		this.x = x;
		highlighted = 0;
		lightLevel = 5;
		explored = false;
		visibility = true;
		setTileFace(tileFace);
		containedEntities = emptyEntityArray();
	}
	
	public static void init(String mapImage) throws SlickException {
		tileTextures = new SpriteSheet("res/wallTiles.png",
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
	}

	protected void setEntity(Entity e, int layer) {
		e.setLayer(layer);
		e.setLocation(this);
		containedEntities[layer] = e;
	}

	public void disconnectEntity(int layer) {
		containedEntities[layer] = null;
	}
	
	public void disconnectEntities() {
		containedEntities = emptyEntityArray();
	}

	public Entity[] getEntities() {
		return containedEntities;
	}
	
	public Entity getEntity(int layer) {
		return containedEntities[layer];
	}

	public boolean isOpen(int layer) {
		return this.containedEntities[layer]==null;
	}
	
	public boolean isOpen() {
		return this.containedEntities.length==0;//TODO not sure if this will work
	}

	public void clearTile(){
		this.containedEntities= emptyEntityArray();
	}
	
	public void clearTile(int i) {
		this.containedEntities[i]=null;
	}
	
	public static Entity[] emptyEntityArray(){
		Entity[] q = new Entity[numLayers];
		for(int i=0; i<numLayers;i++){
			q[i]=null;
		}
		return q;
	}

	public void setHighlighted(int h) {
		this.highlighted = h;
	}

	public int getHighlighted() {
		return highlighted;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		for(int i=0; i<getEntities().length; i++){
			if (!this.isOpen(i)) {
				this.getEntity(i).update(gc, delta);
			}
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
				renderLight = (float)0.5;
			}
			//System.out.println(tileFace);
			Image toDraw = tileTextures.getSprite( tileFace % tileTextures.getHorizontalCount(), tileFace / tileTextures.getHorizontalCount());
			toDraw.setAlpha(  ( 1- ( (float)(numLightLevels - renderLight) / (numLightLevels) ) )*brightness + (1-brightness) );
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
	
	public boolean canBeSeen(){
		return visibility && this.lightLevel>0;
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
		if(!isOpen(Tile.LAYER_ACTIVE)){
			return this.getEntity(Tile.LAYER_ACTIVE).isTransparent();
		}
		return true;
	}

	public String toString() {
		return "Tile[" + x + "," + y + "]";
	}
}