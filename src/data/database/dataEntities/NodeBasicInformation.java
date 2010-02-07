package data.database.dataEntities;


import data.database.dataAccess.NodeBasicInfoAccess;

/**
 * 估算节点基本信息类，与数据表NodeBasicInfor表结构一致
 * @author Administrator
 *
 */
public class NodeBasicInformation extends Entity{

	
	
	public NodeBasicInformation(){
		attributes.put("SLOC", 64000);
		attributes.put("functionPoints", 200);
		attributes.put("teamSize", 5);
		attributes.put("duration", 180);
	}
	
	// 更新基本信息表中的估算类型
	public static void updateEstType(int nodeID, String EstType) {
		NodeBasicInformation nbi = new NodeBasicInformation();
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi = nbi_access.getNodeByID(nodeID);
		nbi.attributes.put("estType", EstType);
		nbi_access.updateNode(nbi);
	}
	
}
