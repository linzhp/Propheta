package entity;

import gui.GUI;

import java.util.ArrayList;

import dataManager.dataAccess.NodeBasicInfoAccess;
import dataManager.dataEntities.NodeBasicInformation;


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
	 */
	public static void readEstimateProjects(){
		estimateProjects=new ArrayList<EstimateNode>();
		
		//从数据库读取估算项目信息
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		ArrayList<NodeBasicInformation> rootNodes=nbi_access.getAllRootNodes(); //所有根节点
		nbi_access.diposeConnection();
		for(int i=0;i<rootNodes.size();i++){
			NodeBasicInformation nbi=rootNodes.get(i);
			EstimateNode newEstimationProject=new EstimateNode(null);
			newEstimationProject.setId(nbi.getNodeID());
			newEstimationProject.setName(nbi.getName());
			newEstimationProject.setParent(newEstimationProject);
			
			initNodeChildren(newEstimationProject);
			estimateProjects.add(newEstimationProject);			
		}		
	}
	
	
	private static void initNodeChildren(EstimateNode node){
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.initConnection();
		ArrayList<NodeBasicInformation> childNodes=nbi_access.getNodesByParentID(node.getId());
		nbi_access.diposeConnection();
		if(childNodes.size()==0){
			return;
		}else{
			for(int i=0;i<childNodes.size();i++){
				NodeBasicInformation nbi=childNodes.get(i);
				EstimateNode newNode=new EstimateNode(null);
				newNode.setId(nbi.getNodeID());
				newNode.setName(nbi.getName());
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
	
	
	private static void storeNode(EstimateNode node){
		System.out.println(node.getName()+"	"+node.getParent().getName());
		NodeBasicInformation nbi=new NodeBasicInformation();
		nbi.setNodeID(node.getId());
		nbi.setName(node.getName());
		nbi.setParentID(node.getParent().getId());
		nbi.setIsRoot(node.getParent().getId()==node.getId());
		if(node.getId()==-1){  //尚未编号，插入
			NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
			nbi_access.initConnection();
			int nodeID=nbi_access.insertNode(nbi);
			nbi_access.diposeConnection();
			for(int i=0;i<node.getChildren().size();i++){
				EstimateNode childNode=node.getChildren().get(i);
				childNode.getParent().setId(nodeID);
				storeNode(childNode);
			}
		}else{ //以编号，更新
			NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
			nbi_access.initConnection();
			nbi_access.updateNode(nbi);
			nbi_access.diposeConnection();
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
	public static void addEstimateProjet(EstimateNode en){
		en.setParent(en);
		estimateProjects.add(en);
		
		//更新treeviewer显示结果
		GUI.getTreeArea().getTreeViewer().refresh();
	}
	

    /**
     * 删除项目
     * @param en
     */
	public static void removeEstimateProject(EstimateNode en){
		estimateProjects.remove(en);
		
		//更新treeviewer显示结果
		GUI.getTreeArea().getTreeViewer().refresh();
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
}
