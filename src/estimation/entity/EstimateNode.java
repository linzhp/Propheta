package estimation.entity;

import java.util.ArrayList;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.NodeBasicInformation;
import data.database.dataEntities.QuickEstimationRecord;

/**
 * the node in the tree view
 * @author Administrator
 *
 */
public class EstimateNode{

	private int id=-1;
	private String name=null;
	private EstimateNode parent=null;
	private ArrayList<EstimateNode> children=new ArrayList<EstimateNode>();
	
	public EstimateNode(String name){
		this.name=name;
	}
	
	public EstimateNode(String name, EstimateNode parent){
		this.name=name;
		this.parent=parent;
	}
	
	
	public void setId(int id){
		this.id=id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setName(String str){
		this.name=str;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public void setChildren(ArrayList<EstimateNode> children){
		this.children =children;
	}
	
	public ArrayList<EstimateNode> getChildren(){
		return this.children;
	}
	
	public void setParent(EstimateNode parent){
		this.parent=parent;
	}
	
	public EstimateNode getParent(){
		return this.parent;
	}
	
	
	/**
	 * 增加子节点
	 * @param node
	 */
	public void addChild(EstimateNode node){
		//插入数据库，获取分配的节点ID
		System.out.println("insert: "+node.getName());
		NodeBasicInformation nbi=new NodeBasicInformation();
		nbi.set("name",node.getName());
		nbi.set("parentID",this.getId());
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		int nodeID=nbi_access.insertNode(nbi);
		
		//为新建节点设置估算输入输出的默认值并保存到数据库中
		QuickEstimationRecord qer=new QuickEstimationRecord();
		qer.setNodeID(nodeID);
		QuickEstimationAccess qe_access=new QuickEstimationAccess();
		qe_access.insertQuickEstimation(qer);
		
		CocomoEstimationRecord cer=new CocomoEstimationRecord();
		cer.setNodeID(nodeID);
		CocomoEstimationAccess ce_access=new CocomoEstimationAccess();
		ce_access.insertCocomoEstimation(cer);
		
		node.setId(nodeID);
		node.setParent(this);
		this.children.add(node);
	}

	
	/**
	 * 删除子节点
	 * @param childNode 子节点
	 */
	public void removeChild(EstimateNode childNode){
		System.out.println("delete:	"+childNode.getName());
		
		//从数据库中删除记录
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.deleteNodeByNodeID(childNode.getId());
		
		//删除对应的估算记录
		QuickEstimationAccess qe_access=new QuickEstimationAccess();
		qe_access.deleteQuickEstimationByNodeID(childNode.getId());
		
		CocomoEstimationAccess ce_access=new CocomoEstimationAccess();
		ce_access.deleteCocomoEstimationByNodeID(childNode.getId());
		
		//从父节点中删除
		childNode.setParent(null);
		this.children.remove(childNode);
		
		// 递归删除其子节点
		if(childNode.hasChildren()){
			ArrayList<EstimateNode> nodes=childNode.getChildren();
			for(int i=0;i<nodes.size();i++){
				removeChild(nodes.get(i));
			}
		}else{
			return;
		}		
	}
	
	public void removeAllChildren(){
		for(int i=0;i<this.children.size();i++){
			this.children.get(i).setParent(null);
		}
		this.children.clear();
	}
	
	
	/**
	 * 重命名节点
	 * @param newName 节点新名称
	 */
	public void renameNode(String newName){
		
		System.out.println("rename:	"+this.getName()+"	"+newName);
		//更新数据库
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.updateNodeName(this.getId(), newName);
		
		//更新节点
		this.setName(newName);
	}
	
	
	public boolean hasChildren() {
		if(this.children.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 判断谁否存在同名的子节点
	 * @param nodeName 节点名称
	 * @return
	 */
	public boolean isChildExist(String nodeName){
		if(this.hasChildren()==true){
			boolean isExist=false;
			ArrayList<EstimateNode> nodes=this.getChildren();
			for(int i=0;i<nodes.size();i++){
				if(nodes.get(i).getName().equals(nodeName)){
					isExist=true;
					break;
				}
			}
			return isExist;
		}else{
			return false;
		}
	} 

	
	public boolean isLeaf() {
		if (this.hasChildren()==false){
			return true;
		}else{
			return false;
		}
	}

	
	public boolean isRoot() {
		if(this.parent==null){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	public int getSLOC() {
		int SLOC = 0;
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		if (this.isLeaf())
			SLOC = (Integer)nbi_access.getNodeByID(this.getId()).get("SLOC");
		else {
			//子系统的规模计算为各模块规模之和，这样计算可能会有点问题
			ArrayList<EstimateNode> children = this.getChildren();
			for (EstimateNode child : children)
				SLOC += child.getSLOC();
		}

		return SLOC;
	}
	
	public int getFunctionPoints() {
		int functionPoints = 0;
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		if (this.isLeaf())
			functionPoints = (Integer)nbi_access.getNodeByID(this.getId()).get("functionPoints");
		else {
			//子系统的规模计算为各模块规模之和，这样计算可能会有点问题
			ArrayList<EstimateNode> children = this.getChildren();
			for (EstimateNode child : children)
				functionPoints += child.getFunctionPoints();
		}

		return functionPoints;
	}

	public String getEstType(){
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		String estType = (String)nbi_access.getNodeByID(this.getId()).get("estType");

		return estType;
	}
	
	
	public void fromNodeBasicInfo(NodeBasicInformation nbi){
		this.id=(Integer)nbi.get("nodeID");
		this.name=(String)nbi.get("name");
		
	}
}
