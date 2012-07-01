package net.plaidypus.deadreckoning.modmaker.saveformats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ModContentManager {
	
	public static File source;
	
	public static ArrayList<BiomeFormat> biomes;
	
	public static void init(){
		ModContentManager.biomes= new ArrayList<BiomeFormat>(0);
	}
	
	public static void updateTo(File f){
		
	}
	
	public static File getWithRelation(String path) throws IOException{
		return new File(source.getCanonicalPath()+"/"+path);
	}
}
