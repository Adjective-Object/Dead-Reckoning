package net.plaidypus.deadreckoning;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.entities.Entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

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
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				board[x][y].render(g,x,y);
			}
		}
		
		if(primaryHighlight !=null ){
			g.setColor(primaryHighlightColor);
			g.setLineWidth(2);
			g.drawRect(primaryHighlight.getX()*DeadReckoningGame.tileSize, primaryHighlight.getY()*DeadReckoningGame.tileSize, DeadReckoningGame.tileSize, DeadReckoningGame.tileSize);
			g.setLineWidth(1);
		}
		
		for(int x = 0; x < 25; x++){
			for(int y = 0; y < 25; y++ ){
				if(!board[x][y].isOpen()){
					board[x][y].getEntity().render(g,x,y);
				}
			}
		}
	}
	
	public void updateSelctor(Input i){
		
		if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			primaryHighlight = board[i.getMouseX()/DeadReckoningGame.tileSize][i.getMouseY()/DeadReckoningGame.tileSize];
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
