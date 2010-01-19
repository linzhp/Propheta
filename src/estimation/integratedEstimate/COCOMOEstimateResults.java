package estimation.integratedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import dataManager.dataAccess.CocomoEstimationAccess;
import dataManager.dataAccess.NodeBasicInfoAccess;
import dataManager.dataAccess.QuickEstimationAccess;
import dataManager.dataEntities.CocomoEstimationRecord;
import dataManager.dataEntities.NodeBasicInformation;

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
		CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
		cer_access.initConnection();
		CocomoEstimationRecord cer = new CocomoEstimationRecord();
		cer = cer_access.getCocomoEstimationByNodeID(parameters.getnodeID());
		
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
			HashMap<String, String> factorsSF = parameters.getScaleFactors();
			String SCEDLevel = parameters.getSCED();
			for(int i=0; i<children.size(); i++){
				sizes[i] = (double)children.get(i).getSLOC();
				productEMs[i] = cer_access.getCocomoEstimationByNodeID(children.get(i).getId()).getProductEM();
			}
			Double[] effort = COCOMO.getIntegratedEffortTime(sizes, productEMs, factorsSF, SCEDLevel);
			Double PM = effort[0];
			Double devTime = effort[1];
			resultText = "根据公式计算出   PM为：" + PM.intValue()+ "(人.月)\n\n"+
			"\t\t TDEV为：" + devTime.intValue()+"(月)\n\n" + "\t\t 平均所需开发人员为：" + (int)((PM/devTime)+1);
			//设置集成估算结果
			cer.setPM(PM);
			cer.setDevTime(devTime);
		}
		else{
			Double[] efforts = new Double[children.size()];
			QuickEstimationAccess qer_access = new QuickEstimationAccess();
			qer_access.initConnection();
			for(int i=0; i<children.size(); i++)
				if(children.get(i).getEstType().contains("quick"))
					efforts[i] = qer_access.getQuickEstimationByNodeID(children.get(i).getId()).getFormulaEffort();
				else
					efforts[i] = cer_access.getCocomoEstimationByNodeID(children.get(i).getId()).getDevTime()* 160;
			qer_access.disposeConnection();
			Double effort = SimpleIntegratedEstimate.getIntegratedEffort(efforts);
			resultText = "集成估算的 工作量为" + effort.intValue()+ " 小时";
			//设置集成估算结果
			cer.setDevTime(effort/160);
		}
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		//显示同论文总结出的CSBSG公式，计算得到的结果
		Label resultLabel = new Label(resultView, SWT.NONE);
		resultLabel.setText(resultText);
		GUI.createNewTab("集成估算结果", resultView);
		
		// 更新基本信息表中的估算类型
		NodeBasicInformation nbi = new NodeBasicInformation();
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi = nbi_access.getNodeByID(parameters.getnodeID());
		nbi.setEstType("cocomoMultiple");
		nbi_access.updateNode(nbi);
		nbi_access.disposeConnection();
		
		//存储集成估算结果到cocomoEstimation表中
//		cer_access.updateCocomoEstimation(cer);
//		cer_access.disposeConnection();
	}
}
