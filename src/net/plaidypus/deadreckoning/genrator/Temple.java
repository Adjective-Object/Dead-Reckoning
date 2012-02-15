package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.entities.Torch;

public class Temple extends Biome{

	@Override
	public GameBoard makeBoard(int depth, ArrayList<String>  linkedLevels) throws SlickException{
		GameBoard gb = new GameBoard(Utilities.randInt(10,50),Utilities.randInt(10,50));
		
		gb.depth=depth;
		
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
		
		new Torch().init();
		new Stair().init();
		
		for(int x=0; x<gb.getWidth(); x++){
			for (int y=0; y<gb.getHeight(); y++){
				if(Utilities.randFloat()<=0.01){
					new Torch(gb.getTileAt(x,y),Tile.LAYER_PASSIVE_MAP,Utilities.randInt(2,7));
				}
			}
		}
		
		for(int i=0; i<linkedLevels.size(); i++){
			int x=Utilities.randInt(0,gb.getWidth()), y=Utilities.randInt(0, gb.getHeight());
			if(gb.getTileAt(x, y).isOpen(Tile.LAYER_PASSIVE_MAP)){
				new Stair(gb.getTileAt(x,y),Tile.LAYER_PASSIVE_MAP,linkedLevels.get(i));
			}
		}
		
		return gb;
	}

}
