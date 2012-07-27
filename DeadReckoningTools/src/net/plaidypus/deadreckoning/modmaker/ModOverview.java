package net.plaidypus.deadreckoning.modmaker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import net.plaidypus.deadreckoning.modmaker.saveformats.ModContentManager;
import net.plaidypus.deadreckoning.modmaker.saveformats.SaveFormat;

public class ModOverview implements ModEditor
{
	
	DefaultListModel[] models;
	
	@Override
	public JScrollPane makeContents(ModMaker parent) {		
		
		JPanel internalPanel = new JPanel(new GridLayout(4,2,12,30));
		internalPanel.setPreferredSize(new Dimension(950,662));
		
		//populating the listmodels from which the lists will be built

		models = new DefaultListModel[7];
		for(int i=0; i<7; i++){
			models[i]=new DefaultListModel();
		}
		populateListModels();
		
		
		//Biome, items, entities, livingentitites, professions, skills, resources
		String[] names = {"Biomes","Items","entities","Living Entities","Professions", "Skills", "Resources"};
		JList[] lists = new JList[7];
		for(int i=0;i<7;i++){
			TitledBorder titledBorder = BorderFactory.createTitledBorder(names[i]);
			JPanel p = new JPanel(new BorderLayout());
			JScrollPane scroll = new JScrollPane();
			lists[i]=new JList(models[i]);
			scroll.add(lists[i]);
			p.setName(names[i]);
			p.add(scroll);
			p.setBorder(titledBorder);
			
			internalPanel.add(p);
		}
				
		JScrollPane mainScroll = new JScrollPane(internalPanel);
		mainScroll.getVerticalScrollBar().setUnitIncrement(10);
		return mainScroll;
		
	}

	private void populateListModels() {
		ArrayList<ArrayList<? extends SaveFormat>> saves = new ArrayList<ArrayList<? extends SaveFormat>>(0);
		saves.add(ModContentManager.biomes);
		
		for(int i=0; i<saves.size(); i++){
			models[i].removeAllElements();
			for(int x=0; x<saves.get(i).size(); x++){
				models[i].addElement(saves.get(i).get(x).getImageIcon());
			}
		}
	}

	@Override
	public void actionPerformed(ModMaker parent, ActionEvent e)
			throws Exception {
		
	}

	@Override
	public String getName() {
		return "Overview";
	}

}
