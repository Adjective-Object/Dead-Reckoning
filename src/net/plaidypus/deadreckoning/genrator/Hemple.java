package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Torch;

public class Hemple extends RoomBasedBiome{

	public Hemple(int numRooms) {
		super(numRooms);
	}
	
	public GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms){
		super.populateBoard(target, rooms);
		System.out.println(target.getWidth()+" "+target.getHeight());
		for(int i=0; i<rooms.size(); i++){
			target.placeEntity(target.getTileAt(rooms.get(i)[0]+rooms.get(i)[2]/2, rooms.get(i)[1]+rooms.get(i)[3]/2),
					new Torch(null,Tile.LAYER_PASSIVE_MAP, 10 ), Tile.LAYER_PASSIVE_MAP);
		}
		return target;
	}
	
}
