package data.database.dataAccess;

import java.sql.ResultSet;

import estimation.entity.EstimateNode;

public class ExportData extends DataBaseAccess {
	public ExportData(String path, EstimateNode node){
		super(path);
	}
	
	public void createSchema() throws Exception{
		DataBaseAccess mainDB = new DataBaseAccess();
		ResultSet rs = mainDB.statement.executeQuery("select * from sqlite_master");
		while(rs.next()){
			System.out.println(rs.getString("sql"));
//			this.statement.executeUpdate(rs.getString("sql"));
		}
	}
	
	public void copyData(EstimateNode node) {
		
	}
}
