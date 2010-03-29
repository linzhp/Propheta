package data.file.pdf;

import gui.GUI;
import gui.widgets.tree.TreeArea;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import data.database.dataEntities.EstimateNode;

public class PrintPDFAction extends Action {
	public PrintPDFAction() {
		super("打印到PDF文件");
	}

	@Override
	public void run() {

		TreeArea treeArea = GUI.getTreeArea();
		FileDialog dialog = new FileDialog(treeArea.getShell(), SWT.SAVE);
		dialog.setFilterNames(new String[] { "PDF", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.pdf", "*.*" }); // Windows
		dialog.setFileName("result.pdf");
		String path = dialog.open();
		if (path != null) {
			EstimateNode id = treeArea.getSelectedNode();
			OutputResultPDF orp = new OutputResultPDF(id.getId(), path);
			orp.saveResult();

		}
	}
}
