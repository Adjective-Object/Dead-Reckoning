package net.plaidypus.deadreckoning.items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.plaidypus.deadreckoning.DeadReckoningGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EtcDrop extends Item{
	
	int number;
	
	public EtcDrop(int itemID, int number) {
		super(itemID,Item.ITEM_ETC);
		this.number=number;
	}

	protected void parseItem(String path) throws IOException, SlickException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		name=reader.readLine();
		description=reader.readLine();
		image=new Image(reader.readLine());
	}

	@Override
	public boolean stacksWith(Item item) {
		if(item.classification==ITEM_ETC){
			return item.itemID == this.itemID;
		}
		return false;
	}
	
	public void render(Graphics g, int x, int y){
		super.render(g,x,y);
		g.drawString(Integer.toString(this.number),x,y);
	}
	
	@Override
	public Item combineWith(Item item) {
		EtcDrop drop = (EtcDrop) item;
		return new EtcDrop(itemID,this.number+drop.number);
	}

}
