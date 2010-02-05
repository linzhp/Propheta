package data.database.dataAccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import estimation.entity.EstimateNode;

public class ExportData extends DataBaseAccess {
	public ExportData(String path){
		super(path);
	}
	
	public void createSchema() throws Exception{
		DataBaseAccess mainDB = new DataBaseAccess();
		ResultSet rs = mainDB.statement.executeQuery("select sql from sqlite_master " +
				"where name in ('nodeBasicInfo','cocomoEstimation', 'quickEstimation')");
		while(rs.next()){
			this.statement.executeUpdate(rs.getString("sql"));
		}
	}
	
	public void copyData(EstimateNode node) {
		
	}
}
