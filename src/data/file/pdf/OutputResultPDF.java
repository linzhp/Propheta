package data.file.pdf;

import java.sql.SQLException;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import gui.Chart;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.DataBaseAccess;
import data.database.dataAccess.NodeBasicInfoAccess;

import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import estimation.COCOMO;

public class OutputResultPDF {
	int nodeID;
	Entity nb;
	PDFUtility pdf;
	int psize;
	int fsize;
	String estType;
	String filepath;

	public OutputResultPDF(int id, String filePath) {
		// connect database
		new DataBaseAccess();
		this.nodeID = id;
		this.filepath = filePath;
		pdf = new PDFUtility(filePath);
	}

	public void saveResult() {
		// 1.save basic information
		pdf.openPDF();
		saveBasicInfo();
		// 判断估算类型
		if (estType.equals("quick")) {
			saveQuickInfo();
		}
		if (estType.equals("cocomoSimple")) {
			saveCocomoResult();
		}
		pdf.closePDF();
		org.eclipse.swt.program.Program.launch(filepath);
	}

	public void saveBasicInfo() {

		FormatUtility fu = new FormatUtility();
		NodeBasicInfoAccess Nia = new NodeBasicInfoAccess();
		nb = Nia.getByID(nodeID);
		// 共12项信息
		pdf.addChapter("项目基本信息");
		// 项目名称
		pdf.addParagra(0, "项目名称:" + nb.get("name"));
		// 项目描述
		System.out.println(nb.get("name"));
		resultFormat("项目描述", nb.get("description"));
		// 团队规模
		resultFormat("团队规模", nb.get("teamSize"));
		// 项目周期
		resultFormat("项目周期", nb.get("duration"));
		// 业务领域
		resultFormat("业务领域", fu.ConvertKeyToShow(1, nb.get("businessArea")));
		// 代码行数

		this.psize = (Integer) (nb.get("estSLOC"));
		resultFormat("代码行数", Integer.toString(this.psize));
		// 功能点数目
		this.fsize = (Integer) (nb.get("functionPoints"));
		resultFormat("功能点数目", Integer.toString(fsize));
		// 开发类型
		resultFormat("开发类型", fu.ConvertKeyToShow(2, nb.get("developmentType")));
		// 开发平台
		resultFormat("开发平台", fu.ConvertKeyToShow(4, nb
				.get("developmentPlatform")));
		// 开发技术
		resultFormat("开发技术", fu.ConvertKeyToShow(3, nb
				.get("developmentTechniques")));
		// 语言类型
		resultFormat("语言类型", fu.ConvertKeyToShow(5, nb.get("functionPoints ")));
		// 开发语言
		resultFormat("开发语言", fu.ConvertKeyToShow(6, nb.get("functionPoints ")));
		// 估算类型
		this.estType = nb.get("estType").toString();

	}

	public void saveQuickInfo() {

		// 快速估算数据
		pdf.addChapter("快速估算结果");
		pdf.addParagra(0, "估算参照" + nb.get("dataType") + "数据");
		// formulaEffort_out = qer.getFormulaEffort();
		pdf.addParagra(0, "根据公式计算出来的工作量为：" + nb.get("formulaEffort") + "小时");
		// historyEffort_out = qer.getHistoryEffort();
		pdf.addParagra(0, "根据历史项目数据的生产率中位数值计算出的工作量：：" + nb.get("historyEffort")
				+ "小时");
		// 输出蒙特卡罗图
		if ((Double) nb.get("meanProductivity") == 0) {
			pdf.addParagra(0, "没有搜索到足够的相关历史数据，无法显示工作量的蒙特卡罗图");
		} else {
			int projectSize;
			if (nb.get("dataType").toString().contains("csbsg")) {
				projectSize = this.psize;
			} else {
				projectSize = this.fsize;
			}
			// 显示图
			// 假设数据库没有存储图，这里直接调用JfreeChaer再次生成图
			JFreeChart monteCarloChart = Chart.createXYLineChart("工作量蒙特卡罗图",
					"工时", null, Chart.createQuickDataSet(nb.get("dataType")
							.toString(), projectSize, (Double) nb
							.get("meanProductivity"), (Double) nb
							.get("stanDevProductivity")));
			pdf.addChart(monteCarloChart, 400, 300, 0.6);

		}
	}

