package data.database.dataEntities;

import java.util.ArrayList;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;

/**
 * 估算节点，与数据表NodeBasicInfor表结构一致
 * 主要包括节点信息存储，数据库交互，treeview显示功能
 * @author Administrator
 *
 */
public class EstimateNode extends Entity{

	private EstimateNode parent; //父节点
	private ArrayList<EstimateNode> children; //子节点集合
	
	
	private void init(){
		attributes.put("id", -1);
		attributes.put("parentID", -1);  //此处parentID和parent.id重复，但基于目前的数据插入模式，这种重复必须存在,在修改parent值时一定要注意parentID同步
		attributes.put("name", null);
		attributes.put("SLOC", 64000);
		attributes.put("functionPoints", 200);
		attributes.put("teamSize", 5);
		attributes.put("duration", 180);
		
		parent=null;
	}
	
	public EstimateNode(){
		init();
	}
	
	
	public EstimateNode(String name){
		init();
		this.set("name", name);
	}
	/**
	 *  从数据库中读取节点所有的子节点
	 * @param node
	 */
	public void loadChildren(){
		initNodeChildren(this);
	}
	
	private static void initNodeChildren(EstimateNode node){
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		ArrayList<Entity> childNodes=nbi_access.getNodesByParentID(node.getId());
		if(childNodes.size()==0){
			return;
		}else{
			for(int i=0;i<childNodes.size();i++){				
				EstimateNode newNode=(EstimateNode)childNodes.get(i);				
				newNode.setParent(node);
				node.getChildren().add(newNode);
				
				initNodeChildren(newNode);
			}
		}	
	}
		
	public void setId(int id){
		this.set("id", id);
	}
	
	public int getId(){
		return Integer.parseInt(this.get("id").toString());
	}
	
	public void setParentID(int parentID){
		this.set("parentID", parentID);
	}
	
	public int getParentID(){
		return Integer.parseInt(this.get("parentID").toString());
	}
	
	public void setName(String str){
		this.set("name", str);
	}
		
	public String getName(){
		return this.get("name").toString();
	}
	
	public void setSLOC(int SLOC){
		this.set("SLOC", SLOC);
	}
	
	public int getSLOC(){
		return Integer.parseInt(this.get("SLOC").toString());
	}
	public void setFunctionPoints(int functionPoints){
		this.set("functionPoints", functionPoints);
	}
	
	public int getFunctionPoints(){
		return Integer.parseInt(this.get("functionPoints").toString());
	}
	public void setTeamSize(int teamSize){
		this.set("teamSize", teamSize);
	}
	
	public int getTeamSize(){
		return Integer.parseInt(this.get("teamSize").toString());
	}
	public void setDuration(int duration){
		this.set("duration", duration);
	}
	
	public int getDuration(){
		return Integer.parseInt(this.get("duration").toString());
	}
	
	public ArrayList<EstimateNode> getChildren(){
		if(children == null)
			children=new ArrayList<EstimateNode>();			
		return this.children;
	}
	
	public void setParent(EstimateNode parent){
		this.parent=parent;
		if(this.parent==null){
			this.setParentID(-1);
		}else{
			this.setParentID(this.parent.getId());
		}
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
		System.out.println("insert: "+node.get("name"));
		node.setParentID(this.getId());
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		int nodeID=nbi_access.insert(node);
		
		//为新建节点设置估算输入输出的默认值并保存到数据库中
		QuickEstimationRecord qer=new QuickEstimationRecord();
		qer.set("nodeID",nodeID);
		QuickEstimationAccess qe_access=new QuickEstimationAccess();
		qe_access.insert(qer);
		
		CocomoEstimationRecord cer=new CocomoEstimationRecord();
		cer.set("nodeID",nodeID);
		CocomoEstimationAccess ce_access=new CocomoEstimationAccess();
		ce_access.insert(cer);
		
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
		nbi_access.deleteByID(childNode.getId());
		
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
		if(this.getChildren().size()>0){
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
		if((Integer)attributes.get("parentID") == -1){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/*
	public int getSLOC() {
		int SLOC = 0;
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		if (this.isLeaf())
			SLOC = (Integer)nbi_access.getByID(this.getId()).get("SLOC");
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
			functionPoints = (Integer)nbi_access.getByID(this.getId()).get("functionPoints");
		else {
			//子系统的规模计算为各模块规模之和，这样计算可能会有点问题
			ArrayList<EstimateNode> children = this.getChildren();
			for (EstimateNode child : children)
				functionPoints += child.getFunctionPoints();
		}

		return functionPoints;
	}
	*/

	public String getEstType(){
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		String estType = (String)nbi_access.getByID(this.getId()).get("estType");

		return estType;
	}

}
