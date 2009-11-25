package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * the node in the tree view
 * @author Administrator
 *
 */
public class EstimateNode {

	public String name=null;
	public List children=new ArrayList();
	
	public EstimateNode(String str){
		this.name=str;
	}
	
	
	public void setName(String str){
		this.name=str;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public void setChildren(List list){
		this.children =list;
	}
	
	public List getChildren(){
		return this.children;
	}
	
	public void addChild(EstimateNode en){
		this.children.add(en);
	}
}
