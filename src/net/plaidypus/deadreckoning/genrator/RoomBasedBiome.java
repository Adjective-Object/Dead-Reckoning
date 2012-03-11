package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Stair;

import org.newdawn.slick.SlickException;

public abstract class RoomBasedBiome extends Biome{
	
	int numRooms, roomSizeMin=5, roomSizeMax=10;
	
	public RoomBasedBiome(int numRooms){
		this.numRooms = numRooms;
	}
	
	public GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms, ArrayList<Stair> linkedLevels){
		for(int i=0; i<rooms.size(); i++){
			for(int x=0; x<rooms.get(i)[2];x++){
				for(int y=0; y<rooms.get(i)[3];y++){
						target.getTileAt(rooms.get(i)[0]+x,rooms.get(i)[1]+y).setTileFace(Tile.TILE_WALL_DOWN_RIGHT);
					}
				}
			}
		return target;
	}
	
	public GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks){
		ArrayList<int[]> rooms = new ArrayList<int[]>(0);
		
		int roomWidth = 1, roomHeight = 1;
		
		while( rooms.size()<numRooms ){
			int[] newRoom = { Utilities.randInt(0,roomWidth+roomSizeMax), Utilities.randInt(0,roomHeight+roomSizeMax), Utilities.randInt(roomSizeMin, roomSizeMax) , Utilities.randInt(roomSizeMin, roomSizeMax) };
			if(!checkforCollisions(newRoom,rooms)){
				rooms.add(newRoom);
				if( roomWidth<newRoom[0]+newRoom[2] ){
					roomWidth = newRoom[0]+newRoom[2];
				}
				if( roomHeight<newRoom[1]+newRoom[3] ){
					roomHeight = newRoom[1]+newRoom[3];
				}
			}
		}
		
		GameBoard gb = this.populateBoard(new GameBoard(roomWidth, roomHeight),rooms, floorLinks);
		gb.depth=depth;
		
		return gb;
		
	}

	public boolean checkforCollisions(int[] room, ArrayList<int[]> rooms){ //check for collisions with other rooms;
		for(int i=0; i<rooms.size(); i++){
			if( !( room[0]+room[2]<rooms.get(i)[0] ||
				room[0]>rooms.get(i)[0]+rooms.get(i)[2] ||
				room[1]+room[3]<rooms.get(i)[1] ||
				room[1]>rooms.get(i)[1]+rooms.get(i)[3]
					) ){
				return true;
			}
		}
		return false;
	}
	
}
