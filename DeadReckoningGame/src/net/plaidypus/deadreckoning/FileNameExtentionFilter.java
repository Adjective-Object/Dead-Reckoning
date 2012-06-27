package net.plaidypus.deadreckoning;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameExtentionFilter implements FilenameFilter {

	String ext;
	
	public FileNameExtentionFilter(String extention){
		ext=extention;
	}
	
	@Override
	public boolean accept(File file, String name) {
		return name.substring( name.lastIndexOf(".") ).equals(ext);
	}

}
