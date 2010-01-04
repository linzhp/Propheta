package entity;

import java.util.ArrayList;

import dataManager.dataAccess.NodeBasicInfoAccess;
import dataManager.dataEntities.NodeBasicInformation;

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
		NodeBasicInformation nbi=new NodeBasicInformation();
		nbi.setName(node.getName());
		nbi.setParentID(this.getId());
		nbi.setIsRoot(false);
		
		//将新建项目插入数据库并获取分配的节点ID
		System.out.println("insert: "+nbi.getName());
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		int nodeID=nbi_access.insertNode(nbi);
		nbi_access.diposeConnection();
		
		node.setId(nodeID);
		node.setParent(this);
		this.children.add(node);
	}

	public void removeChild(EstimateNode node){
		node.setParent(null);
		this.children.remove(node);
	}
	
	public void removeAllChildren(){
		for(int i=0;i<this.children.size();i++){
			this.children.get(i).setParent(null);
		}
		this.children.clear();
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
	
	
	public boolean hasEstPM(){
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		double estPM=nbi_access.getNodeByID(this.getId()).getEstPM();
		nbi_access.diposeConnection();
		
		if(estPM==-1){
			return false;
		}else{
			return true;
		}
	}


}
