package fr.warlog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {
	private static long lastLoad;
	private static long reloadTimeout=10000;
	private static Properties params;
	
	
	/**
	 * Load warlog.properties in war, then in exec directory, then in /etc.
	 * the last load override the first one.
	 * the exec directory is often the server home,which contains the "conf" folder:
	 * tomcat   :/usr/share/tomcat/conf
	 * jboss    :../domain/configuration
	 * glassfish:[..]/domain/config
	 */
	public static synchronized void loadParams(){
		params = new Properties();
		try (InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("warlog.properties")){
			params.load(resourceAsStream);
		} catch (IOException e) {
		}
		//try to load tomcat configuration
		tryLoadFile("conf/warlog.properties");
		//try to load glassfish configuration
		tryLoadFile("config/warlog.properties");
		//try to load jboss configuration
		tryLoadFile("configuration/warlog.properties");
		//try to load std linux configuration
		tryLoadFile("/etc/warlog.properties");
		try{
			reloadTimeout=Long.parseLong("configs.timeout");
		}catch (NumberFormatException e){
			
		}
		//mark app as launched
		if ("true".equals(params.get("warlog.lock"))){
			try {
				new File("warlog.lock").createNewFile();
			} catch (IOException e) {
			}
		}
	}

	private static void tryLoadFile(String filename) {
		try (InputStream in = new FileInputStream(filename)){
			params.load(in);
		} catch (IOException e) {
		}
	}
	
	public static String getParam(String name, String defaultValue){
		if(params == null || System.currentTimeMillis()-lastLoad>reloadTimeout){
			lastLoad=System.currentTimeMillis();
			loadParams();
		}
		return params.getProperty(name, defaultValue);
	}

}
