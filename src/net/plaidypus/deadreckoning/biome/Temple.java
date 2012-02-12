package net.plaidypus.deadreckoning.biome;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Monster;
import net.plaidypus.deadreckoning.entities.Player;
import net.plaidypus.deadreckoning.entities.Torch;
import net.plaidypus.deadreckoning.professions.Profession;

public class Temple extends Biome{

	@Override
	public GameBoard makeBoard(){
		GameBoard gb = new GameBoard(Utilities.randInt(10,50),Utilities.randInt(10,50));
		
		for(int i=0; i<gb.width; i++){
			gb.getTileAt(i, 0).setTileFace(Tile.TILE_WALL_DOWN);
			gb.getTileAt(i, gb.height-1).setTileFace(Tile.TILE_WALL_UP);
		}
		for(int i=0; i<gb.height; i++){
			gb.getTileAt(0,i).setTileFace(Tile.TILE_WALL_LEFT);
			gb.getTileAt(gb.width-1,i).setTileFace(Tile.TILE_WALL_RIGHT);
		}
		
		gb.getTileAt(0,0).setTileFace(Tile.TILE_WALL_UP_LEFT);
		gb.getTileAt(0,gb.getHeight()-1).setTileFace(Tile.TILE_WALL_DOWN_LEFT);
		gb.getTileAt(gb.getWidth()-1,0).setTileFace(Tile.TILE_WALL_UP_RIGHT);
		gb.getTileAt(gb.getWidth()-1,gb.getHeight()-1).setTileFace(Tile.TILE_WALL_DOWN_RIGHT);
		
		for(int x=0; x<gb.getWidth(); x++){
			for (int y=0; y<gb.getHeight(); y++){
				if(Utilities.randFloat()<=0.01){
					new Torch(gb.getTileAt(x,y),Tile.LAYER_PASSIVE_MAP,Utilities.randInt(2,7));
				}
			}
		}
		
		
		
		return gb;
	}

}
