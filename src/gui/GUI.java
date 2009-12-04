package gui;

import org.eclipse.swt.widgets.Composite;

public class GUI {

	private static ContentArea topContentArea=null;  //right-top content composite in the main page
	private static ContentArea buttomContentArea=null;  //right-buttom content composite in the main page
	private static TreeArea treeArea=null;  //tree composite to display WBS in the main page
	
	public static void setTopContentArea(ContentArea com){
		topContentArea=com;
	}
	
	public static ContentArea getTopContentArea(){
		return topContentArea;
	}
	
	public static void setButtomContentArea(ContentArea com){
		buttomContentArea=com;
	}
	
	public static ContentArea getButtomContentArea(){
		return buttomContentArea;
	}
	
	public static void setTreeArea(TreeArea com){
		treeArea=com;
	}
	
	public static TreeArea getTreeArea(){
		return treeArea;
	}
}
