package net.plaidypus.deadreckoning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OptionsHandler {

	public static int resolution = 0, frameRate = 60;
	public static boolean verticalSynch = false, stretchScreen=false;
	
	public static final int[][] resolutions = new int[][] {
			new int[] {800,600},
			new int[] {900,700},
	};
	
	public static String getResolution(int res){
		int[] r = resolutions[res];
		return r[0]+" x "+r[1];
	}
	
	public static void loadSettings(){
		File configFile = new File("config.txt");
		if(configFile.exists()){
			try {
				BufferedReader r = new BufferedReader(new FileReader(configFile));
				resolution=Integer.parseInt(r.readLine());
				frameRate=Integer.parseInt(r.readLine());
				verticalSynch="true".equals(r.readLine());
				stretchScreen="true".equals(r.readLine());
				r.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			resolution = 0;
			frameRate = 60;
			verticalSynch = false;
			stretchScreen=false;
		}
	}
	
	public static void saveSettings(){
		File configFile = new File("config.txt");
		try{
			BufferedWriter w = new BufferedWriter(new FileWriter(configFile));
			w.write(Integer.toString(resolution)); 	w.newLine();
			w.write(Integer.toString(frameRate));	w.newLine();
			if(verticalSynch){ w.write("true");}
			else{ w.write("false");}
			w.newLine();
			if(stretchScreen){ w.write("true");}
			else{ w.write("false");}
			w.close();
		} catch (IOException e) {
			
		}
		
	}

	public static int getResolutionX() {
		return resolutions[resolution][0];
	}
	
	public static int getResolutionY() {
		return resolutions[resolution][1];
	}
}
