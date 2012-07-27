package net.plaidypus.deadreckoning.modmaker;

import javax.swing.*;

import net.plaidypus.deadreckoning.modmaker.saveformats.ModContentManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ModMaker implements ActionListener{
	
	JFrame frame;
	JTabbedPane mainView;
	
	JMenuItem open, save;
	
	static ModEditor[] views = new ModEditor[]{
		new ModOverview(),
		new BiomeEditor()
	};
	
	/**
	 * creates and shows the GUI
	 */
    private void createAndShowGUI() {
    	
        //Create and set up the window.
        frame = new JFrame("DeadReckoning Mod Making Tool of Awesome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Make the tabbed pane that will contain the various ModMakerViews' contents
        mainView = new JTabbedPane();
        
        for(int i=0; i<views.length; i++){
        	mainView.addTab(views[i].getName(),views[i].makeContents(this));
        }
        
        JMenuBar menuBar =  makeMenu();
        
        //frame.add(menuBar);
        frame.add(mainView);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private JMenuBar makeMenu() {
    	//establish top bar
    	JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        //add to JMenuItems to File
        open = new JMenuItem("open",
                KeyEvent.VK_T);
		open.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_1, ActionEvent.ALT_MASK));
		fileMenu.add(open);
		
		save = new JMenuItem("open",
                KeyEvent.VK_T);
		save.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_2, ActionEvent.ALT_MASK));
		fileMenu.add(save);

        return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	try{
    		for(int i=0; i<views.length; i++){
    			views[i].actionPerformed(this, e);
    		}
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
            	ModContentManager.init();
            	ModMaker m = new ModMaker();
                m.createAndShowGUI();
            }
        });
    }
}