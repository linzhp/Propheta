package estimation.quickEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataAccess.QuickEstimationAccess;
import data.database.dataEntities.QuickEstimationRecord;

import estimation.CSBSG;
import estimation.ISBSG;

import gui.Chart;
import gui.tabs.TabContentArea;

public class QEResults extends TabContentArea {


	public QEResults(Composite parent, QEInput quickEstimate, boolean isOpen) {
		super(parent, quickEstimate.getNode());

		String dataType;
		int projectSize = 0;
		// 由公式计算得到的effort值
		Double formulaEffort = 0.0;
		// 由历史数据得到的effort值
		Double historyEffort = 0.0;
		Double meanProductivity = 0.0;
		Double stanDevProductivity = 0.0;

		// 根据用户输入，处理快速估算数据
		if (!isOpen) {
			HashMap<String, String> factors;
			// 根据条件搜索到的生产率数组
			ArrayList<Double> arrayPI;
			// for the statistic of median,mean and standard deviation
			DescriptiveStatistics stats = new DescriptiveStatistics();
			dataType = quickEstimate.getDataType();

			// 根据用户输入，处理快速估算数据
			if (dataType.contains("csbsg")) {
				// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
				factors = quickEstimate.getCSBSGFactors();
				projectSize = quickEstimate.getNode().getSLOC();
				arrayPI = CSBSG.getProductivity(projectSize, factors);
				formulaEffort = CSBSG.getEqnEffort((double) projectSize,
						factors);
				for (double PI : arrayPI)
					stats.addValue(PI);
				historyEffort = projectSize / stats.getPercentile(50);
			} else {
				// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
				factors = quickEstimate.getISBSGFactors();
				projectSize = quickEstimate.getNode().getFunctionPoints();
				arrayPI = ISBSG.getPDR(projectSize, factors);
				formulaEffort = ISBSG.getEqnPDR(factors) * projectSize;
				for (double PI : arrayPI)
					stats.addValue(PI);
				historyEffort = stats.getPercentile(50) * projectSize;
			}

			if (arrayPI.size() > 1) {
				meanProductivity = stats.getMean();
				stanDevProductivity = stats.getStandardDeviation();
			} else {
				meanProductivity = 0.0;
				stanDevProductivity = 0.0;
				if (arrayPI.size() == 0)
					historyEffort = 0.0;
			}
			// 存储数据
			new NodeBasicInfoAccess().updateEstType(quickEstimate.getnodeID(),
					"quick");
			QuickEstimationRecord.saveQuickEstimation(
					quickEstimate.getnodeID(), quickEstimate.getDataType(),
					formulaEffort, historyEffort, meanProductivity,
					stanDevProductivity);
		}
		// 从数据库得到快速估算数据
		else {
			QuickEstimationAccess qer_access = new QuickEstimationAccess();
			
			dataType = (String)qer_access.getQuickEstimationByNodeID(this.getnodeID()).get("dataType");
			if (dataType.contains("csbsg"))
				projectSize = quickEstimate.getNode().getSLOC();
			else
				projectSize = quickEstimate.getNode().getFunctionPoints();

			QuickEstimationRecord qer = qer_access
					.getQuickEstimationByNodeID(node.getId());

			formulaEffort = (Double)qer.get("formulaEffort");
			historyEffort = (Double)qer.get("historyEffort");
			meanProductivity = (Double)qer.get("meanProductivity");
			stanDevProductivity = (Double)qer.get("stanDevProductivity");

		}
		// 快速估算结果显示
		createComResults(projectSize, dataType, formulaEffort, historyEffort,
				meanProductivity, stanDevProductivity);
	}

	private void createComResults(int projectSize, String dataType,
			Double formulaEffort, Double historyEffort,
			Double meanProductivity, Double stanDevProductivity) {
		
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		this.setLayout(layout);

		Label PI = new Label(this, SWT.NONE);
		PI.setText("根据公式计算出的工作量为：" + formulaEffort.intValue() + " 小时");
		if (historyEffort == 0) {
			Label noResult = new Label(this, SWT.SINGLE);
			noResult.setText("没有搜索到任何相关历史数据，无法显示“历史工作量数值”与“工作量的蒙特卡罗图”");
		} else {
			System.out.println("PI median: " + historyEffort);
			Label PImedian = new Label(this, SWT.NONE);
			PImedian.setText("根据历史项目数据的生产率中位数值计算出的工作量："
					+ historyEffort.intValue() + " 小时");

			// 显示工作量的蒙特卡罗图
			if (meanProductivity == 0) {
				Label noResult = new Label(this, SWT.SINGLE);
				noResult.setText("没有搜索到足够的相关历史数据，无法显示工作量的蒙特卡罗图”");
			} else {
				JFreeChart monteCarloChart = Chart.createXYLineChart("工作量蒙特卡罗图", "工时", null, Chart
						.createQuickDataSet(dataType, projectSize,
								meanProductivity, stanDevProductivity));
				Composite monteCarloFrame = new ChartComposite(this,
						SWT.BORDER, monteCarloChart, true);
				// 页面布局
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				monteCarloFrame.setLayoutData(gData);
			}
		}
	}

}
