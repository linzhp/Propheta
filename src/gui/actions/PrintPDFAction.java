package gui.actions;

import gui.GUI;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import data.database.dataEntities.EstimateNode;
import data.database.dataEntities.pdf.OutputResultPDF;

public class PrintPDFAction extends Action {
	public PrintPDFAction(){
		super("打印到PDF文件");
	}
	
	@Override
	public void run(){
		
			 Shell shell = new Shell();      
			
			FileDialog dialog = new FileDialog(shell,SWT.SAVE);
		    dialog
		        .setFilterNames(new String[] { "PDF", "All Files (*.*)" });
		    dialog.setFilterExtensions(new String[] { "*.pdf", "*.*" }); // Windows	                                  
		    dialog.setFilterPath("c:\\"); // Windows path
		    dialog.setFileName("result.pdf");
		    String path = dialog.open();
		    if(path!=null){
		    	EstimateNode id = GUI.getTreeArea().getSelectedNode();
		    	OutputResultPDF orp = new OutputResultPDF(id.getId(),path);
		    	orp.saveResult();
		    
		}
	}
}
