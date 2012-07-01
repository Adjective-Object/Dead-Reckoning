package net.plaidypus.deadreckoning.modmaker.saveformats;

import java.io.File;

import javax.swing.ImageIcon;

public interface SaveFormat{
	
	public SaveFormat makeFrom(File source);
	
	public void writeToSave(File targetLocation);
	
	public ImageIcon getImageIcon();
	
	public String getName();
}
