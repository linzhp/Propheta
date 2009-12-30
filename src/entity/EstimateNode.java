package entity;

import java.util.ArrayList;

import dataManager.dataAccess.NodeBasicInfoAccess;

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
	
	public void addChild(EstimateNode node){
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
