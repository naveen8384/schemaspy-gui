package org.schemaspygui.schemaspygui;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageUtil {
	public static ImageIcon getResizedIcon(String path,int size) {
		ImageIcon icon = null;
		try {
		Image img = new ImageIcon(path).getImage();
		Image newimg = img.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		}catch(Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return icon;
	}

	public static ImageIcon getIcon(String path) {
		ImageIcon icon = null;
		try {
		Image img = new ImageIcon(path).getImage();
		Image newimg = img.getScaledInstance(200, 4, java.awt.Image.SCALE_DEFAULT);
		icon = new ImageIcon(newimg);
		}catch(Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return icon;
	}
}
