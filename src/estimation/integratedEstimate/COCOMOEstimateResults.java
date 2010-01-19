package estimation.integratedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import dataManager.dataAccess.CocomoEstimationAccess;
import dataManager.dataAccess.QuickEstimationAccess;

import entity.EstimateNode;
import estimation.COCOMO;
import estimation.SimpleIntegratedEstimate;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;
	
	public COCOMOEstimateResults(COCOMOEstimate param){
		parameters = param;
	}
	
	public void show()
	{
		ArrayList<EstimateNode> children = parameters.getSelectedChildren();
		int tag = 0;
		String resultText = "test";
		for(EstimateNode child: children)
			if (!child.getEstType().contains("cocomoSimple")){
				tag =1;
				break;
			}
		if (tag == 0){
			Double[] sizes = new Double[children.size()];
			Double[] productEMs = new Double[children.size()];
			CocomoEstimationAccess cocomoAccess = new CocomoEstimationAccess();
			cocomoAccess.initConnection();
			for(int i=0; i<children.size(); i++){
				sizes[i] = (double)children.get(i).getSLOC();
				productEMs[i] = cocomoAccess.getCocomoEstimationByNodeID(children.get(i).getId()).getProductEM();
			}
			cocomoAccess.disposeConnection();
			HashMap<String, String> factorsSF = parameters.getScaleFactors();
			String SCEDLevel = parameters.getSCED();
			Double[] effort = COCOMO.getIntegratedEffortTime(sizes, productEMs, factorsSF, SCEDLevel);
			resultText = "根据公式计算出   PM为：" + effort[0].intValue()+ "(人.月)\n\n"+
			"\t\t TDEV为：" + effort[1].intValue()+"(月)\n\n" + "\t\t 平均所需开发人员为：" + (int)((effort[0]/effort[1])+1);
		}
		else{
			Double[] efforts = new Double[children.size()];
			QuickEstimationAccess quickAccess = new QuickEstimationAccess();
			quickAccess.initConnection();
			CocomoEstimationAccess cocomoAccess = new CocomoEstimationAccess();
			cocomoAccess.initConnection();
			for(int i=0; i<children.size(); i++)
				if(children.get(i).getEstType().contains("quick"))
					efforts[i] = quickAccess.getQuickEstimationByNodeID(children.get(i).getId()).getFormulaEffort();
				else
					efforts[i] = cocomoAccess.getCocomoEstimationByNodeID(children.get(i).getId()).getDevTime()* 160;
			quickAccess.disposeConnection();
			cocomoAccess.disposeConnection();
			Double effort = SimpleIntegratedEstimate.getIntegratedEffort(efforts);
			resultText = "集成估算的 工作量为" + effort.intValue()+ " 小时";
		}
		
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		//显示同论文总结出的CSBSG公式，计算得到的结果
		Label resultLabel = new Label(resultView, SWT.NONE);
		resultLabel.setText(resultText);
		GUI.createNewTab("集成估算结果", resultView);
	}
}
