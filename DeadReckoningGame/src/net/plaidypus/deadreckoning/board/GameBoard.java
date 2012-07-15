package net.plaidypus.deadreckoning.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.grideffects.GridEffect;
import net.plaidypus.deadreckoning.hudelements.game.GameplayElement;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import rlforj.los.BresLos;
import rlforj.los.ILosAlgorithm;
import rlforj.los.ILosBoard;

// TODO: Auto-generated Javadoc
/**
 * The Class GameBoard.
 */
public class GameBoard implements ILosBoard {

	/** The in game entities. */
	private ArrayList<Entity> ingameEntities = new ArrayList<Entity>(0);;
	HashMap<Integer, Entity> entityReferenceMap = new HashMap<Integer,Entity>(0);
	//this doesn't even ensure that the list will always run in the same order.
	//:<
	//potential glitches, but whogivesafuck.
	//Necessary so the iterator won't flip fucks when entities are moved around.
	
	protected int entityCounter = 0;
	
	/** The board. */
	public Tile[][] board;

	/** The primary highlight. */
	Tile primaryHighlight;

	/** The height. */
	public int width, height;

	/** The under effects. */
	ArrayList<GridEffect> overEffects =  new ArrayList<GridEffect>(0), underEffects =  new ArrayList<GridEffect>(0);

	/** The Gameplay element. */
	GameplayElement GameplayElement;

	/** The render dist x. */
	public int depth, renderDistY = 20, renderDistX = 40;
	
	public Biome biome;
	
	/** The map id. */
	public String saveID, mapID;

	/** The Constant primaryHighlightColor. */
	static final Color primaryHighlightColor = new Color(255, 75, 23);

	/**
	 * Instantiates a new game board.
	 * 
	 * @param g
	 *            the g
	 * @param saveID
	 *            the save id
	 * @param mapID
	 *            the map id
	 */
	public GameBoard(GameplayElement g, String saveID, String mapID) {
		this.GameplayElement = g;
		this.saveID = saveID;
		this.mapID = mapID;
	}
	
	/**
	 * Instantiates a new game board.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public GameBoard(int width, int height, Biome b) {
		this.biome = b;
		this.width = width;
		this.height = height;
		board = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				try {
					board[x][y] = new Tile(this, x, y, this.biome.getNullTileValue());
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Place entity.
	 * 
	 * @param t
	 *            the t
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 */
	public void placeEntity(Tile t, Entity e, int layer) {
		placeEntity(t.getX(), t.getY(), e, layer);
	}

	/**
	 * Insert entity.
	 * 
	 * @param index
	 *            the index
	 * @param t
	 *            the t
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 */
	public void insertEntity(int index, Tile t, Entity e, int layer) {
		insertEntity(index, t.getX(), t.getY(), e, layer);
	}

	/**
	 * Place entity.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 */
	public void placeEntity(int x, int y, Entity e, int layer) {
		board[x][y].setEntity(e, layer);
		addEntity(e,-1);
	}

	/**
	 * potential for bugs later if the same entity is removed and replaced on the board. may be assigned multiple EntityIDs.
	 * @return
	 */
	public void addEntity(Entity e, int index) {
		if(index==-1){//if it is not already given an ID on the board
			this.ingameEntities.add(e);
			this.entityReferenceMap.put(entityCounter,e);
			e.setID(entityCounter);
			this.entityCounter++;
		}
		else{
			this.ingameEntities.add(e);
			this.entityReferenceMap.put(index,e);
			e.setID(index);
		}
		Log.info("Entity "+e.getClass().getSimpleName()+"@"+System.identityHashCode(e)+" spawned on GameBoard@"+System.identityHashCode(this)+" with Entity ID "+e.getID());
	}
	
	public Integer iterateCounter(){
		this.entityCounter++;
		return this.entityCounter--;
	}

	/**
	 * Insert entity.
	 * 
	 * @param index
	 *            the index
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 */
	public void insertEntity(int index, int x, int y, Entity e, int layer) {//TODO this is bugged because no actual insertion ()
		this.addEntity(e,index);
		board[x][y].setEntity(e, layer);
	}

