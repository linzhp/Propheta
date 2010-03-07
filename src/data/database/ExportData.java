package data.database;

import java.sql.ResultSet;
import java.sql.SQLException;


import data.database.dataAccess.DataBaseAccess;

public class ExportData extends DataMigration{
	public ExportData(String path){
		toPath = path;
		fromPath = DataBaseAccess.MAIN_DB_PATH;
	}
	
	public void createSchema() throws Exception{
		DataBaseAccess mainDB = new DataBaseAccess(fromPath);
		DataBaseAccess toDB = new DataBaseAccess(toPath);
		ResultSet rs = mainDB.statement.executeQuery("select sql from sqlite_master " +
				"where name in ('nodeBasicInfo','cocomoestimation', 'quickEstimation')");
		while(rs.next()){
			toDB.statement.executeUpdate(rs.getString("sql"));
		}
	}
	
	public void copyData(int nodeID) throws SQLException{
		copyData(nodeID, -1);
	}
}
