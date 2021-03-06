package net.plaidypus.deadreckoning.generator;

import java.util.ArrayList;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.utilities.Utilities;

public class Room {
	
	public int x,y,width,height;
	
	public Room (int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	
	public boolean collidesWith(ArrayList<Room> rooms) {
		for(int i=0; i<rooms.size(); i++){
			if(this.collidesWith(rooms.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesWith(Room room){
		return !( this.x+this.width<room.x || this.x>room.x+room.width ||
				this.y+this.height<room.y || this.y>room.y+room.height); 
	}
	
	public Tile getCenter(GameBoard target){
		return target.getTileAt(this.x+this.width/2, this.y+this.height/2);
	}
	
	public Tile getTileIn(GameBoard source){
		return source.getTileAt(Utilities.randInt(this.x,this.x+this.width), Utilities.randInt(this.y,this.y+this.height));
	}
	
	public boolean isInside(Tile t){
		return t.getX()>=this.x && t.getX()<this.x+this.width && t.getY()>=this.y && t.getY()<this.y+this.height;
	}


	public Tile getTileAt(GameBoard source, int i, int j) {
		return source.getTileAt(x+i, y+j);
	}
	
}
