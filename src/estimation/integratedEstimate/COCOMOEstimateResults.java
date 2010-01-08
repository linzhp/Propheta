package estimation.integratedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import entity.EstimateNode;
import estimation.COCOMO;
import estimation.SimpleIntegratedEstimation;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;
	
	public COCOMOEstimateResults(COCOMOEstimate param){
		parameters = param;
	}
	
	public void show()
	{
		ArrayList<EstimateNode> children = parameters.getSelectedChildren();
		int tag = 0;
		String resultText = null;
		for(EstimateNode child: children)
			if (child.getEstType().contains("cocomoSimple")){
				tag =1;
				break;
			}
		if (tag == 0){
			Double[] sizes = new Double[children.size()];
			for(int i=0; i<children.size(); i++)
				sizes[i] = (double)children.get(i).getSLOC();
			HashMap<String, String> factorSF = parameters.getScaleFactors();
			String SCEDLevel = parameters.getSCED();
			//Double[] effort = COCOMO.getIntegratedEffortTime(sizes, 
					//parameters.getScaleFactors(), 
					//parameters.getEffortMultipliers());
			//resultText = "根据公式计算出   PM为：" + effort[0].intValue()+ "(人.月)\n\n"+
			//"\t\t TDEV为：" + effort[1].intValue()+"(月)\n\n" + "\t\t 平均所需开发人员为：" + (int)((effort[0]/effort[1])+1);
		}
		else{
			Double[] efforts = new Double[children.size()];
			for(int i=0; i<children.size(); i++)
				if(children.get(i).getEstType().contains("quick"))
					;//efforts[i] = 取formulaEffort值
				else
					;//efforts[i] = 直接从cocomo里取devTime值 并转化为小时
			Double effort = SimpleIntegratedEstimation.getIntegratedEffort(efforts);
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
