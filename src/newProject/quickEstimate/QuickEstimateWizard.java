package newProject.quickEstimate;

import java.util.ArrayList;

import newProject.PIEstimate.FactorPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import dataManager.CSBSG;
import dataManager.Statistic;

import run.Application;

public class QuickEstimateWizard extends Wizard {
	private static Composite resultView = null;

	public void addPages() {
		addPage(new SizePage());
		addPage(new PIPage());
		addPage(new FactorPage());
	}

	private SizePage getSizePage() {
		return (SizePage) getPage(SizePage.PAGE_NAME);
	}

	private FactorPage getFactorPage() {
		return (FactorPage) getPage(FactorPage.PAGE_NAME);
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
		if (getContainer().getCurrentPage() instanceof FactorPage)
			return true;
		else
			return false;
	}

	public boolean performFinish() {
		if (this.canFinish()) {
			CSBSG csbsg = new CSBSG();
			ArrayList<Double> arrayPI = csbsg.getProductivity(getSize(),
					getFactor(), getFactorValue());
			ArrayList<Double[]> arrayEffort = csbsg.getEffort(getSize(), 0.2);
			Composite displayArea = Application.getInstance().getMainContent();

			if(resultView != null)
				resultView.dispose();
			resultView = new Composite(displayArea, SWT.NONE);
			GridLayout layout = new GridLayout(1, false);
			layout.verticalSpacing = 10;
			resultView.setLayout(layout);

			if (arrayPI.size() != 0) {
				// 显示生产率的中位数
				Double median = Statistic.getMedian(arrayPI);
				Label PImedian = new Label(resultView, SWT.NONE);
				PImedian.setText("根据生产率中位数值计算出的工作量：" + getSize()/median);

				// 显示工作量的蒙特卡罗图
				Double mean = Statistic.getMean(arrayPI);
				Double standardDeviation = Statistic.getStandardDeviation(mean,
						arrayPI);
				JFreeChart monteCarloChart = LineChart
						.createMonteCarloChart(LineChart
								.createMonteCarloDataSet(getSize(), mean,
										standardDeviation));
				Composite monteCarloFrame = new ChartComposite(resultView,
						SWT.BORDER, monteCarloChart, true);
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				monteCarloFrame.setLayoutData(gData);
			} else {
				Label noResult = new Label(resultView, SWT.SINGLE);
				noResult.setText("没有搜索到任何相关历史数据，无法显示“生产率中位数值”与“工作量的蒙特卡罗图”");

			}
			// 显示规模相近的历史项目的工作量分布
			JFreeChart effortChart = LineChart.createEffortChart(LineChart
					.createEffortXYDataset(arrayEffort));
			ChartComposite effortFrame = new ChartComposite(resultView, SWT.BORDER,
					effortChart, true);
			GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
			effortFrame.setLayoutData(gData);
			resultView.setBounds(displayArea.getClientArea());
			
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
