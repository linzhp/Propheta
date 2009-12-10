package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


public class GUI {

	private static CTabFolder topContentArea=null;  //right-top content composite in the main page
	private static ContentArea buttomContentArea=null;  //right-buttom content composite in the main page
	private static TreeArea treeArea=null;  //tree composite to display WBS in the main page
	private static FormToolkit toolkit;
	
	public static void setTopContentArea(CTabFolder com){
		topContentArea=com;
	}
	
	public static CTabFolder getTopContentArea(){
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
	
	public static FormToolkit getToolkit(){
		if(toolkit==null)
			toolkit = new FormToolkit(Display.getCurrent());
		return toolkit;
	}
	
	public static ScrolledForm createNewParamTab(String title){
		CTabItem tab = new CTabItem(topContentArea, SWT.CLOSE);
		ScrolledForm form = getToolkit().createScrolledForm(topContentArea);
		tab.setText(title);
		tab.setControl(form);
		return form;
	}
}
