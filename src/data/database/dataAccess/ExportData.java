package data.database.dataAccess;

import java.sql.ResultSet;

import estimation.entity.EstimateNode;

public class ExportData extends DataBaseAccess {
	public ExportData(String path){
		super(path);
	}
	
	public void createSchema() throws Exception{
		DataBaseAccess mainDB = new DataBaseAccess();
		ResultSet rs = mainDB.statement.executeQuery("select name, sql from sqlite_master where type = 'table'");
		while(rs.next()){
			if(rs.getString("name").equals("sqlite_sequence"))
				continue;
			this.statement.executeUpdate(rs.getString("sql"));
		}
	}
	
	public void copyData(EstimateNode node) {
		
	}
}
