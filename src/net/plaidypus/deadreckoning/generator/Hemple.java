package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Torch;

public class Hemple extends RoomBasedBiome{

	
	public Hemple(int numRooms) {
		super(numRooms);
		requiredClasses = new Class[] {
				Torch.class,
				Stair.class
		};
	}
	
	public GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms, ArrayList<Stair> linkedLevels){
		super.populateBoard(target, rooms, linkedLevels);
		
		for(int i=0; i<rooms.size(); i++){
			target.placeEntity(target.getTileAt(rooms.get(i)[0]+rooms.get(i)[2]/2, rooms.get(i)[1]+rooms.get(i)[3]/2),
					new Torch(null,Tile.LAYER_PASSIVE_MAP, 10 ), Tile.LAYER_PASSIVE_MAP);
		}
		
		for(int i=0; i<linkedLevels.size(); i++){
			while(true){
				int[] room = rooms.get(Utilities.randInt(0,rooms.size()));
				Tile subject = target.getTileAt(room[0]+Utilities.randInt(1,room[2]-1), room[1]+Utilities.randInt(1,room[3]-1));
				if(subject.isOpen(Tile.LAYER_PASSIVE_MAP)
						&& subject.getToLeft().isOpen(Tile.LAYER_PASSIVE_MAP)){
					target.placeEntity(subject, linkedLevels.get(i), Tile.LAYER_PASSIVE_MAP);
					target.placeEntity(subject.getToLeft(), new LandingPad(subject.getToRight(), Tile.LAYER_PASSIVE_MAP, linkedLevels.get(i).targetFloor), Tile.LAYER_PASSIVE_MAP);
					break;
				}
			}
		}
		return target;
	}
	
}
