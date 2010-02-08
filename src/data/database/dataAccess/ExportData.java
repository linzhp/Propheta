package data.database.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.database.dataEntities.Entity;

public class ExportData {
	private String path;
	public ExportData(String path){
		this.path = path;
	}
	
	public void createSchema() throws Exception{
		DataBaseAccess mainDB = new DataBaseAccess();
		DataBaseAccess toDB = new DataBaseAccess(path);
		ResultSet rs = mainDB.statement.executeQuery("select sql from sqlite_master " +
				"where name in ('nodeBasicInfo','cocomoestimation', 'quickEstimation')");
		while(rs.next()){
			toDB.statement.executeUpdate(rs.getString("sql"));
		}
	}
	
	public void copyData(int nodeID) throws SQLException {
		NodeBasicInfoAccess mainNBIAccess = new NodeBasicInfoAccess();
		NodeBasicInfoAccess toNBIAccess = new NodeBasicInfoAccess(path);
		toNBIAccess.insert(mainNBIAccess.getByID(nodeID));
		for(Entity node:mainNBIAccess.getNodesByParentID(nodeID)){
			copyData((Integer)node.get("id"));
		}
	}
}
