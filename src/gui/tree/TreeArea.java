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
import estimation.detailedEstimate.DetailedEstimationAction;
import estimation.quickEstimate.QuickEstimateAction;
import estimation.sizeEstimate.COCOMOSizeAction;
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
	
	//menus
	NewProjectAction newProjectAction=new NewProjectAction();
	AddNodeAction addNodeAction=new AddNodeAction();
	RemoveNodeAction removeNodeAction=new RemoveNodeAction();
	RenameNodeAction renameNodeAction=new RenameNodeAction();
	MenuManager menuManager_estimation=new MenuManager("估算");
	QuickEstimateAction quickEstimateAction=new QuickEstimateAction();
	DetailedEstimationAction detailedEstimationAction=new DetailedEstimationAction();
	COCOMOSizeAction cOCOMOSizeAction=new COCOMOSizeAction();
	
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
		MenuManager mm = new MenuManager();		
		mm.add(newProjectAction);	
		mm.add(addNodeAction);
		mm.add(removeNodeAction);
		mm.add(renameNodeAction);
		mm.add(new Separator());
		menuManager_estimation.add(quickEstimateAction);
		menuManager_estimation.add(detailedEstimationAction);
		menuManager_estimation.add(cOCOMOSizeAction);
		mm.add(menuManager_estimation);
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
		
		//设置初始状态
		setNoneMenu();
		
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
			public void mouseUp(MouseEvent e) {}		
		});
	}
	
	
	private void setNoneMenu(){
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(false);
		removeNodeAction.setEnabled(false);
		renameNodeAction.setEnabled(false);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		cOCOMOSizeAction.setEnabled(false);		
	}
	
	
	private void setRootMenu(){
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(false);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		cOCOMOSizeAction.setEnabled(false);
	}
	
	
	private void setNodeMenu(){
		newProjectAction.setEnabled(false);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(false);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		cOCOMOSizeAction.setEnabled(false);
	}
	
	
	private void setLeafMenu(){
		newProjectAction.setEnabled(false);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(true);
		detailedEstimationAction.setEnabled(true);
		cOCOMOSizeAction.setEnabled(true);
	}
	
	private void setRootLeafMenu(){
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(true);
		detailedEstimationAction.setEnabled(true);
		cOCOMOSizeAction.setEnabled(true);
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
	
	public boolean isEstimateProjectExist(String projectName){
		boolean isExist=false;
		for(int i=0;i<this.estimateProjects.size();i++){
			if(this.estimateProjects.get(i).getName().equalsIgnoreCase(projectName)){
				isExist=true;
				break;
			}
		}
		return isExist;
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
