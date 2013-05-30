package net.plaidypus.deadreckoning.board;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// TODO: Auto-generated Javadoc
/*

 Griffin Brodman & Jeffrey Tao
 and Maaaaaaxxxxxxxx Huang-Hobbs.
 Created: 11/4/2011

 */

/**
 * The Class Tile.
 */
public class Tile {

	/** The contained entities. */
	private Entity[] containedEntities;

	/** The Constant LAYER_PASSIVE_MAP. */
	public static final int LAYER_ACTIVE = 0, LAYER_PASSIVE_PLAY = 1,
			LAYER_PASSIVE_MAP = 2;

	/** The Constant numLayers. */
	public static final int numLayers = 3;

	/** The parent. */
	private GameBoard parent;

	/** The y. */
	private int x, y;

	/** The tile face. */
	private int tileFace;

	/** The highlighted. */
	public int highlighted;

	/** The Constant numLightLevels. */
	public static final int numLightLevels = 5;

	/** The explored. */
	public boolean explored = false, blocking = false;

	/** The visible. */
	public boolean visible;

	/** The light level. */
	public float lightLevel;

	/** The Constant highlightColors. */
	static final Color[] highlightColors = new Color[] { new Color(0, 0, 0, 0),
			new Color(255, 75, 23, 100), new Color(252, 125, 73, 100) };

	/** The Constant brightness. */
	static final float brightness = 0.8F;

	/** The Constant HIGHLIGHT_DENY. */
	public static final int HIGHLIGHT_NULL = 0, HIGHLIGHT_CONFIRM = 1,
			HIGHLIGHT_DENY = 2;

	/**
	 * Instantiates a new tile.
	 * 
	 * @param parent
	 *            the parent
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param tileFace
	 *            the tile face
	 * @throws SlickException
	 *             the slick exception
	 */
	public Tile(GameBoard parent, int x, int y, int tileFace)
			throws SlickException {
		this.parent = parent;
		this.y = y;
		this.x = x;
		highlighted = 0;
		lightLevel = 5;
		visible = true;
		setTileFace(tileFace);
		containedEntities = emptyEntityArray();
	}

	/**
	 * Inits the.
	 * 
	 * @param mapImage
	 *            the map image
	 * @throws SlickException
	 *             the slick exception
	 */
	public static void init() throws SlickException {
	}

	/**
	 * Sets the entity.
	 * 
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 */
	protected void setEntity(Entity e, int layer) {
		e.setLocation(this,layer);
		containedEntities[layer] = e;
	}

	/**
	 * Disconnect entity.
	 * 
	 * @param layer
	 *            the layer
	 */
	public void disconnectEntity(int layer) {
		this.containedEntities[layer].setLocation(null, layer);
		this.containedEntities[layer] = null;
	}

	/**
	 * Disconnect entities.
	 */
	public void disconnectEntities() {
		for(int i=0; i<numLayers; i++){
			this.containedEntities[i].setLocation(null, i);
		}
		this.containedEntities = emptyEntityArray();
	}

	/**
	 * Gets the entities.
	 * 
	 * @return the entities
	 */
	public Entity[] getEntities() {
		return containedEntities;
	}

	/**
	 * Gets the entity.
	 * 
	 * @param layer
	 *            the layer
	 * @return the entity
	 */
	public Entity getEntity(int layer) {
		return containedEntities[layer];
	}

	/**
	 * Checks if the tile has an entity on it
	 * 
	 * @param layer
	 *            the layer
	 * @return true, if is open
	 */
	public boolean isOpen(int layer) {
		return this.containedEntities[layer] == null;
	}

