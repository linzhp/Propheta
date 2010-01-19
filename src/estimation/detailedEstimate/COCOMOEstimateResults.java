package estimation.detailedEstimate;

import java.util.HashMap;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import dataManager.dataAccess.NodeBasicInfoAccess;
import dataManager.dataEntities.NodeBasicInformation;

import estimation.COCOMO;
import estimation.Chart;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;

	public COCOMOEstimateResults(COCOMOEstimate param) {
		parameters = param;
	}

	public void show() {
		String[] phases = { "plansAndRequirements", "productDesign",
				"programming", "integrationAndTest" };
		String[] activities = { "requirementsAnalysis", "productDesign",
				"programming", "testPlanning", "VV", "projectOffice", "CM/QA",
				"manuals" };

		int size = parameters.getEstimatedSize();
		HashMap<String, String> factorsSF = parameters.getScaleFactors();
		HashMap<String, String> factorsEM = parameters.getEffortMultipliers();
		Double sumSF = COCOMO.getSumSF(factorsSF);
		Double E = COCOMO.getE(sumSF);
		Double[] effort = COCOMO.getModuleEffortTime((double) size, factorsSF,
				factorsEM);
		Double PM = effort[0];
		Double devTime = effort[1];
		Double[] phaseEfforts = COCOMO.getPhaseEfforts(phases, COCOMO
				.getSizeLevel(size), COCOMO.getELevel(E), PM);

		// 详细估算结果
		Composite resultView = new Composite(GUI.getButtomContentArea(),
				SWT.NONE);
		GridLayout resultLayout = new GridLayout(1, false);
		resultLayout.verticalSpacing = 10;
		resultView.setLayout(resultLayout);
		Label result = new Label(resultView, SWT.NONE);
		result.setText("根据公式计算出   PM为：" + PM.intValue() + "(人.月)\n\n"
				+ "\t\t TDEV为：" + devTime.intValue() + "(月)\n\n"
				+ "\t\t 平均所需开发人员为：" + (int) ((PM / devTime) + 1));
		// 阶段工作量分布
		JFreeChart phaseEffortBarChart = Chart.createEffortBarChart("阶段工作量分布",
				"阶段", Chart.createEffortCategoryDataset(phases, phaseEfforts));
		Composite effortFrame = new ChartComposite(resultView, SWT.BORDER,
				phaseEffortBarChart, true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		effortFrame.setLayoutData(gd);
		GUI.createNewTab("详细估算结果", resultView);

		// 阶段的活动工作量
		Composite chartView = new Composite(GUI.getButtomContentArea(),
				SWT.NONE);
		GridLayout chartLayout = new GridLayout(2, false);
		chartLayout.verticalSpacing = 10;
		chartView.setLayout(chartLayout);
		JFreeChart[] activityEffortBarCharts = new JFreeChart[phases.length];
		Composite[] effortFrames = new Composite[phases.length];
		Double[] activityEfforts;
		String title;
		for (int i = 0; i < phases.length; i++) {
			activityEfforts = COCOMO.getActivityEfforts(phases[i], activities,
					COCOMO.getSizeLevel(size), COCOMO.getELevel(E), PM,
					phaseEfforts[i]);
			title = phases[i] + "阶段的活动工作量分布";
			activityEffortBarCharts[i] = Chart.createEffortBarChart(title,
					"活动", Chart.createEffortCategoryDataset(activities,
							activityEfforts));
			effortFrames[i] = new ChartComposite(chartView, SWT.BORDER,
					activityEffortBarCharts[i], true);
			effortFrames[i].setLayoutData(gd);
		}
		GUI.createNewTab("各阶段活动工作量分布", chartView);

		// 更新基本信息表中的估算类型
		NodeBasicInformation nbi = new NodeBasicInformation();
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi = nbi_access.getNodeByID(parameters.getnodeID());
		nbi.setEstType("cocomoSimple");
		nbi_access.updateNode(nbi);
		nbi_access.disposeConnection();

		// 更新cocomo估算结果
		//COCOMO.saveCocomoEstimation(parameters.getnodeID(),
		// parameters.getEMtype(), sumSF, productEM, SCEDValue, PM, devTime,
		// factorsSF, factorsEM)

	}
}
