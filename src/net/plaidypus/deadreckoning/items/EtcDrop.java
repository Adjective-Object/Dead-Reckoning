package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EtcDrop extends Item{

	public EtcDrop(int itemID) {
		super(itemID);
		// TODO Auto-generated constructor stub
	}

	protected void parseItem(String path) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		name=reader.readLine();
		description=reader.readLine();
		image=new Image(reader.readLine());
	}

}
