package data.database.dataEntities;

/**
 * 本节点并不存在于数据库，在界面显示上，它是所有parentID为-1的节点的父节点
 * 本类为单例，用于treeViewer.setInput。在项目的任何地方调用treeViewer.getInput
 * 与调用本类的getInstance()得到的是同一个对像
 * 
 * @author 林中鹏
 *
 */

public class SuperRoot extends EstimateNode {
	private static SuperRoot instance;
	
	private SuperRoot(){
		loadChildren();
	}
	
	public static SuperRoot getInstance(){
		if(instance == null)
			instance = new SuperRoot();
		return instance;
	}
	
	@Override
	public int getId(){
		return -1;
	}
}
