package net.plaidypus.deadreckoning;

import net.plaidypus.deadreckoning.entities.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameBoard
{
	
	Tile[][] board;
	Tile primaryHighlight;
	int width,height;
	
	static final Color primaryHighlightColor = new Color(252,125,73);
	
	public GameBoard(int width,int height)
	{
		board = new Tile[width][height];
		this.width=width;
		this.height=height;
	}
	
	public void placeEntity(int x, int y, Entity e)
	{
		board[x][y].placeEntity(e);
	}
	
	public void init() throws SlickException
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++ )
			{
				board[x][y] = new Tile(this,x,y);
			}
		}
	}
	
	public Tile getTileAt(int x, int y){
		return board[x][y];
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				board[x][y].render(g,x,y);
			}
		}
		
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				if(!board[x][y].isOpen()){
					board[x][y].getEntity().render(g,x,y);
				}
			}
		}
		
		if(primaryHighlight !=null ){
			g.setColor(primaryHighlightColor);
			g.setLineWidth(2);
			g.drawRect(primaryHighlight.getX()*DeadReckoningGame.tileSize, primaryHighlight.getY()*DeadReckoningGame.tileSize, DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
			g.setLineWidth(1);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		for(int y=0;y<this.height;y++){
			for(int x=0;x<this.width;x++){
				board[x][y].update(gc,sbg,delta);
			}
		}
	}
	
	public void highlightSquare(int x, int y){
		board[x][y].setHighlighted(true);
	}
	
	public boolean isTileHighlighted(int x, int y){
		return board[x][y].isHighlighted;
	}
	
	public void setPrimairyHighlight(int x, int y){
		this.primaryHighlight = board[x][y];
	}
	
	public void setPrimairyHighlight(Tile t){
		this.primaryHighlight = t;
	}
	
	public Tile getPrimairyHighlight(){
		return primaryHighlight;
	}
	
	public void clearPrimaryHighlight(){
		this.primaryHighlight=null;
	}
	
	public void clearHighlightedSquares(){
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				board[x][y].setHighlighted(false);
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
}
