/*
 * 
 */
package net.plaidypus.deadreckoning.save;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * The Class SaveFilter.
 * 
 * just a filename filter used to enumerate the saves in the default location.
 */
public class SaveFilter implements FilenameFilter {

	/**
	 * Instantiates a new save filter.
	 */
	public SaveFilter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File file, String name) {
		try {
			File f = new File(file.getCanonicalPath()+"/"+name+"/");
			return f.isDirectory() && fileContains(f, "saveInformation.txt") && fileContains(f, "player.txt");
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean fileContains(File f, String fileName){
		String[] names = f.list();
		for (int i=0;i <names.length; i++){
			if(names[i].equals(fileName)){ return true;}
		}
		return false;
	}

}