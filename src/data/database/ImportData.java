package data.database;

import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataAccess.DataBaseAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.Entity;
import data.database.dataEntities.EstimateNode;

public class ImportData extends DataMigration {
	public ImportData(String path)
	{
		fromPath = path;
		toPath = DataBaseAccess.MAIN_DB_PATH;
	}
	
	public void copyData(EstimateNode parent) throws SQLException{
		NodeBasicInfoAccess fromNBIAccess = new NodeBasicInfoAccess(fromPath);
		ArrayList<Entity> rootNodes = fromNBIAccess.getAllRootNodes();
		NodeBasicInfoAccess toNBIAccess = new NodeBasicInfoAccess(toPath);
		for(Entity node:rootNodes)
		{
			int newID = copyData((Integer)node.get("id"), parent.getId());
			EstimateNode estimateNode = (EstimateNode)toNBIAccess.getByID(newID);
			parent.getChildren().add(estimateNode);
			estimateNode.setParent(parent);
			estimateNode.loadChildren();
		}
	}
}
