package net.plaidypus.deadreckoning.genrator;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.board.GameBoard;

public class DungeonMap {
	
	private int depth;
	
	public DungeonMap(int depthDungeon){
		setDepth(depthDungeon);
	}

	public GameBoard makeBoard(int i) throws SlickException {
		return Biome.getRandomBiome().makeBoard("floor"+(i-1)+".map", "floor"+(i+1)+".map");
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
