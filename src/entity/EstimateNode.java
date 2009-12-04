package entity;

import java.util.ArrayList;

/**
 * the node in the tree view
 * @author Administrator
 *
 */
public class EstimateNode{

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
	
	public void add(EstimateNode node){
		node.setParent(this);
		this.children.add(node);
	}

	public void removeChild(EstimateNode node){
		this.children.remove(node);
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
	
	


}
