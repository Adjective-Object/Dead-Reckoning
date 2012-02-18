package net.plaidypus.deadreckoning.genrator;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

public class Hemple extends RoomBasedBiome{
	
	public Hemple(){
		super(15);
	}

	@Override
	public GameBoard makeRoom(GameBoard target, int x, int y, int width,
			int height) {
		System.out.println(x+" "+y+" "+width+" "+height);
		for(int a=0; a<width; a++){
			for(int b=0; b<height; b++){
				target.getTileAt(x+a,y+b).setTileFace(Tile.TILE_WALL_RIGHT);
			}
		}
		return target;
	}
}
