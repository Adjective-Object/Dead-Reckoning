package net.plaidypus.deadreckoning.Items;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Item {

	int itemID, count;
	Image image;
	String name, description;

	public Item(int itemID, int count) {
		this.itemID = itemID;
		try {
			parseItem("res/" + Integer.toString(itemID) + ".item");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void parseItem(String path) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		name=reader.readLine();
		description=reader.readLine();
		image=new Image(reader.readLine());
		count=Integer.parseInt(reader.readLine());
	}
	
	private void parseItem (BufferedReader r){
		
	}
}
