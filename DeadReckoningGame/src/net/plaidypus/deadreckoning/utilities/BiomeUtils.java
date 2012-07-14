package net.plaidypus.deadreckoning.utilities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

public class BiomeUtils {
	
	public static ArrayList<Tile> tileLine(int startx, int starty, int endx, int endy, GameBoard target){
		ArrayList<Tile> tiles = new ArrayList<Tile>(Math.abs(startx-endx)+Math.abs(starty-endy)+1);
		if(startx==endx){
			for(int y=starty; y!=endy; y+=(endy-starty)/Math.abs(endy-starty)){
				tiles.add(target.getTileAt(startx, y));
			}
		}
		else if (starty==endy){
			for(int x=startx; x!=endx; x+=(endx-startx)/Math.abs(endx-startx)){
				tiles.add(target.getTileAt(x,starty));
			}
		}
		return tiles;
	}
	
	public static ArrayList<Tile> tileLine(Tile tileA, Tile tileB, GameBoard target){
		return tileLine(tileA.getX(), tileA.getY(), tileB.getX(), tileB.getY(), target);
	}
	
	public static ArrayList<Tile> tileRect(int startx, int starty, int endx, int endy, GameBoard target){
		ArrayList<Tile> tiles = new ArrayList<Tile>(Math.abs(startx-endx)*2+(Math.abs(starty-endy)+1)*2);
		Utilities.addAllExclusivley(tiles, tileLine(startx, starty, startx, endy, target));
		Utilities.addAllExclusivley(tiles, tileLine(startx, endy, endx, endy, target));
		Utilities.addAllExclusivley(tiles, tileLine(endx, endy, endx, starty, target));
		Utilities.addAllExclusivley(tiles, tileLine(endx, starty, startx, starty, target));
		return tiles;
	}
	
	public static ArrayList<Tile> tileRect(Tile tileA, Tile tileB, GameBoard target){
		return tileRect(tileA.getX(), tileA.getY(), tileB.getX(), tileB.getY(), target);
	}

	public static ArrayList<Tile> getShitPath(int startx, int starty, int endx, int endy, GameBoard target){
		ArrayList<Tile> tilePath = BiomeUtils.tileLine(startx, starty, endx, starty, target);
		tilePath.addAll(BiomeUtils.tileLine(endx, starty, endx, endy, target));
		tilePath.add(target.getTileAt(endx, endy));
		return tilePath;
	}
	
	public static ArrayList<Tile> getShitPath(Tile tileA, Tile tileB, GameBoard target){
		return getShitPath(tileA.getX(), tileA.getY(), tileB.getX(), tileB.getY(), target);
	}
	
	public static ArrayList<Tile> getHalfSteppedPath(int startx, int starty, int endx, int endy, GameBoard target){
		boolean horizontal = Math.abs(startx-endx)>Math.abs(starty-endy);
		int midx =(startx+endx)/2, midy = (starty+endy)/2;
		int[] breakpoints;
		if(horizontal){
			breakpoints = new int[] {
					startx, starty,
					midx, starty,
					midx, endy,
					endx,endy
			};
		}
		else{
			breakpoints = new int[] {
					startx, starty,
					startx, midy,
					endx, midy,
					endx,endy
			};
		}
		
		ArrayList<Tile> tilePath = BiomeUtils.tileLine(startx, starty, endx, starty, target);

		for(int i=0; i<breakpoints.length-2; i+=2){
			tilePath.addAll(BiomeUtils.tileLine(
					breakpoints[i], breakpoints[i+1],
					breakpoints[i+2], breakpoints[i+3], target));
		}
		tilePath.add(target.getTileAt(endx, endy));
		return tilePath;
	}
	
	public static ArrayList<Tile> getHalfSteppedPath(Tile tileA, Tile tileB, GameBoard target){
		return getHalfSteppedPath(tileA.getX(), tileA.getY(), tileB.getX(), tileB.getY(), target);
	}
	
}
