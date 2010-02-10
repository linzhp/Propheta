package gui;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;

import data.database.ExportData;

import estimation.entity.EstimateNode;
import estimation.entity.EstimationProjects;

/**
 * 对一些模块进行测试用的类
 * @author Administrator
 *
 */
public class Test extends Action{

	public Test(){
		super("测试");
	}
	
	public static void main(String[] args) throws Exception{
//		ExportData dataBaseAccess = new ExportData("/home/clive/Desktop/test.db3");
		Object i = 11;
		System.out.println("object is: "+i);
//		dataBaseAccess.createSchema();
	}
	
	public void run(){
		ArrayList<EstimateNode> allNodes=EstimationProjects.getAllNodes();
		

		for(int i=0;i<allNodes.size();i++){
			System.out.println(allNodes.get(i).getId()+"	"+allNodes.get(i).getName());
		}
	}
}
