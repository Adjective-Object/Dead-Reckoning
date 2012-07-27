package core.biomes;

import java.util.ArrayList;
import java.util.HashMap;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LivingEntity;
import net.plaidypus.deadreckoning.entities.Monster;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.generator.Room;
import net.plaidypus.deadreckoning.generator.RoomBasedBiome;
import net.plaidypus.deadreckoning.modloader.ModLoader;
import net.plaidypus.deadreckoning.statmaster.StatMaster;
import net.plaidypus.deadreckoning.utilities.BiomeUtils;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.entities.Door;
import core.entities.Torch;
 
/**
 * The Class Hemple.
 */
public class WaterTemple extends RoomBasedBiome {
	
	static HashMap<Integer,int[]> interchangable;
	
	/** The Constants used for determening the layout of the map tile file */
	public static final int TILE_EMPTY = 4, TILE_WALL_UP = 1,
			TILE_WALL_DOWN = 7, TILE_WALL_LEFT = 3, TILE_WALL_RIGHT = 5,
			TILE_WALL_UP_RIGHT = 6, TILE_WALL_UP_LEFT = 0,
			TILE_WALL_DOWN_RIGHT = 8, TILE_WALL_DOWN_LEFT = 2,
			TILE_TUNNEL = 27, TILE_NULL = 35;

	public WaterTemple() {
		this(20);
	}

	/**
	 * Instantiates a new Temple.
	 * 
	 * @param numRooms
	 *            the number rooms to be placed
	 */
	public WaterTemple(int numRooms) {
		super(numRooms);
	}

	@Override
	public void init() throws SlickException {
		Image tile = ModLoader.loadImage("core/res/floorTilesBiome1.png");
		this.tileImage = new SpriteSheet(tile,
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
		WaterTemple.interchangable= new HashMap<Integer, int[]>();
		interchangable.put(0, new int[] {9,18});
		interchangable.put(1, new int[] {10,19});
		interchangable.put(2, new int[] {11,20});
		interchangable.put(3, new int[] {12,21});
		interchangable.put(4, new int[] {13,22});
		interchangable.put(5, new int[] {14,23});
		interchangable.put(6, new int[] {15,24});
		interchangable.put(7, new int[] {16,25});
		interchangable.put(8, new int[] {17,26});	

		interchangable.put(27, new int[] {28,29,30,31,32,33,34});
	}


	@Override
	public GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks) throws SlickException{
		GameBoard b = super.makeBoard(depth, floorLinks);
		BiomeUtils.exchangeCompatableTiles(b, interchangable);
		return b;
	}
	
	@Override
	public GameBoard populateRoom(GameBoard target, Room room) throws SlickException {
		for (int i=room.x; i<room.width+room.x ;i++){
			target.getTileAt(i,room.y).setTileFace(TILE_WALL_UP);
			target.getTileAt(i,room.y-1).blocking=true;
			target.getTileAt(i,room.y+room.height-1).setTileFace(TILE_WALL_DOWN);
			target.getTileAt(i,room.y+room.height).blocking=true;
		}
		
		for (int i=room.y; i<room.height+room.y ;i++){
			target.getTileAt(room.x,i).setTileFace(TILE_WALL_LEFT);
			target.getTileAt(room.x-1,i).blocking=true;
			target.getTileAt(room.x+room.width-1,i).setTileFace(TILE_WALL_RIGHT);
			target.getTileAt(room.x+room.width,i).blocking=true;
		}
		
		target.getTileAt(room.x,room.y).setTileFace(TILE_WALL_UP_LEFT);
		target.getTileAt(room.x,room.y+room.height-1).setTileFace(TILE_WALL_UP_RIGHT);
		target.getTileAt(room.x+room.width-1,room.y).setTileFace(TILE_WALL_DOWN_LEFT);
		target.getTileAt(room.x+room.width-1,room.y+room.height-1).setTileFace(TILE_WALL_DOWN_RIGHT);
		
		target.getTileAt(room.x-1,room.y-1).blocking=true;
		target.getTileAt(room.x-1,room.y+room.height).blocking=true;
		target.getTileAt(room.x+room.width,room.y-1).blocking=true;
		target.getTileAt(room.x+room.width,room.y+room.height).blocking=true;

				
		for(int x=room.x+1; x<room.x+room.width-1; x++){
			for(int y=room.y+1; y<room.y+room.height-1; y++){
				target.getTileAt(x, y).setTileFace(TILE_EMPTY);
			}
		}
		
		target.placeEntity(room.getTileAt(target,0,0),new Torch(5), Tile.LAYER_PASSIVE_MAP);
		target.placeEntity(room.getTileAt(target,room.width-1,0),new Torch(5), Tile.LAYER_PASSIVE_MAP);
		target.placeEntity(room.getTileAt(target,0,room.height-1),new Torch(5), Tile.LAYER_PASSIVE_MAP);		
		target.placeEntity(room.getTileAt(target,room.width-1,room.height-1),new Torch(5), Tile.LAYER_PASSIVE_MAP);

		
		for(int i=0; i<Utilities.randInt(0,4); i++){
			Tile t = room.getTileIn(target);
			if(t.isEmpty(Tile.LAYER_ACTIVE)){
				Monster m = new Monster("core", "livingEntities/goblin.entity",
						new StatMaster(15, 15, 4, 4, 4, 4, 1), Entity.ALLIGN_HOSTILE);
				m.getInventory().addAll(m.getDropItems());
				target.placeEntity(t, m, Tile.LAYER_ACTIVE);
			}
		}
		
		return target;
	}

