package net.plaidypus.deadreckoning.genrator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.Utilities;
import net.plaidypus.deadreckoning.board.GameBoard;

public class DungeonMap {
	
	private int depth;
	
	public DungeonMap(int depthDungeon){
		setDepth(depthDungeon);
	}

	public GameBoard makeBoard(int depth) throws SlickException {
		ArrayList<String> topass = new ArrayList<String>(0);
		if(depth!=0){
			topass.add("floor"+(depth-1)+".map");
		}
		if(depth!=this.depth){
			topass.add("floor"+(depth+1)+".map");
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
