package gui;

import gui.tabs.ParameterArea;
import gui.widgets.tree.TreeArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;


public class GUI {

	private static CTabFolder topContentArea=null;  //right-top content composite in the main page
	private static CTabFolder buttomContentArea=null;  //right-buttom content composite in the main page
	private static TreeArea treeArea=null;  //tree composite to display WBS in the main page
	private static FormToolkit toolkit;
	
	public static void setTopContentArea(CTabFolder com){
		topContentArea=com;
		topContentArea.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				((ParameterArea)((CTabItem)e.item).getControl()).refresh();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public static CTabFolder getTopContentArea(){
		return topContentArea;
	}
	
	public static void setButtomContentArea(CTabFolder com){
		buttomContentArea=com;
	}
	
	public static CTabFolder getButtomContentArea(){
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
	
	public static CTabItem createNewTab(String title, Composite content){
		CTabFolder folder = (CTabFolder)content.getParent();
		CTabItem tab = new CTabItem(folder, SWT.CLOSE);
		tab.setText(title);
		tab.setControl(content);
		folder.setSelection(tab);
		return tab;
	}
}
