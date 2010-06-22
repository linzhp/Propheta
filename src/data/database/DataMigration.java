package data.database;

import java.sql.SQLException;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import data.database.dataEntities.QuickEstimationRecord;

public class DataMigration {
	protected String fromPath;
	protected String toPath;
	
	public int copyData(int fromNodeID, int newParentID) throws SQLException {
		NodeBasicInfoAccess fromNBIAccess = new NodeBasicInfoAccess(fromPath);
		NodeBasicInfoAccess toNBIAccess = new NodeBasicInfoAccess(toPath);
		Entity NBI = fromNBIAccess.getByID(fromNodeID);
		NBI.attributes.remove("id");
		NBI.set("parentID", newParentID);
		int newNodeID = toNBIAccess.insert(NBI);
		
		CocomoEstimationAccess fromCEAccess = new CocomoEstimationAccess(fromPath);
		CocomoEstimationAccess toCEAccess = new CocomoEstimationAccess(toPath);
		CocomoEstimationRecord ce = fromCEAccess.getCocomoEstimationByNodeID(fromNodeID);
		ce.attributes.remove("id");
		ce.set("nodeID", newNodeID);
		toCEAccess.insert(ce);
		
		QuickEstimationAccess fromQEAccess = new QuickEstimationAccess(fromPath);
		QuickEstimationAccess toQEAccess = new QuickEstimationAccess(toPath);
		QuickEstimationRecord qe = fromQEAccess.getQuickEstimationByNodeID(fromNodeID);
		qe.attributes.remove("id");
		qe.set("nodeID", newNodeID);
		toQEAccess.insert(qe);
		
		for(Entity node:fromNBIAccess.getNodesByParentID(fromNodeID)){
			copyData((Integer)node.get("id"), newNodeID);
		}
		return newNodeID;
	}
}
