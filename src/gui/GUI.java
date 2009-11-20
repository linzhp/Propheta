package gui;

import org.eclipse.swt.widgets.Composite;

public class GUI {

	private static Composite contentArea=null;
	private static Composite treeArea=null;
	
	public static void setContentArea(Composite com){
		contentArea=com;
	}
	
	public static Composite getContentArea(){
		return contentArea;
	}
	
	public static void setTreeArea(Composite com){
		treeArea=com;
	}
	
	public static Composite getTreeArea(){
		return treeArea;
	}
}
