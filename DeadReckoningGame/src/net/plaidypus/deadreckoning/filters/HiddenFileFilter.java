package net.plaidypus.deadreckoning.filters;

import java.io.File;
import java.io.FilenameFilter;

public class HiddenFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		if (name.startsWith("."))
		{
			return false;
		}
		return true;
	}

}
