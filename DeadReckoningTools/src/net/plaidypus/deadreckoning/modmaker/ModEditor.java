package net.plaidypus.deadreckoning.modmaker;

import java.awt.Component;
import java.awt.event.ActionEvent;

public interface ModEditor {
	
	public Component makeContents(ModMaker parent);
	
	public void actionPerformed(ModMaker parent, ActionEvent e) throws Exception;
	
	
	
	
	public String getName();
}
