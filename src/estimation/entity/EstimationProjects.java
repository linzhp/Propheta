package estimation.entity;

import gui.GUI;

import java.sql.SQLException;
import java.util.ArrayList;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import data.database.dataEntities.NodeBasicInformation;
import data.database.dataEntities.QuickEstimationRecord;


/**
 * 当前所有估算项目（所有方法均为static，可全局访问）
 * @author Administrator
 *
 */
public class EstimationProjects{
	
	private static ArrayList<EstimateNode> estimateProjects; //当前显示的所有估算项目

	
	/**
	 * 获取所有估算项目
	 * @param projects
	 */
	public void setEstimateProjects(ArrayList<EstimateNode> projects){
		estimateProjects=projects;
	}
	
	
	/**
	 * 设置估算项目
	 * @return
	 */
	public static ArrayList<EstimateNode> getEstimateProjects() {
		return estimateProjects;
	}
	
	
	/**
	 * 初始化估算项目集合(从数据库中读取保存的估算项目信息)
	 * @throws SQLException 
	 */
	public static void readEstimateProjects() throws SQLException{
		estimateProjects=new ArrayList<EstimateNode>();
		
		//从数据库读取估算项目信息
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		ArrayList<Entity> rootNodes=nbi_access.getAllRootNodes(); //所有根节点
		for(int i=0;i<rootNodes.size();i++){
			NodeBasicInformation nbi=(NodeBasicInformation)rootNodes.get(i);
			EstimateNode newEstimationProject=new EstimateNode(null);
			newEstimationProject.setId((Integer)nbi.get("id"));
			newEstimationProject.setName((String)nbi.get("name"));
			newEstimationProject.setParent(null);  //根节点，parent为null
			
			initNodeChildren(newEstimationProject);
			estimateProjects.add(newEstimationProject);			
		}		
	}
	
	
	/**
	 *  从数据库中读取节点所有的子节点
	 * @param node
	 */
	private static void initNodeChildren(EstimateNode node){
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		ArrayList<Entity> childNodes=nbi_access.getNodesByParentID(node.getId());
		if(childNodes.size()==0){
			return;
		}else{
			for(int i=0;i<childNodes.size();i++){
				NodeBasicInformation nbi=(NodeBasicInformation)childNodes.get(i);
				EstimateNode newNode=new EstimateNode(null);
				newNode.setId((Integer)nbi.get("id"));
				newNode.setName((String)nbi.get("name"));
				newNode.setParent(node);
				node.getChildren().add(newNode);
				
				initNodeChildren(newNode);
			}
		}	
	}
	
	
	/**
	 * 保存估算项目信息
	 */
	public static void saveEstimationProjects(){
		for(int i=0;i<estimateProjects.size();i++){
			EstimateNode node=estimateProjects.get(i);
			storeNode(node);
		}
	}
	
