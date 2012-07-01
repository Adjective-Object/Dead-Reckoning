package net.plaidypus.deadreckoning.modmaker.saveformats;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class BiomeFormat implements SaveFormat{

	String name;
	String makeBoardSource;
	String initSource;
	String tileImage;
	int minDepth=-1;
	int maxDepth=-1;
	
	@Override
	public void writeToSave(File targetLocation) {
		// TODO Auto-generated method stub
		
	}
	
	public SaveFormat makeFrom(File source){
		return null;
	}
	
	public ImageIcon getImageIcon(){
		try {
			if(this.tileImage!=null){
				Image i = ImageIO.read(
						ModContentManager.getWithRelation(this.tileImage)
						)
				.getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING);
				
				return new ImageIcon(i);
			}
			else{
				return new ImageIcon("res/noIcon.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

}
