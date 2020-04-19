package org.schemaspygui.schemaspygui;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class Session implements Serializable {
	private static final long serialVersionUID = -8749048078348529697L;
	private String version;
	private Date savedDate;
	private Properties properties;
	
	
	
	public Session(String version, Date savedDate, Properties properties) {
		super();
		this.version = version;
		this.savedDate = savedDate;
		this.properties = properties;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getSavedDate() {
		return savedDate;
	}
	public void setSavedDate(Date savedDate) {
		this.savedDate = savedDate;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
	

}
