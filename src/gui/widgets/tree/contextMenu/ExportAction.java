package gui.widgets.tree.contextMenu;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import data.database.ExportData;

import estimation.entity.EstimateNode;
import gui.GUI;
import gui.widgets.tree.TreeArea;

public class ExportAction extends Action {
	private String filePath;

	public ExportAction() {
		super("导出");
	}

	@Override
	public void run() {
		TreeArea treeArea = GUI.getTreeArea();
		final EstimateNode node = treeArea.getSelectedNode();
		Shell shell = treeArea.getShell();
		FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
		fileDialog.setFileName(node.getName() + ".db3");
		filePath = fileDialog.open();
		if (filePath == null)
			return;
		File file = new File(filePath);
		final MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
		while (file.exists()) {
			messageBox.setMessage("文件已存在，请选择另一个文件名");
			messageBox.open();
			filePath = fileDialog.open();
			if (filePath == null)
				return;
			file = new File(filePath);
		}
		ProgressMonitorDialog progress = new ProgressMonitorDialog(shell);
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask("正在初始化文件", 100);
				ExportData exportData = new ExportData(filePath);
				try {
					exportData.createSchema();
					monitor.worked(10);
					monitor.setTaskName("正在导出数据");
					exportData.copyData(node.getId());
					monitor.done();
				} catch (Exception e) {
					messageBox.setMessage(e.getMessage());
					messageBox.open();
					e.printStackTrace();
				}
			}
		};
		try {
			progress.run(true, false, runnable);
		} catch (Exception e) {
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
}
