package run;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;

import entity.EstimateNode;
import entity.EstimationProjects;

/**
 * 对一些模块进行测试用的类
 * @author Administrator
 *
 */
public class Test extends Action{

	public Test(){
		super("测试");
	}
	
	public void run(){
		ArrayList<EstimateNode> allNodes=EstimationProjects.getAllNodes();
		

		for(int i=0;i<allNodes.size();i++){
			System.out.println(allNodes.get(i).getId()+"	"+allNodes.get(i).getName());
		}
	}
}
