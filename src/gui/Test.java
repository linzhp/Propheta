package gui;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;

import data.database.dataAccess.DataBaseAccess;
import data.database.dataAccess.ExportData;

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
		ExportData dataBaseAccess = new ExportData("/home/clive/Desktop/test.db3");
		dataBaseAccess.createSchema();
	}
	
	public void run(){
		ArrayList<EstimateNode> allNodes=EstimationProjects.getAllNodes();
		

		for(int i=0;i<allNodes.size();i++){
			System.out.println(allNodes.get(i).getId()+"	"+allNodes.get(i).getName());
		}
	}
}
