package estimation.detailedEstimate;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Set;

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

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.EstimateNode;

import estimation.COCOMO;

public class DEResults extends TabContentArea {
	private DEInput parameters;
	private final String[] phasesSym = { "plansAndRequirements",
			"productDesign", "programming", "integrationAndTest" };
	private final String[] phasesTex = { "计划与需求", "产品设计", "编码", "集成与测试" };
	private final String[] activitiesSym = { "requirementsAnalysis",
			"productDesign", "programming", "testPlanning", "VV",
			"projectOffice", "CM/QA", "manuals" };
	private final String[] activitiesTex = { "需求", "设计", "编码", "测试计划", "VV",
			"管理活动", "CM/QA", "文档" };
	private final String[] risksSym = { "totalProjectRisk", "scheduleRisk",
			"productRisk", "platformRisk", "personnelRisk", "processRisk",
			"reuseRisk" };
	private final String[] risksTex = { "总项目风险", "进度风险", "产品风险", "平台风险",
			"人员风险", "过程风险", "重用风险" };

	public DEResults(Composite parent, DEInput param, boolean isOpen) {
		super(parent, param.getNode());
		parameters = param;

		int size = (Integer)parameters.getNode().get("estSLOC");
		Double PM, devTime, sumSF;
		HashMap<String, Object> factors = new HashMap<String,Object>();//存储所有SF与EM因子等级值，用于风险估算

		// 根据用户输入，处理详细估算数据
		if (!isOpen) {
			HashMap<String, String> factorsSF = parameters.getScaleFactors();
			HashMap<String, String> factorsEM = parameters
					.getEffortMultipliers();
			factors.putAll(factorsSF);
			factors.putAll(factorsEM);
			sumSF = COCOMO.getSumSF(factorsSF);
			Double productEM = COCOMO.getProductEM(factorsEM);
			Double SCEDValue = COCOMO.getSCEDValue(factorsEM.get("SCED"));

			Double[] effort = COCOMO.getModuleEffortTime((double) size,
					factorsSF, factorsEM);
			PM = effort[0];
			devTime = effort[1];

			// 更新基本信息表中的估算类型
			NodeBasicInfoAccess nbi_access = new NodeBasicInfoAccess();
			EstimateNode nbi = (EstimateNode) nbi_access
					.getByID(parameters.getTabID());
			nbi.set("estType", "cocomoSimple");
			nbi_access.update(nbi);

			// 更新cocomo估算结果
			CocomoEstimationRecord.saveCocomoEstimation(parameters.getTabID(),
					parameters.getEMtype(), sumSF, productEM, SCEDValue, PM,
					devTime, factorsSF, factorsEM);
		}
		// 从数据库得到详细估算数据
		else {
			CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
			CocomoEstimationRecord cer = cer_access
					.getCocomoEstimationByNodeID(this.getTabID());

			PM = (Double) cer.get("PM");
			devTime = (Double) cer.get("devTime");
			sumSF = (Double) cer.get("sumSF");
			factors.putAll((cer.attributes));
		}
		// 显示详细估算结果，生成chart
		createComResults(size, PM, devTime, sumSF, factors);
	}

	// 参数：PM，devTime，size，sumSF，phasesSym,phasesTex,activitiesSym, activitiesTex
	private void createComResults(int size, Double PM, Double devTime,
			Double sumSF, HashMap<String, Object> factors){
		// 详细估算值
		Double E = COCOMO.getE(sumSF);
		Double[] phaseEfforts = COCOMO.getPhaseEfforts(phasesSym, COCOMO
				.getSizeLevel(size), COCOMO.getELevel(E), PM);
		Double[] scheduleTimes = COCOMO.getScheduleTime(phasesSym, devTime);
		Double[] persionDistribution = COCOMO.getPersonDistribution(
				phaseEfforts, scheduleTimes);

		// 界面设置
		this.setLayout(new FillLayout());
		ScrolledComposite resultScroll = new ScrolledComposite(this, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		resultScroll.setExpandHorizontal(true);
		resultScroll.setExpandVertical(true);
		Composite resultView = new Composite(resultScroll, SWT.BORDER_DOT);
		GridLayout resultLayout = new GridLayout(2, true);
		resultLayout.verticalSpacing = 10;
		resultView.setLayout(resultLayout);

		// 详细估算结果
		Label result = new Label(resultView, SWT.NONE);
		NumberFormat format = NumberFormat.getInstance();
		result.setText("根据公式计算出   PM为：" + format.format(PM) + " 人.月\n\n"
				+ "\t\t TDEV为：" + format.format(devTime) + " 月\n\n"
				+ "\t\t 平均所需开发人员为：" + format.format(PM / devTime) + " 人");

		// 阶段工作量分布图
		JFreeChart phaseEffortBarChart = Chart.createBarChart("阶段工作量分布", "阶段",
				"工作量(人.月)", Chart
						.createCategoryDataset(phasesTex, phaseEfforts));
		createChartComposite(resultView, phaseEffortBarChart);

		// 阶段的活动工作量图
		JFreeChart[] activityEffortBarCharts = new JFreeChart[phasesSym.length];
		Double[] activityEfforts;
		String title;
		for (int i = 0; i < phasesSym.length; i++) {
			activityEfforts = COCOMO.getActivityEfforts(phasesSym[i],
					activitiesSym, COCOMO.getSizeLevel(size), COCOMO
							.getELevel(E), PM, phaseEfforts[i]);
			title = phasesTex[i] + "阶段的活动工作量分布";
			activityEffortBarCharts[i] = Chart.createBarChart(title, null,
					"工作量(人.月)", Chart.createCategoryDataset(activitiesTex,
							activityEfforts));
			createChartComposite(resultView, activityEffortBarCharts[i]);
		}

		// 阶段进度分布图
		JFreeChart phaseScheduleGanttChart = Chart.createGanttChart("项目进度甘特图",
				"阶段", null, Chart.createScheduleCategoryDataset(phasesTex,
						scheduleTimes));
		createChartComposite(resultView, phaseScheduleGanttChart);

		// 阶段人员分布图
		JFreeChart personDistributionChart = Chart.createStepLineChart("人员分布图",
				"阶段", "人员量(人)", Chart.createCategoryDataset(phasesTex,
						persionDistribution));
		createChartComposite(resultView, personDistributionChart);

		// 风险估算值
		Set<String> keys = factors.keySet();
		for(String key: keys)
		{
			System.out.println(key +":"+factors.get(key));
		}
		Label riskResult = new Label(resultView, SWT.NONE);
		Double[] riskValues = COCOMO.getRiskAccessment(risksSym, factors);
		String text = new String();
		for(int i=0; i<riskValues.length; i++)
			text += risksTex[i]+": " + riskValues[i] + "\n";
		riskResult.setText(text);

		resultScroll.setContent(resultView);
		resultScroll.setMinSize(500, 800);

	}

	private void createChartComposite(Composite parent, JFreeChart chart) {
		Composite chartFrame = new ChartComposite(parent, SWT.BORDER, chart,
				true);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		chartFrame.setLayoutData(gd);
	}
}
