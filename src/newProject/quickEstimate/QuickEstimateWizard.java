package newProject.quickEstimate;

import gui.GUI;
import dataManager.CSBSG;
import entity.EstimateNode;

import java.util.ArrayList;
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
	private static Composite resultView = null;

	public void addPages() {
		addPage(new SizePage());
		addPage(new PIDataBasePage());
		addPage(new PIFactorPage());
	}

	private SizePage getSizePage() {
		return (SizePage) getPage(SizePage.PAGE_NAME);
	}

	private PIFactorPage getFactorPage() {
		return (PIFactorPage) getPage(PIFactorPage.PAGE_NAME);
	}

	private double getSize() {
		return (double) getSizePage().getSize();
	}

	private String getFactor() {
		return getFactorPage().getFactor();
	}

	private String getFactorValue() {
		return getFactorPage().getFactorValue();
	}

	public boolean canFinish() {
		if (getContainer().getCurrentPage() instanceof PIFactorPage)
			return true;
		else
			return false;
	}

	public boolean performFinish() {
		if (this.canFinish()) {
			
			EstimateNode en=new EstimateNode("未命名估算项目");
			GUI.getTreeArea().addEstimateProjet(en);
			
			
			CSBSG csbsg = new CSBSG();
			ArrayList<Double> arrayPI = csbsg.getProductivity(getSize(),
					getFactor(), getFactorValue());
			// 此处0.2为规模的误差范围，可调，arraySizeEffort:projectSize,effort
			ArrayList<Double[]> arraySizeEffort = csbsg.getEffort(getSize(), 0.2);
			//for the statistic of median,mean and standard deviation 
			DescriptiveStatistics stats = new DescriptiveStatistics();
			
			//在GUI.getContentArea()上生成显示结果的static composite：resultView
			if (resultView != null)
				resultView.dispose();
			GUI.getContentArea().disposeCurrentPage();
			resultView = new Composite(GUI.getContentArea(), SWT.NONE);
			GridLayout layout = new GridLayout(1, false);
			layout.verticalSpacing = 10;
			resultView.setLayout(layout);

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

			if (arraySizeEffort.size() == 0) {
				Label noEffortResult = new Label(resultView, SWT.SINGLE);
				noEffortResult.setText("没有搜索到任何相关历史数据，无法显示“规模相近的历史项目的工作量分布”");
			} else {
				// 显示规模相近的历史项目的工作量分布
				stats.clear();
				for (int i = 0; i < arraySizeEffort.size(); i++) {
					System.out.println("size:" + arraySizeEffort.get(i)[0]
							+ " effort:" + arraySizeEffort.get(i)[1]);
					stats.addValue(arraySizeEffort.get(i)[1]);
				}
				JFreeChart effortChart = Chart.createEffortChart(Chart
						.createEffortXYDataset(arraySizeEffort), stats
						.getMean(), stats.getPercentile(50));
				ChartComposite effortFrame = new ChartComposite(resultView,
						SWT.BORDER, effortChart, true);

				// 页面布局
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				effortFrame.setLayoutData(gData);
			}

			resultView.setBounds(GUI.getContentArea().getClientArea());

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
