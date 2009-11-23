package gui;

import newProject.NewProjectAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import entity.EstimateNode;


/**
 * the special composite to dispaly the tree view
 * @author hezhimin
 *
 */
public class TreeArea extends Composite{

	public TreeArea(Composite parent, int style) {
		super(parent, style);
		addMenu();
	}
	
	
	//添加右键菜单
	public void addMenu(){
		MenuManager mm = new MenuManager(null);
		
		mm.add(new NewProjectAction());		
		
		Menu menu=mm.createContextMenu(this);
		this.setMenu(menu);
	}
	
	public void dispalyTree(EstimateNode en){
		TreeViewer tv=new TreeViewer(this,SWT.V_SCROLL|SWT.V_SCROLL);
		tv.setContentProvider(new treeContentProvider());
		tv.setLabelProvider(new treeLabelProvider());
		tv.setInput(en);
	}
}
