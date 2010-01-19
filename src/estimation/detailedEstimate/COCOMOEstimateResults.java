package estimation.detailedEstimate;

import gui.GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import estimation.COCOMO;
import estimation.Chart;

public class COCOMOEstimateResults {
	private COCOMOEstimate parameters;

	public COCOMOEstimateResults(COCOMOEstimate param) {
		parameters = param;
	}

	public void show() {
		Double[] effort = COCOMO.getModuleEffortTime((double) parameters
				.getEstimatedSize(), parameters.getScaleFactors(), parameters
				.getEffortMultipliers());

		Composite resultView = new Composite(GUI.getButtomContentArea(),
				SWT.NONE);
		GridLayout resultLayout = new GridLayout(1, false);
		resultLayout.verticalSpacing = 10;
		resultView.setLayout(resultLayout);

		Label result = new Label(resultView, SWT.NONE);
		result.setText("根据公式计算出   PM为：" + effort[0].intValue() + "(人.月)\n\n"
				+ "\t\t TDEV为：" + effort[1].intValue() + "(月)\n\n"
				+ "\t\t 平均所需开发人员为：" + (int) ((effort[0] / effort[1]) + 1));

		String[] phasesSym = { "plansAndRequirements", "productDesign",
				"programming", "integrationAndTest" };
		String[] phasesTex = {"计划与需求","产品设计","编码","集成与测试"};
		Double E = COCOMO.getE(COCOMO.getSumSF(parameters.getScaleFactors()));
		Double[] phaseEfforts = COCOMO.getPhaseEfforts(phasesSym, COCOMO
				.getSizeLevel(parameters.getEstimatedSize()), COCOMO
				.getELevel(E), effort[0]);
		JFreeChart phaseEffortBarChart = Chart.createEffortBarChart("阶段工作量分布", "阶段", Chart
				.createEffortCategoryDataset(phasesTex, phaseEfforts));
		Composite effortFrame = new ChartComposite(resultView, SWT.BORDER,
				phaseEffortBarChart, true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		effortFrame.setLayoutData(gd);
		GUI.createNewTab("详细估算结果", resultView);

		Composite chartView = new Composite(GUI.getButtomContentArea(),
				SWT.NONE);
		GridLayout chartLayout = new GridLayout(2, false);
		chartLayout.verticalSpacing = 10;
		chartView.setLayout(chartLayout);
		String[] activitiesSym = { "requirementsAnalysis", "productDesign",
		                       "programming", "testPlanning", "VV", "projectOffice", "CM/QA",
		                       "manuals" };
		String[] activitiesTex = { "需求", "设计",
				"编码", "测试计划", "VV", "管理活动", "CM/QA",
				"文档" };
		JFreeChart[] activityEffortBarCharts = new JFreeChart[phasesSym.length];
		Composite[] effortFrames = new Composite[phasesSym.length];
		Double[] activityEfforts;
		String title;
		for (int i = 0; i < phasesSym.length; i++) {
			activityEfforts = COCOMO.getActivityEfforts(phasesSym[i], activitiesSym,
					COCOMO.getSizeLevel(parameters.getEstimatedSize()), COCOMO
							.getELevel(E), effort[0], phaseEfforts[i]);
			title = phasesTex[i]+ "阶段的活动工作量分布";
			activityEffortBarCharts[i] = Chart.createEffortBarChart(title, "活动", Chart
					.createEffortCategoryDataset(activitiesTex, activityEfforts));
			effortFrames[i] = new ChartComposite(chartView, SWT.BORDER,
					activityEffortBarCharts[i], true);
			effortFrames[i].setLayoutData(gd);
		}
		GUI.createNewTab("各阶段活动工作量分布", chartView);
	}
}
