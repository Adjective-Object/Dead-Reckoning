package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.utilities.Utilities;

import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class RoomBasedBiome.
 */
public abstract class RoomBasedBiome extends Biome {

	/** The room size max. */
	int numRooms, roomSizeMin = 5, roomSizeMax = 10;

	/**
	 * Instantiates a new room based biome.
	 * 
	 * @param numRooms
	 *            the num rooms
	 */
	public RoomBasedBiome(int numRooms) {
		this.numRooms = numRooms;
	}

	/**
	 * Populate board.
	 * 
	 * @param rooms
	 *            the rooms
	 * @param linkedLevels
	 *            the linked levels
	 * @return the game board
	 * @throws SlickException 
	 */
	public GameBoard populateBoard(ArrayList<Room> rooms, ArrayList<Stair> linkedLevels) throws SlickException{
		
		GameBoard target = new GameBoard(calcWidth(rooms), calcHeight(rooms), this);
		
		for(int i=0; i<rooms.size(); i++){
			populateRoom(target, rooms.get(i));
		}
		
		populateCooridors(target,rooms);
		
		placeStairs(target,rooms,linkedLevels);
		
		return target;
		
	}
	
	public GameBoard placeStairs(GameBoard board, ArrayList<Room> rooms, ArrayList<Stair> linkedLevels){
		for(int i=0; i<linkedLevels.size(); i++){
			int failcount = 0;
			Tile t = null;
			while (failcount<10){
				Room r = rooms.get(Utilities.randInt(0, rooms.size())) ;
				t= r.getTileIn(board);
				if(t.isEmpty(Tile.LAYER_PASSIVE_MAP) && t.getToLeft().isEmpty(Tile.LAYER_PASSIVE_MAP)){
					break;
				}
				else{
					failcount++;
				}
			}
			board.placeEntity(t, linkedLevels.get(i), Tile.LAYER_PASSIVE_MAP);
			board.placeEntity(t.getToLeft(), new LandingPad(linkedLevels.get(i).targetFloor),Tile.LAYER_PASSIVE_MAP);
		}
		return board;
	}
	
	public abstract GameBoard populateRoom( GameBoard target, Room room) throws SlickException;

	public abstract GameBoard populateCooridors(GameBoard target, ArrayList<Room> rooms) throws SlickException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.generator.Biome#makeBoard(int,
	 * java.util.ArrayList)
	 */
	@Override
	public GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks) throws SlickException{
		
		ArrayList<Room> rooms = makeRooms();
		
		GameBoard gb = this.populateBoard(rooms, floorLinks);
		gb.depth = depth;

		return gb;

	}
	
	public ArrayList<Room> makeRooms(){
		ArrayList<Room> rooms = new ArrayList<Room>(0);

		int mapWidth = 1, mapHeight = 1;
				
		while (rooms.size() < numRooms) {
			Room newRoom =	new Room(Utilities.randInt(1, mapWidth + roomSizeMax),
					Utilities.randInt(1, mapWidth + roomSizeMax),
					Utilities.randInt(roomSizeMin, roomSizeMax),
					Utilities.randInt(roomSizeMin, roomSizeMax));
			if (!newRoom.collidesWith(rooms)) {
				rooms.add(newRoom);
				if (mapWidth < newRoom.x + newRoom.width) {
					mapWidth = newRoom.x + newRoom.width;
				}
				if (mapHeight < newRoom.y + newRoom.height) {
					mapHeight = newRoom.y + newRoom.height;
				}
			}
		}
		return rooms;
	}
	
	public int calcWidth(ArrayList<Room> r){
		int mapWidth = 0;
		for (int i=0; i<r.size(); i++){
			if (mapWidth < r.get(i).x + r.get(i).width+1) {
				mapWidth = r.get(i).x + r.get(i).width+1;
			}
		}
		return mapWidth;
	}
	
	public int calcHeight(ArrayList<Room> r){
		int mapHeight = 0;
		for (int i=0; i<r.size(); i++){
			if (mapHeight < r.get(i).y + r.get(i).height+1) {
				mapHeight = r.get(i).y + r.get(i).height+1;
			}
		}
		return mapHeight;
	}
	
}
