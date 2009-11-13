package file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyFile {
	public PropertyFile() {
	}

	// Read value based on the value of key
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// Reads a enumeration of keys from a file
	public static Enumeration getKeys(String filePath) {
		Enumeration en;
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				// for testing purpose
				//System.out.println(key + property);
				return en;
			}
			return en;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	} 

	// get the all the keys and values as a Object Properties
	public static Properties readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
		} catch (Exception ex) {
		}
		return props;
	}

	// Write to specific property file
	public static void writeProperties(String filePath, String parameterKey,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// read key value pair from file input stream
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			/* 
			 * * Calls the Hashtable method put. Provided for parallelism with
			 * the getProperty // method. * Enforces use of strings for property
			 * keys and values. * The // value returned is the result of the
			 * Hashtable call to put. * *
			 */
			prop.setProperty(parameterKey, parameterValue);
			/* 
			 * * Writes this property list (key and element pairs) in this
			 * Properties table to * the output stream in a format suitable for
			 * loading into a Properties table * using the load method *
			 */
			prop.store(fos, "Last Update, key:" + parameterKey + ",value: "
					+ parameterValue);
		} catch (Exception ex) {
			System.err.println("Visit " + filePath + " for updating "
					+ parameterKey + " value error");
		}
	}

	// Remove one record based on the key specified
	public static void removeProperty(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			props.remove(key);
			// create a new file output stream to hold the property file in which
			// entry has been deleted.
			OutputStream fos = new FileOutputStream(filePath);
			props.store(fos, "Last Update," + new Date() + " Deletion: key:"
					+ key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Update record based on key specified
	public static void updateProperty(String filePath, String key, String value) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			props.setProperty(key, value);
			OutputStream fos = new FileOutputStream(filePath);
			props.store(fos, "Last Update, key:" + key + ",value: " + value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		System.out.println(readValue("properties\\piCSBSG.properties","EteamSize.5"));

	}
}