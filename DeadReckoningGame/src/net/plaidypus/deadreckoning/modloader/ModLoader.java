package net.plaidypus.deadreckoning.modloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.professions.Profession;

public class ModLoader {

	private static HashMap<String,URLClassLoader> loadedLoaders = new HashMap<String,URLClassLoader>();
	
	/**
	 * checks to see if the prerequisites are met for the installed modpacks
	 * 
	 * @return an arraylist of files pointing to the directories of 
	 * the modpacks with their dependencies met.
	 */
	public static ArrayList<File> resolveMods(boolean vocal){
		File[] mods = new File("modpacks/").listFiles();
		
		HashMap<String,File> loadedMods = new HashMap<String,File>();
		
		for(int i=0; i<mods.length; i++){
			if(mods[i].getName().contains(".jar")){
				loadedMods.put(mods[i].getName(), mods[i]);
			}
		}
		
		Set<String> fileNames =loadedMods.keySet();
		Iterator<String> iter = fileNames.iterator();
		
		// the list of mods that are properley formatted and have the dependencies met
		ArrayList<File> returnMods = new ArrayList<File>(loadedMods.size());
		
		/* Trashed, due to inability to read insides of jars totally
		 * possible, but I am retarded / lazy and will work on others 'fo now
		while (iter.hasNext()){
			String name = iter.next();
			try{
				//checks the integrity of the mod's file system
				if(!new File("modpacks/"+name+"/biomes").exists() ||
						!new URL("modpacks/"+name+"!/entities") ||
						!new File("modpacks/"+name+"!/items").exists() ||
						!new File("modpacks/"+name+"!/livingEntities").exists() ||
						!new File("modpacks/"+name+"!/professions").exists()
				){	throw new ModLoaderException();	}
				
				File requirements = new File("modpacks/"+name+"/requirements.txt");
				BufferedReader r = new BufferedReader(new FileReader(requirements));
				
				//checks the requirements of the mod
				String requirement = r.readLine();
				boolean reqMet = true;
				while (requirement != null){
					if(!fileNames.contains(requirement)){
						if(vocal){System.err.println("modpack \""+name+"\" dependencies not met. will disregard");}
						reqMet = false;
						break;
					}
					requirement = r.readLine();
				}
				
				if(reqMet){
					if(vocal){System.out.println("modpack \""+name+"\" approved for loading");}
					returnMods.add(loadedMods.get(name));
				}
				
			} catch(IOException e){
				if(vocal){ System.err.println("modpack \""+name+"\" not properly formatted, skipping");}
			} catch (ModLoaderException e) {
				if (vocal){System.err.println("modpack \""+name+"\" not properly formatted, skipping");}
			}
		}
		*/
		
		returnMods.addAll(loadedMods.values());
		
		return returnMods;
	}
	
	/**
	 * loads a modpack
	 * 
	 * really just tells the biome code that a given modpack is available - the
	 * other loaders should be able to work the rest out from there.
	 * 
	 * @param f the directory of the modpack
	 */
	public static void loadModpack(File f){
		System.out.println("loading" + f.getPath());
		String modname = f.getName().replace(".jar", "");
		
		try {
			URL x = f.toURI().toURL();
			URLClassLoader urlLoader = new URLClassLoader(new URL[] {x},ClassLoader.getSystemClassLoader());
			loadedLoaders.put(modname,urlLoader);
			
			JarFile jarFile = new JarFile(f);
			Enumeration<JarEntry> contents = jarFile.entries();
			
			ArrayList<Class> biomeClasses = new ArrayList<Class>(0);
			
			int numProfessions = 0;
			//populates list of biomes for loading
			while (contents.hasMoreElements()){
				JarEntry e = contents.nextElement();
				if(e.getName().contains("/biomes/") && e.getName().endsWith(".class")){
					biomeClasses.add(
							urlLoader.loadClass(e.getName().replace("/", ".").replace(".class", ""))
							);
				}
				if(e.getName().contains("/professions/") && e.getName().endsWith("player.entity")){
					numProfessions++;
				}
			}
			
			//loads biomes
			for(int biome=0; biome<biomeClasses.size() ;biome++){
				Biome b = (Biome)(biomeClasses.get(biome).asSubclass(Biome.class).newInstance());
				b.setLoader(modname);
				Biome.addBiome(b);
			}
			
			//loads custom skills
			//TODO that thing
			
			
			//loads professions
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
	
	/**
	 * loads a list of modpacks
	 * 
	 * @param f Arraylist of Files, pointing to the directories of the modpacks
	 */
	public static void loadModpacks(ArrayList<File> f){
		for(int i=0; i<f.size();i++){
			loadModpack(f.get(i));
		}
	}
	
	
	
}



