package estimation.sizeEstimate;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import dataManager.dataEntities.COCOMO;

public class COCOMOSizeResults {
	private COCOMOSize COCOMOsize;
	
	public COCOMOSizeResults(COCOMOSize paraSize){
		COCOMOsize = paraSize;
	}
	
	public void show()
	{
		int projectSize = COCOMOsize.getEstimatedSize();
		
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		
		Label result = new Label(resultView, SWT.NONE);
		result.setText("COCOMO估算出的规模为：" + projectSize + " 行代码");
		GUI.createNewTab("COCOMO规模估算结果", resultView);
//		resultView.setBounds(GUI.getButtomContentArea().getClientArea());

	}
}
