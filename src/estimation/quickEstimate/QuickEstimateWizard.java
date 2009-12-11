package estimation.quickEstimate;

import gui.GUI;
import dataManager.CSBSG;
import entity.EstimateNode;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;


public class QuickEstimateWizard extends Wizard {

	public void addPages() {
		addPage(new PIPage());
	}

	

	private PIPage getPIPage() {
		return (PIPage) getPage(PIPage.PAGE_NAME);
	}

	private int getSize() {
		return getPIPage().getSize();
	}

	private HashMap<String, String> getFactors() {
		return getPIPage().getFactors();
	}

	public boolean canFinish() {
		if (getContainer().getCurrentPage() instanceof PIPage
				&& getPIPage().canFinish())
			return true;	
		else
			return false;
	}

	public boolean performFinish() {
		if (this.canFinish()) {
						
			CSBSG csbsg = new CSBSG();
			HashMap<String,String> factors = getFactors();
			factors.remove("duration");
			ArrayList<Double> arrayPI = csbsg.getProductivity(getSize(),factors);
			// 此处0.2为规模的误差范围，可调，arraySizeEffort:projectSize,effort
			ArrayList<Double[]> arraySizeEffort = csbsg.getEffort(getSize(), 0.2);
			//for the statistic of median,mean and standard deviation 
			DescriptiveStatistics stats = new DescriptiveStatistics();
			
			//在GUI.getButtomContentArea()上生成显示结果的 composite：resultView
			GUI.getButtomContentArea().disposeCurrentPage();
			Composite resultView = new Composite(GUI.getButtomContentArea(), SWT.NONE);
			GridLayout layout = new GridLayout(1, false);
			layout.verticalSpacing = 10;
			resultView.setLayout(layout);

			//显示同论文总结出的CSBSG公式，计算得到的结果
			Label PI = new Label(resultView, SWT.NONE);
			PI.setText("根据公式计算出的工作量为：" + csbsg.getEqnEffort((double)getSize(), getFactors()));
			
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
				PImedian.setText("根据历史项目数据的生产率中位数值计算出的工作量：" + getSize()
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
								.createMonteCarloDataSet(getSize(), stats.getMean(),
										stats.getStandardDeviation()));
				Composite monteCarloFrame = new ChartComposite(resultView,
						SWT.BORDER, monteCarloChart, true);
				//页面布局
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				monteCarloFrame.setLayoutData(gData);
				}
			}

			resultView.setBounds(GUI.getButtomContentArea().getClientArea());

			this.dispose();
			return true;
		} else
			return false;
	}

	public boolean performCancel() {
		this.dispose();
		return true;
	}
}
