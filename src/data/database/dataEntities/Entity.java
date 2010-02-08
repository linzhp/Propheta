package data.database.dataEntities;

import java.util.HashMap;

public class Entity {
	public HashMap<String, Object> attributes = new HashMap<String, Object>();

	public Object get(String attr){
		return attributes.get(attr);
	}
	
	public void set(String attr, Object value){
		attributes.put(attr, value);
	}

}
