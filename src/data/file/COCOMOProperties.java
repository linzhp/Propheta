package data.file;

public class COCOMOProperties{
	private static final String PATH = "properties/COCOMO.properties";
	
	public static String readValue(String key){
		return PropertyFile.readValue(PATH, key);
	}
	
}
