package entity;

import gui.GUI;

import java.util.ArrayList;


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
	 * 初始化估算项目集合
	 */
	public static void initEstimateProjects(){
		estimateProjects=new ArrayList<EstimateNode>();
		
		//从数据库读取估算项目信息
	}
	
	
	/**
	 * 新增项目
	 * @param en
	 */
	public static void addEstimateProjet(EstimateNode en){
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
