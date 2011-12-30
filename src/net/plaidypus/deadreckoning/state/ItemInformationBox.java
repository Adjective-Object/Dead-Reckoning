package net.plaidypus.deadreckoning.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import net.plaidypus.deadreckoning.items.Item;

public class ItemInformationBox {
	
	int width, height, divideHeight, border;
	
	static Color background=new Color(60,40,50), foreground=new Color(55,45,45);
	
	public ItemInformationBox(int width, int height, int divideHeight, int borderSize){
		this.width=width;
		this.height=height;
		this.divideHeight=divideHeight;
		this.border=borderSize;
	}
	
	public void parseItem(Item P){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.setColor(background);
		g.fillRect(x,y,width,height);
		g.setColor(foreground);
		g.fillRect(x+border,y+border,width-2*border,divideHeight);
		g.fillRect(x+border,y+divideHeight+border*2,width-border*2,height-divideHeight-border);
		
		
	}
}


