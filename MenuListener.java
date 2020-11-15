package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuListener {
	
	public MenuListener (){
	}

	public MenuListener(final JFrame frame,JMenuItem openMenuItem, JMenuItem saveMenuItem, JMenuItem runMenuItem, JMenuItem stopMenuItem,
			JMenuItem newMenuItem, JMenuItem aboutMenuItem) {
		aboutMenuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		    	JOptionPane.showMessageDialog(frame,
		    		    "Assignment 05 Team Members: \n Nevedita Kailasam \n Kanti Nizampatnam \n Saloni");
		    }
		});
	}

}
