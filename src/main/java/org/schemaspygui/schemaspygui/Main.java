package org.schemaspygui.schemaspygui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new  Runnable() {
			public void run() {
				
				SchemaspyGui gui = new SchemaspyGui();
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);
				
			}
		});
    }
}
