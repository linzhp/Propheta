package estimation.detailedEstimate;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import dataManager.dataEntities.COCOMO;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;
	
	public COCOMOEstimateResults(COCOMOEstimate param){
		parameters = param;
	}
	
	public void show()
	{
		Double[] effort = COCOMO.getModuleEffortTime(parameters.getEstimatedSize(), 
				parameters.getScaleFactors(), 
				parameters.getEffortMultipliers());
		
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		//显示同论文总结出的CSBSG公式，计算得到的结果
		Label result = new Label(resultView, SWT.NONE);
		result.setText("根据公式计算出   PM为：" + effort[0].intValue()+ "(人.月)\n\n"+
				"\t\t TDEV为：" + effort[1].intValue()+"(月)\n\n" + "\t\t 平均所需开发人员为：" + (int)((effort[0]/effort[1])+1));
		GUI.createNewTab("详细估算结果", resultView);

	}
}
