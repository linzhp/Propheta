package gui.tree;

import java.util.ArrayList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import entity.EstimateNode;
import estimation.NewEstimationAction;
import estimation.detailedEstimate.DetailedEstimationAction;
import estimation.quickEstimate.QuickEstimateAction;
import gui.tree.contextMenu.AddNodeAction;
import gui.tree.contextMenu.NewProjectAction;
import gui.tree.contextMenu.RemoveNodeAction;
import gui.tree.contextMenu.RenameNodeAction;



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
		
	}
	
	
	//初始化控件
	public void initControls(){
		this.treeViewer=new TreeViewer(this,SWT.V_SCROLL|SWT.V_SCROLL);
		this.treeContentProvider=new TreeContentProvider();
		this.treeLabelProvider=new TreeLabelProvider();
		this.estimateProjects=new ArrayList<EstimateNode>();		
		addMenu();
	}
	
	
	//添加右键菜单
	public void addMenu(){
		this.treeViewer.getTree().addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				EstimateNode node=getSelectedNode();
				if(node==null){
					setNoneMenu();
				}else{
					if(node.isRoot()){
						if(node.isLeaf()){
							setRootLeafMenu();
						}else{
							setRootMenu();
						}						
					}else{
						if(node.isLeaf()){
							setLeafMenu();
						}else{
							setNodeMenu();
						}
					} 
				}		
			}

			@Override
			public void mouseUp(MouseEvent e) {
										
			}			
		});
	}
	
	
	private void setNoneMenu(){
		MenuManager mm = new MenuManager();		
		mm.add(new NewProjectAction());	
		mm.add(new QuickEstimateAction());
		mm.add(new DetailedEstimationAction());
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
	}
	
	
	private void setRootMenu(){
		MenuManager mm = new MenuManager();		
		mm.add(new NewProjectAction());	
		mm.add(new AddNodeAction());
		mm.add(new RemoveNodeAction());
		mm.add(new RenameNodeAction());
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
	}
	
	
	private void setNodeMenu(){
		MenuManager mm = new MenuManager();		
		mm.add(new AddNodeAction());
		mm.add(new RemoveNodeAction());
		mm.add(new RenameNodeAction());
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
	}
	
	
	private void setLeafMenu(){
		MenuManager mm = new MenuManager();		
		mm.add(new AddNodeAction());
		mm.add(new RemoveNodeAction());
		mm.add(new RenameNodeAction());
		mm.add(new Separator());
		mm.add(new NewEstimationAction());			
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
	}
	
	private void setRootLeafMenu(){
		MenuManager mm = new MenuManager();		
		mm.add(new NewProjectAction());			
		mm.add(new AddNodeAction());
		mm.add(new RemoveNodeAction());
		mm.add(new RenameNodeAction());
		mm.add(new Separator());
		mm.add(new NewEstimationAction());
		
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
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
	
	public void removeEstimateProject(EstimateNode en){
		this.estimateProjects.remove(en);
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
