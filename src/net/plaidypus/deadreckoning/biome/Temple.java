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
		GameBoard gb = new GameBoard(Utilities.randInt(30,50),Utilities.randInt(30,50));
		
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
		
		new Monster(gb.getTileAt(7, 4),Tile.LAYER_ACTIVE,"res/goblin.entity",1);
		new Monster(gb.getTileAt(7, 5),Tile.LAYER_ACTIVE,"res/goblin.entity",1);
		new Monster(gb.getTileAt(7, 6),Tile.LAYER_ACTIVE,"res/goblin.entity",1);
		new Monster(gb.getTileAt(7, 7),Tile.LAYER_ACTIVE,"res/goblin.entity",1);
		new Monster(gb.getTileAt(7, 8),Tile.LAYER_ACTIVE,"res/goblin.entity",1);

		
		new Monster(gb.getTileAt(9, 4),Tile.LAYER_ACTIVE,"res/goblin.entity",2);
		new Monster(gb.getTileAt(9, 5),Tile.LAYER_ACTIVE,"res/goblin.entity",2);
		new Monster(gb.getTileAt(9, 6),Tile.LAYER_ACTIVE,"res/goblin.entity",2);
		new Monster(gb.getTileAt(9, 7),Tile.LAYER_ACTIVE,"res/goblin.entity",2);
		new Monster(gb.getTileAt(9, 8),Tile.LAYER_ACTIVE,"res/goblin.entity",2);
		//new Torch(gb.getTileAt(0,0),Tile.LAYER_PASSIVE_MAP,20);
		
		
		return gb;
	}

}
