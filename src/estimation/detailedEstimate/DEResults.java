package estimation.detailedEstimate;

import java.text.NumberFormat;
import java.util.HashMap;

import gui.Chart;
import gui.tabs.TabContentArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.NodeBasicInformation;

import estimation.COCOMO;

public class DEResults extends TabContentArea{
	private DEInput parameters;

	public DEResults(Composite parent, DEInput param) {
		super(parent, param.getNode());
		parameters = param;
		NodeBasicInformation nbi = new NodeBasicInformation();
		NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
		nbi_access.initConnection();
		nbi = nbi_access.getNodeByID(parameters.getnodeID());
		
		String[] phasesSym = { "plansAndRequirements", "productDesign",
				"programming", "integrationAndTest" };
		String[] phasesTex = { "计划与需求", "产品设计", "编码", "集成与测试" };
		String[] activitiesSym = { "requirementsAnalysis", "productDesign",
				"programming", "testPlanning", "VV", "projectOffice", "CM/QA",
				"manuals" };
		String[] activitiesTex = { "需求", "设计", "编码", "测试计划", "VV", "管理活动",
				"CM/QA", "文档" };

		int size = nbi.getSLOC();
		HashMap<String, String> factorsSF = parameters.getScaleFactors();
		HashMap<String, String> factorsEM = parameters.getEffortMultipliers();
		Double sumSF = COCOMO.getSumSF(factorsSF);
		Double productEM = COCOMO.getProductEM(factorsEM);
		Double SCEDValue = COCOMO.getSCEDValue(factorsEM.get("SCED"));
		Double E = COCOMO.getE(sumSF);
		Double[] effort = COCOMO.getModuleEffortTime((double) size, factorsSF,
				factorsEM);
		Double PM = effort[0];
		Double devTime = effort[1];
		Double[] phaseEfforts = COCOMO.getPhaseEfforts(phasesSym, COCOMO
				.getSizeLevel(size), COCOMO
				.getELevel(E), effort[0]);

		// 详细估算结果
		this.setLayout(new FillLayout());
		ScrolledComposite resultScroll = new ScrolledComposite(this,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		resultScroll.setExpandHorizontal(true);
		resultScroll.setExpandVertical(true);
		Composite resultView = new Composite(resultScroll,
		SWT.BORDER_DOT);
		GridLayout resultLayout = new GridLayout(2, true);
		resultLayout.verticalSpacing = 10;
		resultView.setLayout(resultLayout);
		Label result = new Label(resultView, SWT.NONE);
		NumberFormat format = NumberFormat.getInstance();
		result.setText("根据公式计算出   PM为：" + format.format(PM) + "(人.月)\n\n"
				+ "\t\t TDEV为：" + format.format(devTime) + "(月)\n\n"
				+ "\t\t 平均所需开发人员为：" + format.format(PM / devTime));
		// 阶段工作量分布
		JFreeChart phaseEffortBarChart = Chart.createEffortBarChart("阶段工作量分布",
				"阶段", Chart
						.createEffortCategoryDataset(phasesTex, phaseEfforts));
		Composite effortFrame = new ChartComposite(resultView, SWT.BORDER,
				phaseEffortBarChart, true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		effortFrame.setLayoutData(gd);
		// 阶段的活动工作量
		JFreeChart[] activityEffortBarCharts = new JFreeChart[phasesSym.length];
		Composite[] effortFrames = new Composite[phasesSym.length];
		Double[] activityEfforts;
		String title;
		for (int i = 0; i < phasesSym.length; i++) {
			activityEfforts = COCOMO.getActivityEfforts(phasesSym[i],
					activitiesSym, COCOMO.getSizeLevel(size), COCOMO.getELevel(E),
					effort[0], phaseEfforts[i]);
			title = phasesTex[i] + "阶段的活动工作量分布";
			activityEffortBarCharts[i] = Chart.createEffortBarChart(title,
					null, Chart.createEffortCategoryDataset(activitiesTex,
							activityEfforts));
			effortFrames[i] = new ChartComposite(resultView, SWT.BORDER,
					activityEffortBarCharts[i], true);
			effortFrames[i].setLayoutData(gd);
		}
		resultScroll.setContent(resultView);
		resultScroll.setMinSize(500,400);

		// 更新基本信息表中的估算类型
		nbi.setEstType("cocomoSimple");
		nbi_access.updateNode(nbi);
		nbi_access.disposeConnection();

		// 更新cocomo估算结果
		CocomoEstimationRecord.saveCocomoEstimation(parameters.getnodeID(), parameters
				.getEMtype(), sumSF, productEM, SCEDValue, PM, devTime,
				factorsSF, factorsEM);

	}

}