	@Override
	public GameBoard populateCooridors(GameBoard target, ArrayList<Room> rooms) {
		for(int i=0; i<rooms.size()-1; i++){
			Tile start= rooms.get(i).getCenter(target), end = rooms.get(i+1).getCenter(target);
			
			ArrayList<Tile> tilePath = BiomeUtils.getShitPath(start,end, target);
			
			drawPath(tilePath,rooms,TILE_TUNNEL);
			
		}
		for(int i=0; i<rooms.size(); i++){
			for (int y=0; y<rooms.get(i).height; y++){
				Tile a = target.getTileAt(rooms.get(i).x-1, rooms.get(i).y+y),
						b= target.getTileAt(rooms.get(i).x+rooms.get(i).width, rooms.get(i).y+y);
				if(a.getTileFace()==TILE_TUNNEL){
					target.placeEntity(a, new Door(), Tile.LAYER_ACTIVE);
				}
				if(b.getTileFace()==TILE_TUNNEL){
					target.placeEntity(b, new Door(), Tile.LAYER_ACTIVE);
				}
			}
			
			for (int x=0; x<rooms.get(i).width; x++){
				Tile a = target.getTileAt(rooms.get(i).x+x,rooms.get(i).y-1),
						b= target.getTileAt(rooms.get(i).x+x,rooms.get(i).y+rooms.get(i).height);
				if(a.getTileFace()==TILE_TUNNEL){
					target.placeEntity(a, new Door(), Tile.LAYER_ACTIVE);
				}
				if(b.getTileFace()==TILE_TUNNEL){
					target.placeEntity(b, new Door(), Tile.LAYER_ACTIVE);
				}
			}
		}
		
		
		return target;
	}
	
	public static void drawPath(ArrayList<Tile> tiles, ArrayList<Room> rooms, int tileValue){
		
		//check for collisions along the length of the tunnel
		//	marking the ones that do not have collisions, allowing for one collision consecutively
		//go along the path, and adjust the blocking of the unmarked tiles
		
		boolean[] marks = new boolean[tiles.size()];
		
		int colls = 2;
		for(int i=0; i<tiles.size(); i++){
			marks[i]=true;
			if( !checkTileColl(tiles.get(i)) ){
				colls=0;
				marks[i-1]=true;
			}
			else{
				if(colls>0){
					marks[i]=false;
				}
				colls++;
			}
		}
		
		for(int i=0; i<tiles.size(); i++){
			if(marks[i] && tiles.get(i).getTileFace()==TILE_NULL){
				tiles.get(i).setTileFace(tileValue);
			}
		}

		
		for(int i=0; i<tiles.size(); i++){
			if(marks[i]){
				tunnelTile(tiles.get(i));
			}
		}
	}
	
	private static boolean checkTileColl(Tile t){
		return t.getToLeft().getTileFace()!=TILE_NULL || t.getToRight().getTileFace()!=TILE_NULL ||
				t.getToDown().getTileFace()!=TILE_NULL || t.getToUp().getTileFace()!=TILE_NULL;
	}
	
	private static void tunnelTile(Tile t){
		for (int x=-1; x<=1; x++){
			for(int y=-1; y<=1; y++){
				Tile p = t.getRelativeTo(x, y);
				if(p.getTileFace()==TILE_NULL){
					p.blocking=true;
				}
			}
		}
		t.blocking=false;
	}
	
	@Override
	public int getNullTileValue(){
		return TILE_NULL;
	}
	
}
