package data.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.DataBaseAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import data.database.dataEntities.QuickEstimationRecord;

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
		Entity NBI = mainNBIAccess.getByID(nodeID);
		NBI.attributes.remove("id");
		int newNodeID = toNBIAccess.insert(NBI);
		
		CocomoEstimationAccess mainCEAccess = new CocomoEstimationAccess();
		CocomoEstimationAccess toCEAccess = new CocomoEstimationAccess(path);
		CocomoEstimationRecord ce = mainCEAccess.getCocomoEstimationByNodeID(nodeID);
		ce.attributes.remove("id");
		ce.set("nodeID", newNodeID);
		toCEAccess.insert(ce);
		
		QuickEstimationAccess mainQEAccess = new QuickEstimationAccess();
		QuickEstimationAccess toQEAccess = new QuickEstimationAccess(path);
		QuickEstimationRecord qe = mainQEAccess.getQuickEstimationByNodeID(nodeID);
		qe.attributes.remove("id");
		qe.set("nodeID", newNodeID);
		toQEAccess.insert(qe);
		
		for(Entity node:mainNBIAccess.getNodesByParentID(nodeID)){
			copyData((Integer)node.get("id"));
		}
	}
}
