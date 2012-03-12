package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.Stair;

public class DungeonMap {
	
	private int depth;
	
	public DungeonMap(int depthDungeon){
		setDepth(depthDungeon);
	}

	public GameBoard makeBoard(int depth) throws SlickException {
		ArrayList<Stair> topass = new ArrayList<Stair>(0);
		if(depth!=0){
			topass.add(new Stair(null,Tile.LAYER_PASSIVE_MAP,"floor"+(depth-1)+".map",Stair.UP));
		}
		if(depth!=this.depth){
			topass.add(new Stair(null,Tile.LAYER_PASSIVE_MAP,"floor"+(depth+1)+".map",Stair.DOWN));
		}
		return Biome.getRandomBiome().makeBoard(depth, topass );
	}
	
	public String getFloorName(int depth){
		return "floor"+depth+".map";
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}
}
