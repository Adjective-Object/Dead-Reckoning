package net.plaidypus.deadreckoning.modloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
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

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.exceptions.ErrorType;
import net.plaidypus.deadreckoning.exceptions.ModLoadException;
import net.plaidypus.deadreckoning.filters.HiddenFileFilter;
import net.plaidypus.deadreckoning.generator.Biome;
import net.plaidypus.deadreckoning.statmaster.Profession;

public class ModLoader {

	private static HashMap<String, URLClassLoader> loadedLoaders = new HashMap<String, URLClassLoader>();

	/**
	 * checks to see if the prerequisites are met for the installed modpacks
	 * 
	 * @return an arraylist of files pointing to the directories of the modpacks
	 *         with their dependencies met.
	 * 
	 *         TODO determine the order modpacks must be loaded so no errors
	 *         happen
	 * @throws IOException
	 */
	public static ArrayList<File> resolveMods(boolean vocal) throws SlickException {
		
		HiddenFileFilter filter = new HiddenFileFilter();
		File[] mods = new File("modpacks/").listFiles(filter);
		if(mods.length == 0)
		{
			mods = new File[0];
			throw new SlickException("No modpacks found.");
		}
				
		HashMap<String, File> uncountedMods = new HashMap<String, File>();
		HashMap<String, ArrayList<String>> modReqs = new HashMap<String, ArrayList<String>>();
		
		try{
			for (int i = 0; i < mods.length; i++) {
				if (mods[i].getName().contains(".jar")) {
					String name = mods[i].getName().split("\\.")[0];
					uncountedMods.put(name, mods[i]);
	
					URL x = mods[i].toURI().toURL();
					URLClassLoader urlLoader = new URLClassLoader(new URL[] { x },
							ClassLoader.getSystemClassLoader());
					InputStream in = urlLoader.getResourceAsStream(name
							+ "/requirements.txt");
					BufferedReader b = new BufferedReader(new InputStreamReader(in));
					String requirement = b.readLine();
					ArrayList<String> tempReqs = new ArrayList<String>(0);
					while (requirement != null) {
						tempReqs.add(requirement);
						requirement = b.readLine();
						modReqs.put(name, tempReqs);
					}
				}
			}
		} catch (MalformedURLException e){
			throw new SlickException ("Malformed URL? lolwut I dunno how this could even be thrown. name your mods right next time?",e);
		} catch (IOException e) {
			throw new SlickException ("IOException. You're probably missing requirements.txt.",e);
		}

		ArrayList<File> returnMods = new ArrayList<File>(uncountedMods.size());

		returnMods.addAll(uncountedMods.values());

		return returnMods;
	}

	/**
	 * loads a modpack
	 * 
	 * really just tells the biome code that a given modpack is available - the
	 * other loaders should be able to work the rest out from there.
	 * 
	 * @param f
	 *            the directory of the modpack
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public static void loadModpack(File f) throws SlickException {
		String modname = f.getName().replace(".jar", "");

		try {
			URL x = f.toURI().toURL();
			URLClassLoader urlLoader = new URLClassLoader(new URL[] { x },
					ClassLoader.getSystemClassLoader());
			loadedLoaders.put(modname, urlLoader);

			JarFile jarFile = new JarFile(f);
			Enumeration<JarEntry> contents = jarFile.entries();

			@SuppressWarnings("rawtypes")
			ArrayList<ArrayList<Class>> classes = new ArrayList<ArrayList<Class>>(0);

			int numProfessions = 0;
			String[] classKeys = new String[] { "/biomes/", "/skills/",
					"/entities/", "/statuses/" };
			// populates list of biomes for loading
			while (contents.hasMoreElements()) {
				JarEntry e = contents.nextElement();

				for (int i = 0; i < classKeys.length; i++) {
					classes.add(new ArrayList<Class>(0));
					if (e.getName().contains(classKeys[i])
							&& e.getName().endsWith(".class")) {
						classes.get(i).add(
								urlLoader.loadClass(e.getName()
										.replace("/", ".")
										.replace(".class", "")));
					}
				}
				if (e.getName().contains("/professions/")
						&& e.getName().endsWith("player.entity")) {
					numProfessions++;
				}

			}

			// initializes custom components
			// handles special cases of biomes
			for (int i = 0; i < classes.size(); i++) {
				for (int c = 0; c < classes.get(i).size(); c++) {
					Log.info("Initializing "
							+ classes.get(i).get(c).getCanonicalName());
					@SuppressWarnings("unchecked")
					DeadReckoningComponent e = (DeadReckoningComponent) classes
							.get(i).get(c)
							.asSubclass(DeadReckoningComponent.class)
							.newInstance();
					e.init();
					if (classKeys[i].equals("/biomes/")) {
						Biome b = (Biome) e;
						b.setParentMod(modname);
						Biome.addBiome(b.getClass().getCanonicalName(),b);
					}
				}
			}

			// loads professions
			// handes special cases of professions
			for (int prof = 0; prof < numProfessions; prof++) {
				Profession p = new Profession(modname, prof,
						urlLoader.getResourceAsStream(modname + "/professions/"
								+ prof + "/tree1.txt"),
						urlLoader.getResourceAsStream(modname + "/professions/"
								+ prof + "/tree2.txt"),
						urlLoader.getResourceAsStream(modname + "/professions/"
								+ prof + "/tree3.txt"));
				Profession.addProfession(p);

			}

		} catch (Exception e) {
			throw new SlickException("Error loading mod "+modname, e);
		}

	}

	public static URLClassLoader getModpackLoader(String modpackName) {
		return loadedLoaders.get(modpackName);
	}

	public static URLClassLoader getLoaderFor(String classpath) {
		return getModpackLoader(classpath.split("\\.")[0]);
	}

	public static Class<? extends DeadReckoningComponent> loadClass(String line) throws SlickException {
		try{
			URLClassLoader l = getLoaderFor(line);
			if (l != null) {
				return l.loadClass(line).asSubclass(DeadReckoningComponent.class);
			} else {
				Log.error("the appropriate loader is not present. attempting to use System class loader.");
				return ClassLoader.getSystemClassLoader().loadClass(line).asSubclass(DeadReckoningComponent.class);
			}
		} catch (ClassNotFoundException e) {
			throw new SlickException("Cannot find the class indicated by "+line+".",e);
		}
	}

	/**
	 * loads a list of modpacks
	 * 
	 * @param f
	 *            Arraylist of Files, pointing to the directories of the
	 *            modpacks
	 */
	public static void loadModpacks(ArrayList<File> f) throws SlickException{
		for (int i = 0; i < f.size(); i++) {
			loadModpack(f.get(i));
		}
	}

	public static Image loadImage(String imagepath) throws SlickException{
		String modpack = imagepath.split("(/|\\\\)")[0];
		try {
			return new Image(
					TextureLoader.getTexture("PNG",ModLoader.getModpackLoader(modpack)
									.getResourceAsStream(imagepath)));
		} catch (IOException e) {
			return new Image("res/noSkill.png");
		} catch (NullPointerException e ){
			Log.error("can't load image "+imagepath+" in "+modpack+" with "+ModLoader.getModpackLoader(modpack));
			return null;
		}
	}

}
