package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GameBoard
{
	
	public ArrayList<Entity> ingameEntities;
	
	Tile[][] board;
	Tile primaryHighlight;
	int width,height;
	
	static final Color primaryHighlightColor = new Color(255,75,23);
	
	public GameBoard(int width,int height)
	{
		board = new Tile[width][height];
		this.width=width;
		this.height=height;
		ingameEntities=new ArrayList<Entity>(0);
	}
	
	public void placeEntity(int x, int y, Entity e)
	{
		board[x][y].placeEntity(e);
		ingameEntities.add(e);
	}
	
	public void clearTile(int x, int y){
		ingameEntities.remove(board[x][y].getEntity());
		board[x][y].removeEntity();
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
	
	public void render(Graphics g, float xoff, float yoff)
	{
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				board[x][y].render(g,x+xoff/DeadReckoningGame.tileSize,y+yoff/DeadReckoningGame.tileSize);
			}
		}
		
		if(primaryHighlight !=null ){
			g.setColor(primaryHighlightColor);
			g.setLineWidth(2);
			g.drawRect(primaryHighlight.getX()*DeadReckoningGame.tileSize+xoff, primaryHighlight.getY()*DeadReckoningGame.tileSize+yoff, DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
			g.setLineWidth(1);
		}
		
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				if(!board[x][y].isOpen()){
					board[x][y].getEntity().render(g,x+xoff/DeadReckoningGame.tileSize,y+yoff/DeadReckoningGame.tileSize);
				}
			}
		}
	}
	
	public void updateSelctor(Input i, float xOff, float yOff){
		
		if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			primaryHighlight = board[Utilities.limitTo((i.getMouseX()-(int)xOff)/DeadReckoningGame.tileSize,0,this.getWidth())][Utilities.limitTo((i.getMouseY()-(int)yOff)/DeadReckoningGame.tileSize,0,this.getHeight())];
		}
		
		if(primaryHighlight!=null){
			if(i.isKeyPressed(Input.KEY_LEFT)){
				primaryHighlight = primaryHighlight.getToLeft();
			}
			if(i.isKeyPressed(Input.KEY_RIGHT)){
				primaryHighlight = primaryHighlight.getToRight();
			}
			if(i.isKeyPressed(Input.KEY_UP)){
				primaryHighlight = primaryHighlight.getToUp();
			}
			if(i.isKeyPressed(Input.KEY_DOWN)){
				primaryHighlight = primaryHighlight.getToDown();
			}
		}
	}
	
	public void updateAllTiles(GameContainer gc, int delta){
		for(int y=0;y<this.height;y++){
			for(int x=0;x<this.width;x++){
				if( !board[x][y].isOpen()){
					board[x][y].getEntity().update(gc, delta);
				}
			}
		}
	}
	
	public void highlightSquare(int x, int y){
		board[x][y].setHighlighted(1);
	}
	
	public boolean isTileHighlighted(int x, int y){
		return board[x][y].getHighlighted()==1;
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
				board[x][y].setHighlighted(0);
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