	/**
	 * 保存估算项目所有节点信息
	 * @param node
	 */
	private static void storeNode(EstimateNode node){
		if(node.isRoot()){
			System.out.println(node.getName()+"	"+"Root");
		}else{
			System.out.println(node.getName()+"	"+node.getParent().getName());
		}
		
		NodeBasicInformation nbi=new NodeBasicInformation();
		nbi.set("id",node.getId());
		nbi.set("name",node.getName());
		if(node.isRoot()==true){
			nbi.set("parentID",-1);
		}else{
			nbi.set("parentID",node.getParent().getId());
		}		
		if(node.getId()==-1){  //尚未编号，插入
			NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
			int nodeID=nbi_access.insert(nbi);
			for(int i=0;i<node.getChildren().size();i++){
				EstimateNode childNode=node.getChildren().get(i);
				childNode.getParent().setId(nodeID);
				storeNode(childNode);
			}
		}else{ //以编号，更新
			NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
			nbi_access.update(nbi);
			for(int i=0;i<node.getChildren().size();i++){
				EstimateNode childNode=node.getChildren().get(i);
				storeNode(childNode);
			}
		}
	}
	
	
	/**
	 * 新增项目
	 * @param en
	 */
	public static void addEstimateProjet(EstimateNode node){
		
		/**这两个操作可以改成多线程**/
		//更新数据库
		NodeBasicInformation nbi=new NodeBasicInformation();
		nbi.set("id",-1);
		nbi.set("name",node.getName());
		nbi.set("parentID",-1);
		
		//将新建项目插入数据库并获取分配的节点ID
		System.out.println("insert: "+nbi.get("name"));
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		int nodeID=nbi_access.insert(nbi);
		
		//为新建项目设置估算输入输出的默认值并保存到数据库中
		QuickEstimationRecord qer=new QuickEstimationRecord();
		qer.set("nodeID",nodeID);
		QuickEstimationAccess qe_access=new QuickEstimationAccess();
		qe_access.insert(qer);
		
		CocomoEstimationRecord cer=new CocomoEstimationRecord();
		cer.set("nodeID",nodeID);
		CocomoEstimationAccess ce_access=new CocomoEstimationAccess();
		ce_access.insert(cer);
		
		node.setId(nodeID);
		node.setParent(null);
		estimateProjects.add(node);	
		
		//更新treeviewer显示结果
		GUI.getTreeArea().getTreeViewer().refresh();		
	}
	

    /**
     * 删除项目
     * @param en
     */
	public static void removeEstimateProject(EstimateNode node){
		System.out.println("delete:	"+node.getName());
		//删除根节点
		estimateProjects.remove(node);
		
		//更新数据库
		removeNode(node);
		
		//更新treeviewer显示结果
		GUI.getTreeArea().getTreeViewer().refresh();
	}
	
	
	/**
	 * 删除估算项目所有的子节点
	 * @param node
	 */
	private static void removeNode(EstimateNode node){
		System.out.println("delete:	"+node.getName());
		//更新数据库		
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.deleteByID(node.getId());
		
		//删除对应的估算记录
		QuickEstimationAccess qe_access=new QuickEstimationAccess();
		qe_access.deleteQuickEstimationByNodeID(node.getId());
		
		CocomoEstimationAccess ce_access=new CocomoEstimationAccess();
		ce_access.deleteCocomoEstimationByNodeID(node.getId());
		
		if(node.hasChildren()){
			ArrayList<EstimateNode> nodes=node.getChildren();
			for(int i=0;i<nodes.size();i++){
				removeNode(nodes.get(i));
			}
		}else{
			return;
		}			
	}
	

    /**
     * 判断是否存在同名项目（项目名称不考虑大小写）
     * @param projectName
     * @return
     */
	public static boolean isEstimateProjectExist(String projectName){
		boolean isExist=false;
		for(int i=0;i<estimateProjects.size();i++){
			if(estimateProjects.get(i).getName().equalsIgnoreCase(projectName)){
				isExist=true;
				break;
			}
		}
		return isExist;
	}
	
	
	/**
	 * 获取所有估算节点
	 * @return
	 */
	public static ArrayList<EstimateNode> getAllNodes(){
		ArrayList<EstimateNode> allNodes=new ArrayList<EstimateNode>();
		for(int i=0;i<estimateProjects.size();i++){
			getAllChildren(estimateProjects.get(i),allNodes);
		}
		
		return allNodes;		
	}
	
	private static void getAllChildren(EstimateNode node, ArrayList<EstimateNode> nodes){
		if(node!=null){
			nodes.add(node);
			for(int i=0;i<node.getChildren().size();i++){
				getAllChildren(node.getChildren().get(i),nodes);
			}
		}
	}
	
	
	/**
	 * 根据节点ID获取节点，节点不存在时返回null
	 * @param nodeID
	 * @return
	 */
	public static EstimateNode getNodeByID(int nodeID){
		ArrayList<EstimateNode> allNodes=getAllNodes();
		EstimateNode node=null;
		for(int i=0;i<allNodes.size();i++){
			if(allNodes.get(i).getId()==nodeID){
				node=allNodes.get(i);
				break;
			}
		}
		
		return node;		
	}
	
	
}
