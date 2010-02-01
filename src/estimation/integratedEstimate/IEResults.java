package estimation.integratedEstimate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import gui.tabs.TabContentArea;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.NodeBasicInformation;
import data.database.dataEntities.QuickEstimationRecord;

import estimation.COCOMO;
import estimation.SimpleIntegratedEstimate;
import estimation.entity.EstimateNode;

public class IEResults extends TabContentArea {
	private IEInput integratedEstimate;

	public IEResults(Composite parent, IEInput integratedEstimate,
			boolean isOpen) {
		super(parent, integratedEstimate.getNode());
		this.integratedEstimate = integratedEstimate;
		
		// 根据用户输入，处理集成估算数据
		if (!isOpen) {
			CocomoEstimationAccess cer_access = new CocomoEstimationAccess();

			ArrayList<EstimateNode> children = integratedEstimate
					.getSelectedChildren();
			int tag = 0;
			for (EstimateNode child : children)
				if (!child.getEstType().contains("cocomoSimple")) {
					tag = 1;
					break;
				}
			if (tag == 0) {
				Double[] sizes = new Double[children.size()];
				Double[] productEMs = new Double[children.size()];
				HashMap<String, String> factorsSF = integratedEstimate
						.getScaleFactors();
				String SCEDLevel = integratedEstimate.getSCED();
				for (int i = 0; i < children.size(); i++) {
					sizes[i] = (double) children.get(i).getSLOC();
					productEMs[i] = cer_access.getCocomoEstimationByNodeID(
							children.get(i).getId()).getProductEM();
				}
				Double[] efforts = COCOMO.getIntegratedEffortTime(sizes,
						productEMs, factorsSF, SCEDLevel);
				Double PM = efforts[0];
				Double devTime = efforts[1];
				createComCocomoResults(PM, devTime);
				// 存储集成估算结果
				saveCocomoEstimation(integratedEstimate.getnodeID(),
						"multiple", PM, devTime);
				// 更新基本信息表中的估算类型
				NodeBasicInformation.updateEstType(integratedEstimate
						.getnodeID(), "cocomoMultiple");
			} else {
				Double[] efforts = new Double[children.size()];
				QuickEstimationAccess qer_access = new QuickEstimationAccess();
				for (int i = 0; i < children.size(); i++)
					if (children.get(i).getEstType().contains("quick"))
						efforts[i] = qer_access.getQuickEstimationByNodeID(
								children.get(i).getId()).getFormulaEffort();
					else
						efforts[i] = cer_access.getCocomoEstimationByNodeID(
								children.get(i).getId()).getDevTime() * 152;
				Double effort = SimpleIntegratedEstimate.getIntegratedEffort(efforts);
				//显示集成估算结果
				createComQuickResults(effort);
				// 存储集成估算结果
				saveQuickEstimation(integratedEstimate.getnodeID(), "multiple",
						effort);
				// 更新基本信息表中的估算类型
				NodeBasicInformation.updateEstType(integratedEstimate
						.getnodeID(), "quickMultiple");
			}
		}
		// 从数据库得到集成估算数据
		else
		{
			String estType = this.integratedEstimate.getNode().getEstType();
			
			if(estType.contains("cocomoMultiple")){
				CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
				CocomoEstimationRecord cer = cer_access.getCocomoEstimationByNodeID(this.getnodeID());
				Double PM = cer.getPM();
				Double devTime = cer.getDevTime();
				
				createComCocomoResults(PM, devTime);
			}
			else if(estType.contains("quickMultiple"))
			{
				QuickEstimationAccess qer_access = new QuickEstimationAccess();
				QuickEstimationRecord cer = qer_access.getQuickEstimationByNodeID(this.getnodeID());
				Double effort = cer.getFormulaEffort();
				
				createComQuickResults(effort);
			}
		}
			
	}

	private void saveCocomoEstimation(int nodeID, String EMType, Double PM,
			Double devTime) {
		CocomoEstimationRecord cer = new CocomoEstimationRecord();
		CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
		cer = cer_access.getCocomoEstimationByNodeID(nodeID);
		cer.setEMType(EMType);
		cer.setPM(PM);
		cer.setDevTime(devTime);
		cer_access.updateCocomoEstimation(cer);
	}

	// 更新某条quickEstimation数据
	private void saveQuickEstimation(int nodeID, String dataType,
			Double formula_Effort) {
		QuickEstimationRecord qer = new QuickEstimationRecord();
		QuickEstimationAccess qer_access = new QuickEstimationAccess();
		qer = qer_access.getQuickEstimationByNodeID(nodeID);

		qer.setDataType(dataType);
		qer.setFormulaEffort(formula_Effort);

		qer_access.updateQuickEstimation(qer);
	}

	public void createComCocomoResults(Double PM, Double devTime) {
		NumberFormat format = NumberFormat.getInstance();
		String resultText = "根据公式计算出   PM为：" + format.format(PM) + "(人.月)\n\n"
				+ "\t\t TDEV为：" + format.format(devTime) + "(月)\n\n"
				+ "\t\t 平均所需开发人员为：" + format.format(PM / devTime);
		createComResults(resultText);
	}

	public void createComQuickResults(Double effort) {
		String resultText = "集成估算的 工作量为" + effort.intValue() + " 小时";
		createComResults(resultText);
	}

	private void createComResults(String resultText) {
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		this.setLayout(layout);

		// 显示同论文总结出的CSBSG公式，计算得到的结果
		Label resultLabel = new Label(this, SWT.NONE);
		resultLabel.setText(resultText);
	}
}
