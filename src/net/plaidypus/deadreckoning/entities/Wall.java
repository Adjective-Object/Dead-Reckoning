package net.plaidypus.deadreckoning.entities;

import net.plaidypus.deadreckoning.GameBoard;
import net.plaidypus.deadreckoning.Tile;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.actions.WaitAction;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Wall extends Entity{

	public Wall(Tile targetTile){
		super(targetTile);
		interactive = false;
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
		return new WaitAction(getLocation());
	}

	public void forceRender(Graphics g, float x, float y) {}

	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		return new Wall(g.getTileAt(Integer.parseInt(toload[1]), Integer.parseInt(toload[2])));
		}
	

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}

}
