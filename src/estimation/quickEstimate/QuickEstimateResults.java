package estimation.quickEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import gui.GUI;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import dataManager.dataEntities.CSBSG;
import dataManager.dataEntities.ISBSG;
import estimation.Chart;

public class QuickEstimateResults {
	private QuickEstimate quickEstimate;

	public QuickEstimateResults(QuickEstimate estimate) {
		quickEstimate = estimate;
	}

	public void show() {

		HashMap<String, String> factors;
		int projectSize;
		// 根据条件搜索到的生产率数组
		ArrayList<Double> arrayPI;
		// 由公式计算得到的effort值
		Double formulaEffort;
		// 由历史数据得到的effort值
		Double historyEffort;
		// for the statistic of median,mean and standard deviation
		DescriptiveStatistics stats = new DescriptiveStatistics();

		Composite resultView = new Composite(GUI.getButtomContentArea(),
				SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		//快速估算数据处理
		if (quickEstimate.getDataType() == "csbsg") {
			CSBSG csbsg = new CSBSG();
			// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
			factors = quickEstimate.getCSBSGFactors();
			projectSize = quickEstimate.getCSBSGSize();
			System.out.println("projectSize = " + projectSize);
			arrayPI = csbsg.getProductivity(projectSize, factors);
			formulaEffort = csbsg.getEqnEffort((double) projectSize, factors);
			for (double PI : arrayPI)
				stats.addValue(PI);
			historyEffort = projectSize / stats.getPercentile(50);
		} else {
			ISBSG isbsg = new ISBSG();
			// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
			factors = quickEstimate.getISBSGFactors();
			projectSize = quickEstimate.getISBSGSize();
			System.out.println("projectSize = " + projectSize);
			arrayPI = isbsg.getPDR(projectSize, factors);
			formulaEffort = isbsg.getEqnPDR(factors) * projectSize;
			for (double PI : arrayPI)
				stats.addValue(PI);
			historyEffort = stats.getPercentile(50) * projectSize;
		}

		//快速估算结果显示
		Label PI = new Label(resultView, SWT.NONE);
		PI.setText("根据公式计算出的工作量为：" + formulaEffort.intValue() + " 小时");
		if (arrayPI.size() == 0) {
			Label noResult = new Label(resultView, SWT.SINGLE);
			noResult.setText("没有搜索到任何相关历史数据，无法显示“历史工作量数值”与“工作量的蒙特卡罗图”");
		} else {
			System.out.println("PI median: " + stats.getPercentile(50));
			Label PImedian = new Label(resultView, SWT.NONE);
			PImedian.setText("根据历史项目数据的生产率中位数值计算出的工作量："
					+ historyEffort.intValue() + " 小时");

			// 显示工作量的蒙特卡罗图
			System.out.println("PI mean: " + stats.getMean());
			System.out.println("PI standardDeviation: "
					+ stats.getStandardDeviation());
			if (arrayPI.size() == 1) {
				Label noResult = new Label(resultView, SWT.SINGLE);
				noResult.setText("没有搜索到足够的相关历史数据，无法显示工作量的蒙特卡罗图”");
			} else {
				JFreeChart monteCarloChart = Chart.createMonteCarloChart(Chart
						.createQuickDataSet(quickEstimate.getDataType(),
								projectSize, stats.getMean(), stats
										.getStandardDeviation()));
				Composite monteCarloFrame = new ChartComposite(resultView,
						SWT.BORDER, monteCarloChart, true);
				// 页面布局
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				monteCarloFrame.setLayoutData(gData);
			}
		}

		GUI.createNewTab("快速估算结果", resultView);
	}
}
