package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;
import java.util.LinkedList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Wall;

import org.newdawn.slick.SlickException;

public abstract class RoomBasedBiome extends Biome{
	
	int numRooms, roomSizeMin=5, roomSizeMax=10;
	
	public RoomBasedBiome(int numRooms){
		this.numRooms = numRooms;
	}
	
	public abstract GameBoard populateBoard(GameBoard target, ArrayList<int[]> rooms, ArrayList<Stair> linkedLevels);
	
	public void placeWallsOnNullBorders(GameBoard g){
		for(int x=0; x<g.getWidth(); x++){
			for(int y = 0; y<g.getHeight(); y++){
				if(g.getTileAt(x, y).getTileFace()==Tile.TILE_NULL&& 
						(g.getTileAt(Utilities.limitTo(x+1, 0, g.getWidth()), y).getTileFace()!=Tile.TILE_NULL ||
						g.getTileAt(Utilities.limitTo(x-1, 0, g.getWidth()), y).getTileFace()!=Tile.TILE_NULL ||
						g.getTileAt(x, Utilities.limitTo(y-1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL ||
						g.getTileAt(x, Utilities.limitTo(y+1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL||
						g.getTileAt(Utilities.limitTo(x-1, 0, g.getWidth()), Utilities.limitTo(y+1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL||
						g.getTileAt(Utilities.limitTo(x+1, 0, g.getWidth()), Utilities.limitTo(y+1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL||
						g.getTileAt(Utilities.limitTo(x-1, 0, g.getWidth()), Utilities.limitTo(y-1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL||
						g.getTileAt(Utilities.limitTo(x+1, 0, g.getWidth()), Utilities.limitTo(y-1, 0, g.getHeight()) ).getTileFace()!=Tile.TILE_NULL) ){
					//g.placeEntity(g.getTileAt(x, y), new Wall(), Tile.LAYER_ACTIVE);
				}
			}
		}
	}
	
	public void outlineRoom(GameBoard target, int[] room){
		for(int x=1; x<room[2]-1; x++){
			target.getTileAt(room[0]+x,room[1]).setTileFace(Tile.TILE_WALL_UP);
			target.getTileAt(room[0]+x,room[1]+room[3]-1).setTileFace(Tile.TILE_WALL_DOWN);
		}
		for(int y=1; y<room[3]-1; y++){
			target.getTileAt(room[0],room[1]+y).setTileFace(Tile.TILE_WALL_LEFT);
			target.getTileAt(room[0]+room[2]-1,room[1]+y).setTileFace(Tile.TILE_WALL_RIGHT);
		}
		
		target.getTileAt(room[0],room[1]).setTileFace(Tile.TILE_WALL_UP_LEFT);
		target.getTileAt(room[0],room[1]+room[3]-1).setTileFace(Tile.TILE_WALL_DOWN_LEFT);
		target.getTileAt(room[0]+room[2]-1,room[1]).setTileFace(Tile.TILE_WALL_UP_RIGHT);
		target.getTileAt(room[0]+room[2]-1,room[1]+room[3]-1).setTileFace(Tile.TILE_WALL_DOWN_RIGHT);
		
		for(int x=1; x<room[2]-1;x++){
			for(int y=1; y<room[3]-1;y++){
					target.getTileAt(room[0]+x,room[1]+y).setTileFace(Tile.TILE_EMPTY);
			}
		}
	}
	
	public GameBoard drawCooridor(GameBoard target, int[] roomA, int[] roomB){
		int x = roomA[0]+roomA[2]/2;
		int y = roomA[1]+roomA[3]/2;
		int xDiff =  (roomB[0]+roomB[2]/2)-x;
		int yDiff =  (roomB[1]+roomB[3]/2)-y;
		
		ArrayList<int[]> points = new ArrayList<int[]>(Math.abs(xDiff)+Math.abs(yDiff));
		
		if(Math.abs(xDiff)>=Math.abs(yDiff)){
			points.addAll(addLine(target,x,y,xDiff/2,true));
			points.addAll(addLine(target,x+xDiff/2,y,yDiff,false));
			points.addAll(addLine(target,x+xDiff/2,y+yDiff,xDiff-xDiff/2,true));}
		else{
			points.addAll(addLine(target,x,y,yDiff/2,false));
			points.addAll(addLine(target,x,y+yDiff/2,xDiff,true));
			points.addAll(addLine(target,x+xDiff,y+yDiff/2,yDiff-yDiff/2,false));}
		
		drawOnBoard(target,points);
		
		return target;
		
		
	}
	
	public ArrayList<int[]> addLine(GameBoard target, int ax, int ay, int length, boolean horizontal){
		
		ArrayList<int[]> retPoints = new ArrayList<int[]>(Math.abs(length));
		
		for(int i=0; i!=length; i+=length/Math.abs(length)){
				
			if(horizontal){
				retPoints.add(new int[] {ax+i ,  ay });
			}
			else{
				retPoints.add(new int[] {ax ,  ay+i });
			}
			
		}
		return retPoints;
	}
	
	public void drawOnBoard(GameBoard target, ArrayList<int[]> points){
		boolean willdr=true;
		Tile previous = null;
		Tile t = null;
		
		for(int i=0; i<points.size(); i++){
			
			previous = t;
			
			t=target.getTileAt(points.get(i)[0], points.get(i)[1]);
			
			int draw = 0;
			if( t.getToDown().getTileFace()==Tile.TILE_SPECIAL ) {draw++;}
			if( t.getToUp().getTileFace()==Tile.TILE_SPECIAL ) {draw++;}
			if( t.getToLeft().getTileFace()==Tile.TILE_SPECIAL ) {draw++;}
			if( t.getToRight().getTileFace()==Tile.TILE_SPECIAL ) {draw++;}
			
			if(draw == 0 && !willdr){
				willdr=true;
				previous.setTileFace(Tile.TILE_SPECIAL);
			}
			if(willdr){
				t.setTileFace(Tile.TILE_SPECIAL);
			}
			if(draw>1){
				willdr=false;
			}
			
		}
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

	public void genericPopulation(GameBoard target, ArrayList<int[]> rooms, ArrayList<Stair> linkedLevels) {
		for(int i=0; i<rooms.size()-1; i++){
			drawCooridor(target,rooms.get(i), rooms.get(i+1));
		}
		
		for(int i=0; i<rooms.size(); i++){
			outlineRoom(target, rooms.get(i));
		}
		
		//placing stairs
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
		
		placeWallsOnNullBorders(target);
	}
	
}