	/**
	 * Checks if is open.
	 * 
	 * @return true, if is open
	 */
	public boolean isOpen() {
		for(int i=0; i<this.containedEntities.length; i++){
			if(this.containedEntities[i]!=null){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks if there is an entity on the tile OR if the tile blocks movement(is a wall)
	 * 
	 * @param layer
	 * @return
	 */
	public boolean isEmpty(int layer) {
		return this.containedEntities[layer] == null && !blocking;

	}

	/**
	 * Empty entity array.
	 * 
	 * @return the entity[]
	 */
	public static Entity[] emptyEntityArray() {
		Entity[] q = new Entity[numLayers];
		for (int i = 0; i < numLayers; i++) {
			q[i] = null;
		}
		return q;
	}

	/**
	 * Sets the highlighted.
	 * 
	 * @param h
	 *            the new highlighted
	 */
	public void setHighlighted(int h) {
		this.highlighted = h;
	}

	/**
	 * Gets the highlighted.
	 * 
	 * @return the highlighted
	 */
	public int getHighlighted() {
		return highlighted;
	}

	/**
	 * renders to Graphics.
	 * 
	 * @param g
	 *            the g
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void render(Graphics g, int x, int y) {
		if ((explored||DeadReckoningGame.debugMode) && this.tileFace!=this.getParent().getBiome().getNullTileValue()) {
			
			SpriteSheet s = this.getParent().getBiome().getTileImage();
			
			Image toDraw = s.getSprite(
					tileFace % s.getHorizontalCount(), tileFace
							/ s.getHorizontalCount());
			g.drawImage(toDraw, x, y);

			if (this.highlighted != HIGHLIGHT_NULL) {
				g.setColor(highlightColors[highlighted]);
				g.fillRect(x, y, DeadReckoningGame.tileSize,
						DeadReckoningGame.tileSize);
			}
		}

	}
	
	public float getLightAlpha(){
		float renderLight = this.lightLevel;
		if (renderLight == 0 || !this.isVisible()) {
			renderLight = 0.5F;
		}
		
		if(DeadReckoningGame.debugMode){
			renderLight=numLightLevels;
		}
		
		return
				((numLightLevels - renderLight) / (numLightLevels))
				* brightness + (1 - brightness);

	}

	/**
	 * Checks if is visible.
	 * 
	 * @return true, if is visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Can be seen.
	 * 
	 * @return true, if successful
	 */
	public boolean canBeSeen() {
		return visible && this.lightLevel > 0;
	}

	/**
	 * Gets the to left.
	 * 
	 * @return the to left
	 */
	public Tile getToLeft() {
		return parent.getTileAt(
				Utilities.limitTo(getX() - 1, 0, this.parent.getWidth()),
				getY());
	}

	/**
	 * Gets the to right.
	 * 
	 * @return the to right
	 */
	public Tile getToRight() {
		return parent.getTileAt(
				Utilities.limitTo(getX() + 1, 0, this.parent.getWidth()),
				getY());
	}

	/**
	 * Gets the to up.
	 * 
	 * @return the to up
	 */
	public Tile getToUp() {
		return parent.getTileAt(getX(),
				Utilities.limitTo(getY() - 1, 0, this.parent.getHeight()));
	}

	/**
	 * Gets the to down.
	 * 
	 * @return the to down
	 */
	public Tile getToDown() {
		return parent.getTileAt(getX(),
				Utilities.limitTo(getY() + 1, 0, this.parent.getHeight()));
	}

	/**
	 * Gets the relative to.
	 * 
	 * @param xoff
	 *            the xoff
	 * @param yoff
	 *            the yoff
	 * @return the relative to
	 */
	public Tile getRelativeTo(int xoff, int yoff) {
		return parent
				.getTileAt(
						Utilities.limitTo(getX() + xoff, 0,
								this.parent.getWidth() - 1),
						Utilities.limitTo(getY() + yoff, 0,
								this.parent.getHeight() - 1));
	}

	/**
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	public GameBoard getParent() {
		return this.parent;
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the tile face.
	 * 
	 * @param tileFace
	 *            the new tile face
	 */
	public void setTileFace(int tileFace) {
		this.tileFace = tileFace;
	}

	/**
	 * Gets the tile face.
	 * 
	 * @return the tile face
	 */
	public int getTileFace() {
		return tileFace;
	}

	/**
	 * Checks if is transparent.
	 * 
	 * @return true, if is transparent
	 */
	public boolean isTransparent() {
		if (!isOpen(Tile.LAYER_ACTIVE)) {
			return this.getEntity(Tile.LAYER_ACTIVE).isTransparent() && !blocking;
		}
		return !blocking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tile[" + x + "," + y + "]";
	}
	
	public void swapLayers(int layer1, int layer2){
		Entity e1 = containedEntities[layer1], e2 = containedEntities[layer2];
		containedEntities[layer2]=e1;
		containedEntities[layer1]=e2;
	}
}