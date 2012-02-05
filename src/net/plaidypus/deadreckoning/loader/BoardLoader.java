package net.plaidypus.deadreckoning.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.hudelements.GameplayElement;

import org.newdawn.slick.SlickException;

public class BoardLoader {

	public static GameBoard loadBoardFromSave(GameplayElement g, int saveNumber, int floorNumber) throws IOException, SlickException {
		GameBoard b = new GameBoard(g, floorNumber);
		BufferedReader r = new BufferedReader(new FileReader("saves/"+saveNumber+"/floor"+floorNumber+".map"));
		
		b.width=(r.read()-48)*10+r.read()-48;
		r.readLine();
		b.height=(r.read()-48)*10+r.read()-48;
		r.readLine();
		System.out.println("Map Dimensions: "+b.width+","+b.height);
		b.board = new Tile[b.width][b.height];
		for(int y=0; y<b.height; y++){
			for(int x=0; x<b.width; x++){
				int q=(r.read()-48)*10+r.read()-48;
				b.board[x][y]=new Tile(b,x,y,q);
			}
			r.readLine();
		}
		System.out.println(b.board.length);
		for(int i=0; i<b.board.length; i++){
			System.out.println(b.board[i].length);
		}
		
		EntityLoader.loadEntititiesFromSave(b,r);
		
		return b;
		
	}
}
