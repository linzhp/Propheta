package gui.widgets.tree;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import estimation.detailedEstimate.DEOpen;
import estimation.entity.EstimateNode;
import estimation.entity.EstimationProjects;
import estimation.integratedEstimate.IEOpen;
import estimation.quickEstimate.QEOpen;
import gui.OpenBasicInformationPageAction;
import gui.widgets.tree.contextMenu.AddNodeAction;
import gui.widgets.tree.contextMenu.ExportAction;
import gui.widgets.tree.contextMenu.ImportAction;
import gui.widgets.tree.contextMenu.NewProjectAction;
import gui.widgets.tree.contextMenu.RemoveNodeAction;
import gui.widgets.tree.contextMenu.RenameNodeAction;


/**
 * the special composite to dispaly the tree view
 * @author hezhimin
 *
 */
public class TreeArea extends Composite{

	private TreeViewer treeViewer;
	private TreeContentProvider treeContentProvider;
	private TreeLabelProvider treeLabelProvider;
	
	//menus
	OpenBasicInformationPageAction openBasicInformationPageAction=new OpenBasicInformationPageAction();
	NewProjectAction newProjectAction=new NewProjectAction();
	AddNodeAction addNodeAction=new AddNodeAction();
	RemoveNodeAction removeNodeAction=new RemoveNodeAction();
	RenameNodeAction renameNodeAction=new RenameNodeAction();
	MenuManager menuManager_estimation=new MenuManager("估算");
	QEOpen quickEstimateAction=new QEOpen();
	DEOpen detailedEstimationAction=new DEOpen();
	IEOpen integratedEstimationAction=new IEOpen();
	ExportAction exportAction = new ExportAction();
	ImportAction importAction = new ImportAction();
	
	public TreeArea(Composite parent, int style) {
		super(parent, style);
		initControls();
		
	}
	
	
	//初始化控件
	public void initControls(){
		this.treeViewer=new TreeViewer(this,SWT.V_SCROLL|SWT.V_SCROLL);
		this.treeContentProvider=new TreeContentProvider();
		this.treeLabelProvider=new TreeLabelProvider();		
		addMenu();
	}
	
	
	//添加右键菜单
	public void addMenu(){		
		MenuManager mm = new MenuManager();	
		mm.add(openBasicInformationPageAction);
		mm.add(importAction);
		mm.add(exportAction);
		mm.add(newProjectAction);	
		mm.add(addNodeAction);
		mm.add(removeNodeAction);
		mm.add(renameNodeAction);
		mm.add(new Separator());
		menuManager_estimation.add(quickEstimateAction);
		menuManager_estimation.add(detailedEstimationAction);
		//menuManager_estimation.add(cOCOMOSizeAction);
		menuManager_estimation.add(integratedEstimationAction);
		mm.add(menuManager_estimation);
		Menu menu=mm.createContextMenu(this);
		this.treeViewer.getTree().setMenu(menu);
		
		//设置初始状态
		setNoneMenu();
		
		this.treeViewer.getTree().addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				openBasicInformationPageAction.run();
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
		openBasicInformationPageAction.setEnabled(false);
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(false);
		removeNodeAction.setEnabled(false);
		renameNodeAction.setEnabled(false);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		integratedEstimationAction.setEnabled(false);		
	}
	
	
	private void setRootMenu(){
		openBasicInformationPageAction.setEnabled(false);
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		integratedEstimationAction.setEnabled(true);		
	}
	
	
	private void setNodeMenu(){
		openBasicInformationPageAction.setEnabled(false);
		newProjectAction.setEnabled(false);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(false);
		detailedEstimationAction.setEnabled(false);
		integratedEstimationAction.setEnabled(true);	
	}
	
	
	private void setLeafMenu(){
		openBasicInformationPageAction.setEnabled(true);
		newProjectAction.setEnabled(false);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(true);
		detailedEstimationAction.setEnabled(true);
		integratedEstimationAction.setEnabled(false);
	}
	
	private void setRootLeafMenu(){
		openBasicInformationPageAction.setEnabled(true);
		newProjectAction.setEnabled(true);
		addNodeAction.setEnabled(true);
		removeNodeAction.setEnabled(true);
		renameNodeAction.setEnabled(true);
		menuManager_estimation.setVisible(true);
		quickEstimateAction.setEnabled(true);
		detailedEstimationAction.setEnabled(true);
		integratedEstimationAction.setEnabled(false);
	}
	
	
	/**
	 * 显示树结构(估算项目结构拆分)
	 */
	public void dispalyTree(){		
		this.treeViewer.setContentProvider(treeContentProvider);
		this.treeViewer.setLabelProvider(treeLabelProvider);
		this.treeViewer.setInput(EstimationProjects.getEstimateProjects());		
	}
	
	
	/**
	 * 获取树中当前选择的节点
	 * @return
	 */
	public EstimateNode getSelectedNode(){
		StructuredSelection se=(StructuredSelection)treeViewer.getSelection();
		EstimateNode node=(EstimateNode)se.getFirstElement();
		return node;
	}
	
	
	public TreeViewer getTreeViewer(){
		return this.treeViewer;
	}
}