	/**
	 * Place entity near. //TODO NOPENOENONAKL
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param e
	 *            the e
	 * @param layer
	 *            the layer
	 * @return true, if successful
	 */
	public boolean placeEntityNear(int x, int y, Entity e, int layer) {
		if(this.board[x][y].isOpen(layer)){
			placeEntity(x,y,e,layer);
			return true;
		}
		for (int scanRadius = 0; scanRadius < 10; scanRadius++) {
			for (int i = -scanRadius + 1; i < scanRadius; i++) {
				if (getTileAt(x + i, y - scanRadius).isEmpty(layer)) {
					placeEntity(x+i,y - scanRadius,e,layer);
					return true;
				}
				if (getTileAt(x - scanRadius, y + i).isEmpty(layer)) {
					placeEntity(x-scanRadius,y+i,e,layer);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes the entity.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param layer
	 *            the layer
	 */
	public void removeEntity(int x, int y, int layer) {
		removeEntity(board[x][y].getEntity(layer));
	}

	/**
	 * Removes the entity.
	 * 
	 * @param e
	 *            the e
	 */
	public void removeEntity(Entity e) {
		entityReferenceMap.remove(e.getID());
		ingameEntities.remove(e.getID());
		e.getLocation().disconnectEntity(e.getLayer());
		Log.info("Entity "+e+" removed from Game Board@"+System.identityHashCode(this));
	}

	/**
	 * Clear tile.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void clearTile(int x, int y) {
		for (int i = 0; i < Tile.numLayers; i++) {
			ingameEntities.remove(board[x][y].getEntity(i).getID());
		}
		board[x][y].disconnectEntities();
	}

	/**
	 * Move entity.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @param layer
	 *            the layer
	 */
	public void moveEntity(Entity source, Tile target, int layer) {
		source.getLocation().disconnectEntity(layer);
		target.setEntity(source, layer);
	}

	/**
	 * Gets the tile at.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the tile at
	 */
	public Tile getTileAt(int x, int y) {
		return board[x][y];
	}

	/**
	 * Render.
	 * 
	 * @param g
	 *            the g
	 * @param xoff
	 *            the xoff
	 * @param yoff
	 *            the yoff
	 */
	public void render(Graphics g, int xoff, int yoff) {

		int 
			lowX = (int) Utilities.limitTo(
				-xoff / DeadReckoningGame.tileSize,
				0, this.getWidth()),
				
			highX = (int) Utilities.limitTo(
					-xoff/ DeadReckoningGame.tileSize + renderDistX,
					0, this.getWidth()),
			
			lowY = (int) Utilities.limitTo(
					-yoff / DeadReckoningGame.tileSize,
					0, this.getHeight()),
					
			highY = (int) Utilities.limitTo(
				-yoff / DeadReckoningGame.tileSize + renderDistY,
				0,this.getHeight()) ;

		for (int x = lowX; x < highX; x++) {
			for (int y = lowY; y < highY; y++) {
				board[x][y].render(g,
						(int)(x * DeadReckoningGame.tileSize + xoff),
						(int)(y * DeadReckoningGame.tileSize + yoff) );
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
			underEffects.get(i).render(g, (int)xoff, (int)yoff);
		}

		for (int x = lowX; x < highX; x++) {
			for (int y = lowY; y < highY; y++) {
				for (int i = Tile.numLayers - 1; i >= 0; i--) {
					if (!board[x][y].isOpen(i)
							&& ( (board[x][y].lightLevel >= 1 && board[x][y]
									.isVisible()) || (board[x][y].getEntity(i)
									.isTerrain() && board[x][y].explored) || DeadReckoningGame.debugMode )) {
						board[x][y].getEntity(i).render(g,
								(int)(x * DeadReckoningGame.tileSize + xoff),
								(int)(y * DeadReckoningGame.tileSize + yoff) );
					}
				}
			}
		}

		for (int i = 0; i < overEffects.size(); i++) {
			overEffects.get(i).render(g, xoff, yoff);
		}
	}

	/**
	 * Update selctor.
	 * 
	 * @param i
	 *            the i
	 * @param xOff
	 *            the x off
	 * @param yOff
	 *            the y off
	 */
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

	/**
	 * Update all tiles.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 */
	public void updateAllTiles(GameContainer gc, int delta) {

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				for (int i = 0; i < Tile.numLayers; i++) {
					if (!board[x][y].isOpen(i)) {
						board[x][y].getEntity(i).update(gc, delta);
						Entity e = board[x][y].getEntity(i);
						if (e.toKill) {
							this.removeEntity(e);
							e.onDeath();
						}
					}
				}
			}
		}

		for (int i = 0; i < underEffects.size(); i++) {
			underEffects.get(i).update(delta);
			if (underEffects.get(i).isComplete()) {
				underEffects.remove(i);
				i -= 1;
			}
		}

		for (int i = 0; i < overEffects.size(); i++) {
			overEffects.get(i).update(delta);
			if (overEffects.get(i).isComplete()) {
				overEffects.remove(i);
				i -= 1;
			}
		}
	}

	/**
	 * Update board effects.
	 * 
	 * @param gc
	 *            the gc
	 * @param delta
	 *            the delta
	 */
	public void updateBoardEffects(GameContainer gc, int delta) {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				for (int i = 0; i < Tile.numLayers; i++) {
					if (!board[x][y].isOpen(i)) {
						board[x][y].getEntity(i).updateBoardEffects(gc, delta);
					}
				}
			}
		}
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Adds the effect under.
	 * 
	 * @param g
	 *            the g
	 */
	public void addEffectUnder(GridEffect g) {
		if (g != null) {
			this.underEffects.add(g);
		}
	}

	/**
	 * Adds the effect over.
	 * 
	 * @param g
	 *            the g
	 */
	public void addEffectOver(GridEffect g) {
		if (g != null) {
			this.overEffects.add(g);
		}
	}

	/**
	 * Adds the effect under.
	 * 
	 * @param t
	 *            the t
	 * @param g
	 *            the g
	 */
	public void addEffectUnder(Tile t, GridEffect g) {
		if (g != null) {
			g.setLocation(t);
			this.underEffects.add(g);
		}
	}

	/**
	 * Adds the effect over.
	 * 
	 * @param t
	 *            the t
	 * @param g
	 *            the g
	 */
	public void addEffectOver(Tile t, GridEffect g) {
		if (g != null) {
			g.setLocation(t);
			this.overEffects.add(g);
		}
	}

	/**
	 * Highlight square.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void highlightSquare(int x, int y) {
		board[x][y].setHighlighted(Tile.HIGHLIGHT_CONFIRM);
	}

	/**
	 * Checks if is tile highlighted.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if is tile highlighted
	 */
	public boolean isTileHighlighted(int x, int y) {
		return board[x][y].getHighlighted() == 1;
	}

	/**
	 * Sets the primairy highlight.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setPrimairyHighlight(int x, int y) {
		this.primaryHighlight = board[x][y];
	}

	/**
	 * Sets the primairy highlight.
	 * 
	 * @param t
	 *            the new primairy highlight
	 */
	public void setPrimairyHighlight(Tile t) {
		this.primaryHighlight = t;
	}

	/**
	 * Gets the primairy highlight.
	 * 
	 * @return the primairy highlight
	 */
	public Tile getPrimairyHighlight() {
		return primaryHighlight;
	}

	/**
	 * Clear primary highlight.
	 */
	public void clearPrimaryHighlight() {
		this.primaryHighlight = null;
	}

	/**
	 * Clear highlighted squares.
	 */
	public void clearHighlightedSquares() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[x][y].setHighlighted(0);
			}
		}
	}

	// TODO block light based on transparency / vision
	/**
	 * Light in radius.
	 * 
	 * @param location
	 *            the location
	 * @param VIS
	 *            the vIS
	 */
	public void lightInRadius(Tile location, float VIS) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				int dist = (int) Utilities.getDistance(location, board[x][y]);
				if (dist <= VIS) {
					float level = Utilities.limitTo(VIS + 1 - dist, 1,
							Tile.numLightLevels);
					if (level > (int) board[x][y].lightLevel
							&& isLineofSight(location, board[x][y])) {
						board[x][y].lightLevel = level;
					}
				}
			}
		}
	}

	/**
	 * Reveal from entity.
	 * 
	 * @param entity
	 *            the entity
	 * @param sightDistance
	 *            the sight distance
	 */
	public void revealFromEntity(Entity entity, int sightDistance) {
		// TODO temporary. only in place because I can't get the RL4J version to work
		// needs to be replaced. it is inefficient and also can see through diagonally adjacent walls.
		for (int i = 0; i < board.length; i++) {
			for (int y = 0; y < board[i].length; y++) {
				if (Utilities.getDistance(board[i][y], entity.getLocation()) <= sightDistance
						&& isLineofSight(entity.getLocation(), board[i][y])) {
					board[i][y].visible = true;
					board[i][y].explored = true;
				}
			}
		}
	}

	/**
	 * Checks if is lineof sight.
	 * 
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return true, if is lineof sight
	 */
	public boolean isLineofSight(Tile a, Tile b) {
		ILosAlgorithm alg = new BresLos(false);
		return alg.existsLineOfSight(this, a.getX(), a.getY(), b.getX(),
				b.getY(), false);
	}

	/**
	 * Find available paths.
	 * 
	 * @param source
	 *            the source
	 * @param wanderDist
	 *            the wander dist
	 * @param layer
	 *            the layer
	 * @return the array list
	 */
	public ArrayList<Tile> findAvailablePaths(Tile source, int wanderDist,
			int layer) {// TODO limit generated array to dimensions of board
		ArrayList<Tile> toRet = new ArrayList<Tile>(0);

		double[][] tiles = new double[wanderDist * 2 + 1][wanderDist * 2 + 1];
		for (int i = 0; i < wanderDist * 2 + 1; i++) {
			for (int t = 0; t < wanderDist * 2 + 1; t++) {
				tiles[i][t] = wanderDist + 1;
			}
		}
		tiles[wanderDist][wanderDist] = 0;

		for (int i = 0; i < wanderDist; i++) {
			for (int x = 0; x < wanderDist * 2 + 1; x++) {
				for (int y = 0; y < wanderDist * 2 + 1; y++) {
					for (int a = -1; a < 2; a++) {
						for (int b = -1; b < 2; b++) {
							if (Math.abs(b) != Math.abs(a)) {
								double tot = tiles[Utilities.limitTo(x + a, 0,
										tiles.length)][Utilities.limitTo(y + b,
										0, tiles[x].length)];
								if (tiles[x][y] >= tot + 1
										&& coordInGrid(source.getX()
												- wanderDist + x, source.getY()
												- wanderDist + y)
										&& getTileAt(
												source.getX() - wanderDist + x,
												source.getY() - wanderDist + y)
												.isOpen(layer)) {
									tiles[x][y] = tot + 1;
								}
							}
						}
					}
				}
			}
		}

		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				int ax = source.getX() - wanderDist + x;
				int ay = source.getY() - wanderDist + y;
				if (tiles[x][y] <= wanderDist && coordInGrid(ax, ay)) {
					toRet.add(getTileAt(ax, ay));
				}
			}
		}

		toRet.remove(source);
		return toRet;
	}

	/**
	 * High light available paths.
	 * 
	 * @param source
	 *            the source
	 * @param wanderDist
	 *            the wander dist
	 * @param layer
	 *            the layer
	 */
	public void highLightAvailablePaths(Tile source, int wanderDist, int layer) {
		ArrayList<Tile> targetableTiles = findAvailablePaths(source,
				wanderDist, layer);
		for (int i = 0; i < targetableTiles.size(); i++) {
			targetableTiles.get(i).highlighted = Tile.HIGHLIGHT_CONFIRM;
		}
	}

	/**
	 * makes every tile on the board have a low light level, and makes it
	 * invisible.
	 */
	public void HideAll() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				board[x][y].lightLevel = 0;
				board[x][y].visible = false;
			}
		}
	}

	/**
	 * Coord in grid.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean coordInGrid(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < width;
	}

	/**
	 * Gets the game.
	 * 
	 * @return the game
	 */
	public GameplayElement getGame() {
		return GameplayElement;
	}

	/**
	 * Sets the game.
	 * 
	 * @param g
	 *            the new game
	 */
	public void setGame(GameplayElement g) {
		this.GameplayElement = g;
	}

	/**
	 * Assign element.
	 * 
	 * @param g
	 *            the g
	 */
	public void assignElement(GameplayElement g) {
		this.GameplayElement = g;
	}

	/**
	 * Checks if is idle.
	 * 
	 * @return true, if is idle
	 */
	public boolean isIdle() {
		for (int i = 0; i < this.ingameEntities.size(); i++) {
			if (!ingameEntities.get(i).isIdle()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the save id.
	 * 
	 * @return the save id
	 */
	public String getSaveID() {
		return saveID;
	}

	/**
	 * Gets the map id.
	 * 
	 * @return the map id
	 */
	public String getMapID() {
		return mapID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rlforj.los.ILosBoard#contains(int, int)
	 */
	public boolean contains(int x, int y) {
		return x < this.getWidth() && x >= 0 && y < this.getHeight() && y >= 0
				&& this.getTileAt(x, y).isOpen(Tile.LAYER_ACTIVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rlforj.los.ILosBoard#isObstacle(int, int)
	 */
	public boolean isObstacle(int x, int y) {
		return x < this.getWidth() && x >= 0 && y < this.getHeight() && y >= 0
				&& !board[x][y].isTransparent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rlforj.los.ILosBoard#visit(int, int)
	 */
	public void visit(int x, int y) {
		if (x < this.getWidth() && x >= 0 && y < this.getHeight() && y >= 0) {
			this.getTileAt(x, y).visible = true;
			this.getTileAt(x, y).explored = true;
		}
	}

	/**
	 * method for getting a valid tile to drop the player on
	 * 
	 * used in lieu of a default spawn point as defined by Biome.
	 * 
	 * @return
	 */
	public Tile getValidSpawnTile() {
		Tile returnTile = this.getTileAt(0, 0);
		while (returnTile.getTileFace() == this.biome.getNullTileValue()) {
			returnTile = this.getTileAt(Utilities.randInt(0, this.getWidth()),
					Utilities.randInt(0, this.getHeight()));
		}
		return returnTile;
	}

	public Biome getBiome() {
		return this.biome;
	}
	
	public Entity getEntityFromID(int id){
		if(this.entityReferenceMap.containsKey(id)){
			return this.entityReferenceMap.get(id);
		}
		else{
			System.err.println("Entity of ID "+id+" does not exist!");
			return null;
		}
	}
	
	public static Entity getEntity(int sourceID) {
		return DeadReckoningGame.instance.getGameElement().getBoard().getEntityFromID(sourceID);
	}
	
	public ArrayList<Entity> getIngameEntities() {
		return ingameEntities;
	}

}
