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

import dataManager.COCOMO;
import dataManager.CSBSG;

public class QuickEstimateResults {
	private QuickEstimate quickEstimate;
	
	public QuickEstimateResults(QuickEstimate estimate){
		quickEstimate = estimate;
	}
	
	public void show()
	{
		
		Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		resultView.setLayout(layout);

		CSBSG csbsg = new CSBSG();
		//此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
		HashMap<String,String> factors = quickEstimate.getFactors();
		int projectSize = quickEstimate.getEstimatedSize();
		System.out.println("projectSize = " + projectSize);
		ArrayList<Double> arrayPI = csbsg.getProductivity(projectSize,factors);
		// 此处0.2为规模的误差范围，可调，arraySizeEffort:projectSize,effort
		ArrayList<Double[]> arraySizeEffort = csbsg.getEffort(projectSize, 0.2);
		//for the statistic of median,mean and standard deviation 
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
		//显示同论文总结出的CSBSG公式，计算得到的结果
		Label PI = new Label(resultView, SWT.NONE);
		PI.setText("根据公式计算出的工作量为：" + csbsg.getEqnEffort((double)projectSize, factors));
		if (arrayPI.size() == 0) {
			Label noResult = new Label(resultView, SWT.SINGLE);
			noResult.setText("没有搜索到任何相关历史数据，无法显示“生产率中位数值”与“工作量的蒙特卡罗图”");
		} else {
			// 显示生产率的中位数
			for( int i = 0; i < arrayPI.size(); i++) {
		        stats.addValue(arrayPI.get(i));
			}
			System.out.println("PI median: " + stats.getPercentile(50));
			Label PImedian = new Label(resultView, SWT.NONE);
			PImedian.setText("根据历史项目数据的生产率中位数值计算出的工作量：" + projectSize
					/ stats.getPercentile(50) + " 小时");

			// 显示工作量的蒙特卡罗图
			System.out.println("PI mean: " + stats.getMean());
			System.out.println("PI standardDeviation: " + stats.getStandardDeviation());
			if(arrayPI.size() == 1){
				Label noResult = new Label(resultView, SWT.SINGLE);
				noResult.setText("没有搜索到足够的相关历史数据，无法显示工作量的蒙特卡罗图”");
			}
			else{
			JFreeChart monteCarloChart = Chart
					.createMonteCarloChart(Chart
							.createMonteCarloDataSet(projectSize, stats.getMean(),
									stats.getStandardDeviation()));
			Composite monteCarloFrame = new ChartComposite(resultView,
					SWT.BORDER, monteCarloChart, true);
			//页面布局
			GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
			monteCarloFrame.setLayoutData(gData);
			}
		}
		
		GUI.createNewTab("快速估算结果", resultView);
	}
}
