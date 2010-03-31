package data.file;

import java.util.Properties;

public class Languages extends PropertyFile {
	private static final String PATH = "properties/languages.properties";
	
	public static String readValue(String key){
		return PropertyFile.readValue(PATH, key);
	}
	
	public static String[] getLanguages(){
		Properties properties = PropertyFile.readProperties(PATH);
		return properties.stringPropertyNames().toArray(new String[properties.size()]);
	}
}
