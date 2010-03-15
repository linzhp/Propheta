package gui.actions;

import data.database.ImportData;
import data.database.dataEntities.EstimateNode;
import data.database.dataEntities.SuperRoot;
import gui.GUI;
import gui.widgets.tree.TreeArea;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ImportAction extends Action {
	private String filePath;
	private boolean toRoot = false;

	public ImportAction(){
		this(false);
	}
	
	public ImportAction(boolean toRoot){
		super();
		this.toRoot = toRoot;
		if(toRoot == true)
			this.setText("导入到根节点");
		else{
			this.setText("导入到本节点");
		}
	}
	
	@Override
	public void run() {
		TreeArea treeArea = GUI.getTreeArea();
		TreeViewer treeViewer = treeArea.getTreeViewer();
		final EstimateNode parent;
		if(toRoot==true){
			parent = (SuperRoot)treeViewer.getInput();
		}else{
			parent = treeArea.getSelectedNode();
		}
		Shell shell = treeArea.getShell();
		FileDialog fileDialog = new FileDialog(shell,SWT.SAVE);
		filePath = fileDialog.open();
		if(filePath == null)
			return;
		File file = new File(filePath);
		final MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
		while(!file.exists())
		{
			messageBox.setMessage("文件不存在，请重新选择");
			messageBox.open();
			filePath = fileDialog.open();
			if(filePath == null)
				return;
			file = new File(filePath);
		}
		ProgressMonitorDialog progress = new ProgressMonitorDialog(shell);
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
					InterruptedException {
				monitor.beginTask("正在导入数据", IProgressMonitor.UNKNOWN);
				ImportData importData = new ImportData(filePath);
				try {
					importData.copyData(parent);
				} catch (SQLException e) {
					messageBox.setMessage(e.getMessage());
					messageBox.open();
					e.printStackTrace();
				}
			}
		};
		try {
			progress.run(true, false, runnable);
			treeViewer.refresh();
		} catch (Exception e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}

	}
}
