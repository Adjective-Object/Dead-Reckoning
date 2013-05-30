package core.biomes;

import java.util.ArrayList;
import java.util.Random;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LandingPad;
import net.plaidypus.deadreckoning.entities.Stair;
import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.entities.TemplePedestal;
import core.entities.TemplePillar;
import core.entities.TempleTorch;

public class ThroneRoom extends Biome{
	
	public static final int 
		TILE_EMPTY = 4,
		TILE_EMPTY_ALT = 5,
		
		TILE_COBBLE = 21,
		TILE_COBBLE_ALT = 9,
		
		TILE_TOPWALL = 8,
		TILE_TOPWALL_BELOW = 6,
		
		TILE_CARPET_LOW_START = 12,
		TILE_CARPET_LOW_MIDDLE = 19,
		TILE_CARPET_LOW_END = 13,
		TILE_CARPET_MID_START = 14,
		TILE_CARPET_MID_END = 15,
		TILE_CARPET_TOP = 16,
		
		TILE_SHADOW_LEFT = 7,
		TILE_SHADOW_BOT = 25,
		TILE_SHADOW_BOT_ALT = 18,
		TILE_SHADOW_BOTLEFT = 24,

		TILE_LOWBORDER_LEFT = 20,
		TILE_LOWBORDER_BOT = 27,
		TILE_LOWBORDER_BOTLEFT = 26,
		TILE_LOWBORDER_TOPLEFT=2,
		TILE_LOWBORDER_TOP = 3,
		
		TILE_TOPBORDER_TOPLEFT = 10,
		TILE_TOPBORDER_TOP = 11,
		TILE_TOPBORDER_BOTLEFT = 22,
		TILE_TOPBORDER_BOT = 23,
		TILE_THRONE=17;
	
	public ThroneRoom(){
		this.minDepth=-2;//will not appear through normal generation.
		this.maxDepth=-2;
	}
	
