package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Stair;

import org.newdawn.slick.SlickException;

public abstract class RoomBasedBiome extends Biome{
	
	int minRoomSize,maxRoomSize,minTunnelLen,maxTunnelLen,numRooms;
	
	public RoomBasedBiome(int numRooms){
		this.numRooms=numRooms;
		minRoomSize = 5;
		maxRoomSize=20;
		minTunnelLen=5;
		maxTunnelLen=15;
	}
	
	public abstract GameBoard makeRoom(GameBoard target, int x, int y, int width, int height);
	
	public GameBoard makeBoard(int width, int height, ArrayList<int[]> rooms){
		GameBoard g = new GameBoard(width,height);
		for(int i=0; i<rooms.size(); i++){
			makeRoom(g,rooms.get(i)[0],rooms.get(i)[1],rooms.get(i)[2],rooms.get(i)[3]);
		}
		return g;
	}
	
	public GameBoard makeBoard(int depth, ArrayList<Stair> floorLinks)
			throws SlickException {
		ArrayList<int[]> rooms = new ArrayList<int[]>(0); // x,y,width,height
		
		ArrayList<Integer> rowHeights = new ArrayList<Integer>(0),
			rowWidths = new ArrayList<Integer>(0);
		
		rowWidths.add(0);
		rowHeights.add(0);
		
		int currentRow=0;
		
		for(int i=0; i<numRooms; i++){
			if(Utilities.randFloat()<=0.2){
				currentRow++;
				rowWidths.add(0);
				rowHeights.add(0);
			}
			
			int nWidth=Utilities.randInt(minRoomSize,maxRoomSize),
				nHeight=Utilities.randInt(minRoomSize,maxRoomSize);
			int nX=rowWidths.get(currentRow), nY=0;
			for(int p=0; p<=currentRow; p++){
				nY+=rowHeights.get(p);
			}
			
			int tunnel = +Utilities.randInt(minTunnelLen, maxTunnelLen);
			if(rowHeights.get(currentRow)<nHeight+tunnel){
				rowHeights.set(currentRow,nHeight+tunnel);
			}
			rowWidths.set(currentRow,rowWidths.get(currentRow)+nWidth+tunnel);
			
			rooms.add(new int[] {nX,nY,nWidth,nHeight});
			
		}
		
		int height = 0;
		for(int p=0; p<rowHeights.size(); p++){
			height+=rowHeights.get(p);
		}
		
		int width = 0;
		for(int p=0; p<rowWidths.size(); p++){
			if(rowWidths.get(p)>width){
				width=rowWidths.get(p);
			}
		}
		
		System.out.println(rowWidths+" "+width+" && "+rowHeights+" "+height);
		
		GameBoard b =makeBoard(width,height,rooms);
		b.depth=depth;
		
		return b;
		
		
	}
	
}
