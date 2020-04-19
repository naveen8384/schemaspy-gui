package org.schemaspygui.schemaspygui;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class About extends JFrame {
	About(){
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		String html = "<center><h1>SchemaSpy</h1>"
				+"Version "+Config.VERSION
				+"<p>Email : hello@naveenkumar.org</p>"
				+"<p>Website : www.naveenkumar.org</p>"
				+"<p><br/></p>"
				+"<p>Icons from iconshock.com</p>"
				+ "</center>";
		
		JEditorPane editorPaneAbout = new JEditorPane();
		editorPaneAbout.setContentType("text/html");
		editorPaneAbout.setText(html);
		editorPaneAbout.setEditable(false);
		getContentPane().add(editorPaneAbout, BorderLayout.CENTER);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		setTitle("About");
		setResizable(false);
		setSize(400,300);
		setLocationRelativeTo(null);
		getContentPane().add(btnClose, BorderLayout.SOUTH);
		setVisible(true);
		
	}

}
