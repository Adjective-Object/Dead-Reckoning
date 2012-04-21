package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Door;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Monster;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.professions.StatMaster;

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
		
		
		for(int i=0; i<rooms.size(); i++){//TODO this still does not work
			int[] room = rooms.get(i);
			for(int x=room[0]; x<room[0]+room[2]; x++){
				Tile t = target.getTileAt(x, Utilities.limitTo(room[1]-1,0,target.getHeight()));
				if(t.getX()!=0 && t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
				t = target.getTileAt(x, Utilities.limitTo(room[1]+room[3],0,target.getHeight()));
				if(t.getX()!=target.getWidth() && t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
			}
			for(int y=room[1];y<room[1]+room[3]; y++){
				Tile t = target.getTileAt(Utilities.limitTo(room[0]-1,0,target.getWidth()),y);
				if(t.getY()!=0 && t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
				t = target.getTileAt(Utilities.limitTo(room[0]+room[2],0,target.getWidth()),y);
				if(t.getY()!=target.getHeight() &&  t.getTileFace()!=Tile.TILE_NULL){
					target.placeEntity(t, new Door(t,Tile.LAYER_ACTIVE), Tile.LAYER_ACTIVE);
				}
			}
		}
		
		
		for(int i=0; i<rooms.size(); i++){
			int s=0;
			while(s<1){
				Tile t = target.getTileAt((int)(rooms.get(i)[0]+rooms.get(i)[2]*Utilities.randFloat()), (int)(rooms.get(i)[1]+rooms.get(i)[3]*Utilities.randFloat()));
				if(t.isOpen(Tile.LAYER_ACTIVE)){
					s++;
					target.placeEntity(t, new Monster(t,Tile.LAYER_ACTIVE,"res/goblin.entity",
							new StatMaster(50,50,4,4,4,4),Entity.ALLIGN_HOSTILE), Tile.LAYER_ACTIVE);
				}
			}
			
			target.placeEntity(target.getTileAt(rooms.get(i)[0]+rooms.get(i)[2]/2, rooms.get(i)[1]+rooms.get(i)[3]/2),
					new Torch(null,Tile.LAYER_PASSIVE_MAP, Utilities.randInt(4,6) ), Tile.LAYER_PASSIVE_MAP);
		}
		
		return target;
	}
	
}
