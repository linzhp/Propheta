package estimation.integratedEstimate;

import java.util.ArrayList;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import dataManager.dataEntities.COCOMO;
import entity.EstimateNode;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;
	
	public COCOMOEstimateResults(COCOMOEstimate param){
		parameters = param;
	}
	
	public void show()
	{
		ArrayList<EstimateNode> children = GUI.getTreeArea().getSelectedNode().getChildren();
		Double[] sizes = new Double[children.size()];
		for(int i=0; i<children.size(); i++)
			sizes[i] = (double)children.get(i).getSLOC();
		Double[] effort = COCOMO.getIntegratedEffortTime(sizes, 
				parameters.getScaleFactors(), 
				parameters.getEffortMultipliers(), parameters.getEMtype());
		
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		//显示同论文总结出的CSBSG公式，计算得到的结果
		Label result = new Label(resultView, SWT.NONE);
		result.setText("根据公式计算出   PM为：" + effort[0].intValue()+ "(人.月)\n\n"+
				"\t\t TDEV为：" + effort[1].intValue()+"(月)\n\n" + "\t\t 平均所需开发人员为：" + (int)((effort[0]/effort[1])+1));
		GUI.createNewTab("集成估算结果", resultView);

	}
}