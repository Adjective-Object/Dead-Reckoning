package net.plaidypus.deadreckoning.modmaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

public class BiomeEditor implements ModEditor{
	
	File imageFile;
	JLabel imageName;
	JFileChooser imageFileChooser;
	JButton chooseImageButton;
	
	public BiomeEditor(){
	}
	
	@Override
	public JPanel makeContents(ModMaker parent) {
		
		//drawing the left of the menu
		JPanel left = new JPanel (new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		left.setMinimumSize(new Dimension(100,762));
		left.setBackground(Color.gray);
		
		imageFileChooser = new JFileChooser();
		imageFileChooser.setFileFilter(new ImageFileFilter());
		
		imageName = new JLabel("...");
		chooseImageButton = new JButton("Choose File");
		chooseImageButton.addActionListener(parent);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=100;
		c.gridheight=15;
		left.add(chooseImageButton,c.clone());
		c.gridy=1;
		left.add(imageName,c.clone());
		c.gridy=2;
		c.gridheight=732;
		
		
		//drawing the right of the panel
		JPanel right = new JPanel ();
		right.setMinimumSize(new Dimension(100,762));
		
		//put left and right into a split pane, and then dump into panel P and return;
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,left,right);
		splitPane.setPreferredSize(new Dimension(1024,762));
		splitPane.setDividerLocation(200);
		
		
		JPanel p = new JPanel();
		p.add(splitPane);
		
		return p;
	}

	@Override
	public void actionPerformed(ModMaker  parent, ActionEvent e) throws IOException {
		if (e.getSource() == chooseImageButton) {
	        int returnVal = imageFileChooser.showOpenDialog(chooseImageButton);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            imageFile=imageFileChooser.getSelectedFile();
	            imageName.setText(imageFile.getName());
	        }
		}
	}
	
	@Override
	public String getName(){
		return "Biome";
	}

}


class ImageFileFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		String ext="";
		if(f.getName().contains(".")){
			ext = f.getName().substring(f.getName().lastIndexOf('.'));
		}
		System.out.println(ext);
		return (f.isDirectory() && ext.equals("")) || (ext.matches(".(p|P)(n|N)(g|G)"));
	}

	@Override
	public String getDescription() {
		return "PNG Images";
	}
	
	
	
}
