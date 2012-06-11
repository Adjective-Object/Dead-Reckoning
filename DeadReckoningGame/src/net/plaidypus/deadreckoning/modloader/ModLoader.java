package net.plaidypus.deadreckoning.modloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.newdawn.slick.SlickException;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.professions.Profession;

public class ModLoader {

	private static HashMap<String,URLClassLoader> loadedLoaders = new HashMap<String,URLClassLoader>();
	
	/**
	 * checks to see if the prerequisites are met for the installed modpacks
	 * 
	 * @return an arraylist of files pointing to the directories of 
	 * the modpacks with their dependencies met.
	 * 
	 *TODO determine the order modpacks must be loaded so no errors happen
	 * @throws IOException 
	 */
	public static ArrayList<File> resolveMods(boolean vocal) throws IOException{
		File[] mods = new File("modpacks/").listFiles();
		
		HashMap<String,File> uncountedMods = new HashMap<String,File>();
		HashMap<String,ArrayList<String>> modReqs = new HashMap<String,ArrayList<String>>();
		
		for(int i=0; i<mods.length; i++){
			if(mods[i].getName().contains(".jar")){
				String name = mods[i].getName().split("\\.")[0];
				System.out.println(name);
				uncountedMods.put(name, mods[i]);
				
				URL x = mods[i].toURI().toURL();
				URLClassLoader urlLoader = new URLClassLoader(new URL[] {x},ClassLoader.getSystemClassLoader());
				InputStream in = urlLoader.getResourceAsStream(name+"/requirements.txt");
				BufferedReader b = new BufferedReader(new InputStreamReader(in));
				String requirement = b.readLine();
				ArrayList<String> tempReqs = new ArrayList<String>(0);
				while(requirement !=null){
					tempReqs.add(requirement);
					requirement=b.readLine();
				}
				modReqs.put(name,tempReqs);
			}
		}
		
		Set<String> fileNames =uncountedMods.keySet();
		Iterator<String> iter = fileNames.iterator();
		
		// the list of mods that are properley formatted and have the dependencies met
		ArrayList<File> returnMods = new ArrayList<File>(uncountedMods.size());
		
		//TODO
		
		
		returnMods.addAll(uncountedMods.values());
		
		return returnMods;
	}
	
	/**
	 * loads a modpack
	 * 
	 * really just tells the biome code that a given modpack is available - the
	 * other loaders should be able to work the rest out from there.
	 * 
	 * @param f the directory of the modpack
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void loadModpack(File f) throws IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		System.out.println("loading " + f.getPath());
		String modname = f.getName().replace(".jar", "");
		
		try {
			URL x = f.toURI().toURL();
			URLClassLoader urlLoader = new URLClassLoader(new URL[] {x},ClassLoader.getSystemClassLoader());
			loadedLoaders.put(modname,urlLoader);
			
			JarFile jarFile = new JarFile(f);
			Enumeration<JarEntry> contents = jarFile.entries();
			
			
			ArrayList<ArrayList<Class>> classes = new ArrayList<ArrayList<Class>>(0);
			
			int numProfessions = 0;
			String[] classKeys = new String[]{"/biomes/","/skills/","/entities/"};
			//populates list of biomes for loading
			while (contents.hasMoreElements()){
				JarEntry e = contents.nextElement();
				
				for(int i=0; i<classKeys.length;i++){
					classes.add(new ArrayList<Class>(0));
					if(e.getName().contains(classKeys[i]) && e.getName().endsWith(".class")){
						classes.get(i).add(
								urlLoader.loadClass(e.getName().replace("/", ".").replace(".class", ""))
								);
					}
				}
				if(e.getName().contains("/professions/") && e.getName().endsWith("player.entity")){
					numProfessions++;
				}
				
			}
			
			//initializes custom components
			//handles special cases of biomes
			for(int i=0; i<classes.size(); i++){
				for(int c=0; c<classes.get(i).size() ;c++){
					System.out.println("Initializing entity "+classes.get(i).get(c).getCanonicalName());
					DeadReckoningComponent e = (DeadReckoningComponent) classes.get(i).get(c).asSubclass(DeadReckoningComponent.class).newInstance();
					e.init();
					if(classKeys[i].equals("/biomes/")){
						Biome b = (Biome) e;
						b.setParentMod(modname);
						Biome.addBiome(b);
					}
				}
			}
			
			
			//loads professions
			//handes special cases of professions
			for(int prof = 0; prof<numProfessions; prof++){
				Profession p = new Profession(modname,prof,
						urlLoader.getResourceAsStream(modname+"/professions/"+prof+"/tree1.txt"),
						urlLoader.getResourceAsStream(modname+"/professions/"+prof+"/tree2.txt"),
						urlLoader.getResourceAsStream(modname+"/professions/"+prof+"/tree3.txt"));
				Profession.addProfession(p);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static URLClassLoader getModpackLoader(String modpackName){
		return loadedLoaders.get(modpackName);
	}
	
	public static URLClassLoader getLoaderFor(String classpath){
		return getModpackLoader(classpath.split("\\.")[0]);
	}
	
	public static Class loadClass(String line) throws ClassNotFoundException {
		URLClassLoader l =getLoaderFor(line);
		if(l!=null){
			return l.loadClass(line);
		}
		else{
			return ClassLoader.getSystemClassLoader().loadClass(line);
		}
	}
	
	/**
	 * loads a list of modpacks
	 * 
	 * @param f Arraylist of Files, pointing to the directories of the modpacks
	 */
	public static void loadModpacks(ArrayList<File> f){
		for(int i=0; i<f.size();i++){
			try {
				loadModpack(f.get(i));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
}