	public void saveCocomoResult() {
		CocomoEstimationAccess cer = new CocomoEstimationAccess();
		CocomoEstimationRecord cr = cer.getCocomoEstimationByNodeID(nodeID);

		// 参数：PM，devTime，size，sumSF，phasesSym,phasesTex,activitiesSym,
		// activitiesTex
		pdf.addChapter("详细估算结果");
		pdf.addParagra(0, "根据公式计算出：");
		NumberFormat format = NumberFormat.getInstance();
		double PM = (Double) cr.get("PM");
		pdf.addParagra(0, "PM为" + format.format(PM) + "人.月");
		double devTime = (Double) cr.get("devTime");
		pdf.addParagra(0, "TDEV为" + format.format(devTime) + "月");
		pdf.addParagra(0, "平均所需开发人员为：" + format.format(PM / devTime) + " 人");

		// 输出7个图
		double sumSF = (Double) cr.get("sumSF");
		;
		String[] phasesSym = { "plansAndRequirements", "productDesign",
				"programming", "integrationAndTest" };
		String[] phasesTex = { "计划与需求", "产品设计", "编码", "集成与测试" };
		String[] activitiesSym = { "requirementsAnalysis", "productDesign",
				"programming", "testPlanning", "VV", "projectOffice", "CM/QA",
				"manuals" };
		String[] activitiesTex = { "需求", "设计", "编码", "测试计划", "VV", "管理活动",
				"CM/QA", "文档" };
		Double E;
		try {
			E = COCOMO.getE(sumSF);
			Double[] phaseEfforts = COCOMO.getPhaseEfforts(phasesSym, COCOMO
					.getSizeLevel(psize), COCOMO.getELevel(E), PM);
			Double[] scheduleTimes = COCOMO.getScheduleTime(phasesSym, devTime);
			Double[] persionDistribution = COCOMO.getPersonDistribution(
					phaseEfforts, scheduleTimes);
			// 阶段工作量分布图
			JFreeChart phaseEffortBarChart = Chart.createBarChart("阶段工作量分布",
					"阶段", "工作量(人.月)", Chart.createCategoryDataset(phasesTex,
							phaseEfforts));
			pdf.addChart(phaseEffortBarChart, 400, 300, 0.8);

			// 阶段的活动工作量图
			JFreeChart[] activityEffortBarCharts = new JFreeChart[phasesSym.length];
			Double[] activityEfforts;
			String title;
			for (int i = 0; i < phasesSym.length; i++) {
				activityEfforts = COCOMO.getActivityEfforts(phasesSym[i],
						activitiesSym, COCOMO.getSizeLevel(psize), COCOMO
								.getELevel(E), PM, phaseEfforts[i]);
				title = phasesTex[i] + "阶段的活动工作量分布";
				activityEffortBarCharts[i] = Chart.createBarChart(title, null,
						"工作量(人.月)", Chart.createCategoryDataset(activitiesTex,
								activityEfforts));
				pdf.addChart(activityEffortBarCharts[i], 400, 300, 0.8);
			}

			// 阶段进度分布图
			JFreeChart phaseScheduleGanttChart = Chart.createGanttChart(
					"项目进度甘特图", "阶段", null, Chart.createScheduleCategoryDataset(
							phasesTex, scheduleTimes));
			pdf.addChart(phaseScheduleGanttChart, 400, 300, 0.8);

			// 阶段人员分布图
			JFreeChart personDistributionChart = Chart.createStepLineChart(
					"人员分布图", "阶段", "人员量(人)", Chart.createCategoryDataset(
							phasesTex, persionDistribution));

			pdf.addChart(personDistributionChart, 400, 300, 0.8);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void resultFormat(String title, String content) {

		if (content != null) {

			pdf.addParagra(0, title + ":" + content);
		}

	}

	private void resultFormat(String title, Object content) {

		if (content != null) {

			pdf.addParagra(0, title + ":" + content);
		}
	}

}