	@Override
	public void init() throws SlickException {
		Image tile = ModLoader.loadImage("core/res/bossroom/TILES_THRONEROOM.png");
		this.tileImage = new SpriteSheet(tile,
				DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
		System.out.println(this.tileImage);
	}
	
	@Override
	public GameBoard makeBoard(int depth, ArrayList floorLinks)
			throws SlickException {
		GameBoard board = new GameBoard(40,13,this);
		Random r = new Random();
		
		//fill background
		for(int x=0; x<board.width; x++){
			for(int y=0; y<board.height; y++){
				if(r.nextFloat()<0.1F){
					board.getTileAt(x, y).setTileFace(TILE_EMPTY_ALT);
				}else{
					board.getTileAt(x, y).setTileFace(TILE_EMPTY);
				}
			}
		}
		
		//set back-wall
		for(int x=0; x<board.width; x++){
			board.getTileAt(x, 0).blocking=true;
			board.getTileAt(x, 0).setTileFace(TILE_TOPWALL);
			board.getTileAt(x, 1).setTileFace(TILE_TOPWALL_BELOW);
		}
		
		//draw out carpet
		int carpetheight = board.getHeight()/2;
		board.getTileAt(2, carpetheight).setTileFace(TILE_CARPET_LOW_START);
		for(int x=3; x<=board.width-5; x++){
			board.getTileAt(x, carpetheight).setTileFace(TILE_CARPET_LOW_MIDDLE);
			if(r.nextBoolean()){
				board.getTileAt(x, carpetheight+1).setTileFace(TILE_SHADOW_BOT_ALT);
			} else{
				board.getTileAt(x, carpetheight+1).setTileFace(TILE_SHADOW_BOT);
			}
		}
		
		for(int x=board.width-3;x>=4; x-=4){
			board.placeEntity(
					board.getTileAt(x, carpetheight-4),
					new TemplePedestal(5),
					Tile.LAYER_ACTIVE);
			board.placeEntity(
					board.getTileAt(x, carpetheight+4),
					new TemplePedestal(5),
					Tile.LAYER_ACTIVE);
		}
		
		for(int x=board.width-6;x>=4; x-=2){
			board.getTileAt(x, carpetheight-2).blocking=true;
			board.getTileAt(x, carpetheight+2).blocking=true;
			board.placeEntity(
					board.getTileAt(x, carpetheight-2),
					new TemplePillar(),
					Tile.LAYER_ACTIVE);
			board.placeEntity(
					board.getTileAt(x, carpetheight+2),
					new TemplePillar(),
					Tile.LAYER_ACTIVE);		
			
			board.placeEntity(
					board.getTileAt(x, carpetheight-1),
					new TemplePedestal(2),
					Tile.LAYER_ACTIVE);
			board.placeEntity(
					board.getTileAt(x, carpetheight+1),
					new TemplePedestal(2),
					Tile.LAYER_ACTIVE);
		}
		
		//place tomes
		
		//create throne area
		board.getTileAt(board.width-5, carpetheight).setTileFace(TILE_CARPET_MID_START);
		board.getTileAt(board.width-4, carpetheight).setTileFace(TILE_CARPET_MID_END);
		board.getTileAt(board.width-3, carpetheight).setTileFace(TILE_CARPET_TOP);
		board.getTileAt(board.width-2, carpetheight).setTileFace(TILE_THRONE);

		board.getTileAt(board.width-5, carpetheight-1).setTileFace(TILE_LOWBORDER_LEFT);
		board.getTileAt(board.width-4, carpetheight-1).setTileFace(TILE_COBBLE);//TODO
		board.getTileAt(board.width-3, carpetheight-1).setTileFace(TILE_TOPBORDER_TOPLEFT);
		board.getTileAt(board.width-2, carpetheight-1).setTileFace(TILE_TOPBORDER_TOP);
		
		board.getTileAt(board.width-5, carpetheight-2).setTileFace(TILE_LOWBORDER_TOPLEFT);
		board.getTileAt(board.width-4, carpetheight-2).setTileFace(TILE_LOWBORDER_TOP);
		board.getTileAt(board.width-3, carpetheight-2).setTileFace(TILE_LOWBORDER_TOP);
		board.getTileAt(board.width-2, carpetheight-2).setTileFace(TILE_LOWBORDER_TOP);
		
		board.getTileAt(board.width-5, carpetheight+1).setTileFace(TILE_LOWBORDER_LEFT);
		board.getTileAt(board.width-4, carpetheight+1).setTileFace(TILE_COBBLE);//TODO
		board.getTileAt(board.width-3, carpetheight+1).setTileFace(TILE_TOPBORDER_BOTLEFT);
		board.getTileAt(board.width-2, carpetheight+1).setTileFace(TILE_TOPBORDER_BOT);
		
		board.getTileAt(board.width-5, carpetheight+2).setTileFace(TILE_LOWBORDER_BOTLEFT);
		board.getTileAt(board.width-4, carpetheight+2).setTileFace(TILE_LOWBORDER_BOT);
		board.getTileAt(board.width-3, carpetheight+2).setTileFace(TILE_LOWBORDER_BOT);
		board.getTileAt(board.width-2, carpetheight+2).setTileFace(TILE_LOWBORDER_BOT);

		board.getTileAt(board.width-5, carpetheight+3).setTileFace(TILE_SHADOW_BOT);//TODO
		board.getTileAt(board.width-4, carpetheight+3).setTileFace(TILE_SHADOW_BOT);
		board.getTileAt(board.width-3, carpetheight+3).setTileFace(TILE_SHADOW_BOT);
		board.getTileAt(board.width-2, carpetheight+3).setTileFace(TILE_SHADOW_BOT);
		
		board.placeEntity(
				board.getTileAt(board.width-5, carpetheight-2),
				new TempleTorch(5),
				Tile.LAYER_ACTIVE);
		board.placeEntity(
				board.getTileAt(board.width-5, carpetheight+2),
				new TempleTorch(5),
				Tile.LAYER_ACTIVE);
		
		for(int i=0; i<floorLinks.size(); i++){
			board.placeEntity(
					board.getTileAt(0, carpetheight+i),
					(Stair)(floorLinks.get(i)),
					Tile.LAYER_PASSIVE_MAP);
			Stair l = (Stair)(floorLinks.get(i));
			board.placeEntity(
					board.getTileAt(1, carpetheight+i),
					new LandingPad(l.targetFloor),
					Tile.LAYER_PASSIVE_MAP);
		}
		
		
		return board;
	}

}
