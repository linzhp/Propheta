package gui;

import org.eclipse.swt.widgets.Composite;

public class GUI {

	private static ContentArea contentArea=null;  //content composite in the main page
	private static TreeArea treeArea=null;  //tree composite to display WBS in the main page
	
	public static void setContentArea(ContentArea com){
		contentArea=com;
	}
	
	public static ContentArea getContentArea(){
		return contentArea;
	}
	
	public static void setTreeArea(TreeArea com){
		treeArea=com;
	}
	
	public static TreeArea getTreeArea(){
		return treeArea;
	}
}
