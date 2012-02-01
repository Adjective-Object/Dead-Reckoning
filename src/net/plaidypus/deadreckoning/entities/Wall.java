package net.plaidypus.deadreckoning.entities;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Wall extends Entity{

	public Wall(Tile targetTile, int layer){
		super(targetTile, layer);
		this.setTransparent(false);
		targetTile.setTileFace(Tile.TILE_WALL);
	}
	
	public Wall(String stringCode){
		super(stringCode);
	}
	
	public void update(GameContainer gc, int delta) {}
	
	public void updateBoardEffects(GameContainer gc, int delta){
	}
	
	@Override
	public Action chooseAction(GameContainer gc, int delta) {
		return new WaitAction(this);
	}

	public void forceRender(Graphics g, float x, float y) {}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return new Wall(g.getTileAt(Integer.parseInt(toload[1]), Integer.parseInt(toload[2])), Integer.parseInt(toload[3]));
		}
	

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Action> advanceTurn() {
		return new ArrayList<Action>(0);
	}

	@Override
	public boolean isInteractive() {
		return false;
	}

}
