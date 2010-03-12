package gui.widgets.tree.contextMenu;

import data.database.ImportData;
import data.database.dataEntities.EstimateNode;
import gui.GUI;
import gui.widgets.tree.TreeArea;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ImportAction extends Action {
	private String filePath;

	public ImportAction(){
		super("导入");
	}
	
	@Override
	public void run() {
		TreeArea treeArea = GUI.getTreeArea();
		final EstimateNode node=treeArea.getSelectedNode();
		Shell shell = treeArea.getShell();
		FileDialog fileDialog = new FileDialog(shell,SWT.SAVE);
		fileDialog.setFileName(node.getName()+".db3");
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
					importData.copyData(node.getId());
				} catch (SQLException e) {
					messageBox.setMessage(e.getMessage());
					messageBox.open();
					e.printStackTrace();
				}
			}
		};
		try {
			progress.run(true, false, runnable);
			treeArea.getTreeViewer().refresh();
		} catch (Exception e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}

	}
}
