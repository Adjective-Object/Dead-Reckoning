package net.plaidypus.deadreckoning;

import java.io.File;
import java.io.FilenameFilter;

public class SaveFilter implements FilenameFilter {

	public SaveFilter() {

	}

	public boolean accept(File file, String name) {
		return name.startsWith("SAVE") && file.isDirectory();
	}

}