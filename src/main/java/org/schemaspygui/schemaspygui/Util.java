package org.schemaspygui.schemaspygui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Properties;

public class Util {
	public static void openUrl(String url){
		File file = new File(url);

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(file.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static Session getSession(Properties properties) {
		return new Session(Config.VERSION, new Date(), properties);
		
	}
	
	public static Properties getProperties(String path) {
		Properties p = new Properties();
		
		try {
			FileInputStream fi = new FileInputStream(new File(path));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Session session = (Session) oi.readObject();
			p = session.getProperties();
			oi.close();
			fi.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Reading Properties"+p);
		return p;
	}
	
	public static void putProperties(Properties p,String path) {
		System.out.println("Saving Properties"+p);
		try {
			FileOutputStream f = new FileOutputStream(new File(path));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(new Session(Config.VERSION, new Date(), p));
			o.close();
			f.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
