/*
 * 
 */
package net.plaidypus.deadreckoning;

import java.io.File;
import java.io.FilenameFilter;

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
	public boolean accept(File file, String name) {
		return name.startsWith("SAVE") && file.isDirectory();
	}

}