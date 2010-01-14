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

		String[] phases = { "plansAndRequirements", "productDesign",
				"programming", "integrationAndTest" };
		Double E = COCOMO.getE(COCOMO.getSumSF(parameters.getScaleFactors()));
		Double[] phaseEfforts = COCOMO.getPhaseEfforts(phases, COCOMO
				.getSizeLevel(parameters.getEstimatedSize()), COCOMO
				.getELevel(E), effort[0]);
		JFreeChart phaseEffortBarChart = Chart.createEffortBarChart("阶段工作量分布", "阶段", Chart
				.createEffortCategoryDataset(phases, phaseEfforts));
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

		String[] activities = { "requirementsAnalysis", "productDesign",
				"programming", "testPlanning", "VV", "projectOffice", "CM/QA",
				"manuals" };
		JFreeChart[] activityEffortBarCharts = new JFreeChart[phases.length];
		Composite[] effortFrames = new Composite[phases.length];
		Double[] activityEfforts;
		String title;
		for (int i = 0; i < phases.length; i++) {
			activityEfforts = COCOMO.getActivityEfforts(phases[i], activities,
					COCOMO.getSizeLevel(parameters.getEstimatedSize()), COCOMO
							.getELevel(E), effort[0], phaseEfforts[i]);
			title = phases[i]+ "阶段的活动工作量分布";
			activityEffortBarCharts[i] = Chart.createEffortBarChart(title, "活动", Chart
					.createEffortCategoryDataset(activities, activityEfforts));
			effortFrames[i] = new ChartComposite(chartView, SWT.BORDER,
					activityEffortBarCharts[i], true);
			effortFrames[i].setLayoutData(gd);
		}
		GUI.createNewTab("各阶段活动工作量分布", chartView);
	}
}
