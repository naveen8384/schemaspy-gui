package org.schemaspygui.schemaspygui;

import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class Config {
	public static boolean enableLookAndFeel = true;
	public static final String VERSION = "123";
	public static final String BUILD = "";

	public static ComboBoxModel<String> getDatabaseTypes() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		File folder = new File("resources/types");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName().replace(".properties", "");
				model.addElement(name.toUpperCase());
			}
		}
		//model.addElement("mysql");
		return model;
	}

}
