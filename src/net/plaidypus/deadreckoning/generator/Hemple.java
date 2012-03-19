package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Door;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Torch;

public class Hemple extends RoomBasedBiome{

	
	public Hemple(int numRooms) {
		super(numRooms);
		requiredClasses = new Class[] {
				Torch.class,
				Stair.class,
				Door.class
		};
	}
	
	public GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms, ArrayList<Stair> linkedLevels){
		super.genericPopulation(target,rooms,linkedLevels);
		
		
		for(int i=0; i<rooms.size(); i++){
			int[] room = rooms.get(i);
			for(int x=room[0]; x<room[0]+room[2]; x++){
				Tile t = target.getTileAt(x, Utilities.limitTo(room[1]-1,0,target.getHeight()));
				if(t !=target.getTileAt(x,room[1]) && t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
				t = target.getTileAt(x, Utilities.limitTo(room[1]+room[3]+1,0,target.getHeight()));
				if(t !=target.getTileAt(x,room[1]) && t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
			}
		}
		
		for(int i=0; i<rooms.size(); i++){
			target.placeEntity(target.getTileAt(rooms.get(i)[0]+rooms.get(i)[2]/2, rooms.get(i)[1]+rooms.get(i)[3]/2),
					new Torch(null,Tile.LAYER_PASSIVE_MAP, 10 ), Tile.LAYER_PASSIVE_MAP);
		}
		
		return target;
	}
	
}
