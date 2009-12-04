package gui;

import java.util.ArrayList;

import newProject.NewEstimationAction;
import newProject.NewProjectAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
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
	private ArrayList<EstimateNode> estimateProjects;
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
		this.estimateProjects=new ArrayList<EstimateNode>();
	}
	
	
	//添加右键菜单
	public void addMenu(){
		MenuManager mm = new MenuManager(null);
		
		mm.add(new NewProjectAction());	
		mm.add(new NewEstimationAction());
		mm.add(new AddNodeAction());
		
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
		//this.treeViewer.getControl().setMenu(menu);
	}
	
	public void dispalyTree(){
		
		this.treeViewer.setContentProvider(treeContentProvider);
		this.treeViewer.setLabelProvider(treeLabelProvider);
		this.treeViewer.setInput(this.estimateProjects);
	}
	
	
	public void setEstimateProjects(ArrayList<EstimateNode> list){
		this.estimateProjects=list;
	}
	
	
	public void addEstimateProjet(EstimateNode en){
		this.estimateProjects.add(en);
		this.treeViewer.refresh();
	}
	
	
	public EstimateNode getSelectedNode(){
		StructuredSelection se=(StructuredSelection)treeViewer.getSelection();
		EstimateNode node=(EstimateNode)se.getFirstElement();
		return node;
	}
	
	
	public TreeViewer getTreeViewer(){
		return this.treeViewer;
	}
}
