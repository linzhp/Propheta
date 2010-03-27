package data.database.dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataEntities.Entity;

public class ConstantsAccess extends DataBaseAccess {
	public void set(String name, double value) throws SQLException {
		ArrayList<Entity> records = findAllWhere("name='" + name+"'");
		Entity record;
		if (records.size() == 0) {
			record = new Entity();
			record.set("name", name);
			record.set("value", value);
			insert(record);
		} else {
			record = records.get(0);
			record.set("name", name);
			record.set("value", value);
			update(record);
		}
	}

	public Double get(String name) throws SQLException {
		ArrayList<Entity> records = findAllWhere("name='" + name +"'");
		if (records.size() == 0) {
			return null;
		} else {
			return (Double)records.get(0).get("value");
		}

	}
}
