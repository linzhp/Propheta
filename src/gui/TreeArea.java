package gui;

import java.util.ArrayList;
import java.util.List;

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

	private TreeViewer treeViewer;
	private ArrayList estimateProjects;
	private TreeContentProvider treeContentProvider;
	private TreeLabelProvider treeLabelProvider;
	
	
	public TreeArea(Composite parent, int style) {
		super(parent, style);
		initControls();
		addMenu();
	}
	
	
	//初始化控件
	public void initControls(){
		this.treeViewer=new TreeViewer(this,SWT.V_SCROLL|SWT.V_SCROLL);
		this.treeContentProvider=new TreeContentProvider();
		this.treeLabelProvider=new TreeLabelProvider();
		this.estimateProjects=new ArrayList();
	}
	
	
	//添加右键菜单
	public void addMenu(){
		MenuManager mm = new MenuManager(null);
		
		mm.add(new NewProjectAction());		
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
	}
	
	public void dispalyTree(){
		
		this.treeViewer.setContentProvider(treeContentProvider);
		this.treeViewer.setLabelProvider(treeLabelProvider);
		this.treeViewer.setInput(this.estimateProjects);
	}
	
	
	public void setEstimateProjects(ArrayList list){
		this.estimateProjects=list;
	}
	
	
	public void addEstimateProjet(EstimateNode en){
		this.estimateProjects.add(en);
		this.treeViewer.refresh();
	}
}
