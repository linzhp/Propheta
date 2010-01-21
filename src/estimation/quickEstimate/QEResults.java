package estimation.quickEstimate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import gui.Chart;
import gui.tabs.TabContentArea;

public class QEResults extends TabContentArea {

	public QEResults(Composite parent, QEProcessing processing) {
		super(parent, processing.getNode());
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 10;
		setLayout(layout);
		
		Label PI = new Label(this, SWT.NONE);
		PI.setText("根据公式计算出的工作量为：" + processing.formulaEffort.intValue() + " 小时");
		if (processing.historyEffort == 0) {
			Label noResult = new Label(this, SWT.SINGLE);
			noResult.setText("没有搜索到任何相关历史数据，无法显示“历史工作量数值”与“工作量的蒙特卡罗图”");
		} else {
			System.out.println("PI median: " + processing.historyEffort);
			Label PImedian = new Label(this, SWT.NONE);
			PImedian.setText("根据历史项目数据的生产率中位数值计算出的工作量："
					+ processing.historyEffort.intValue() + " 小时");
		
			// 显示工作量的蒙特卡罗图
			if (processing.meanProductivity == 0) {
				Label noResult = new Label(this, SWT.SINGLE);
				noResult.setText("没有搜索到足够的相关历史数据，无法显示工作量的蒙特卡罗图”");
			} else {
				JFreeChart monteCarloChart = Chart.createMonteCarloChart(Chart
						.createQuickDataSet(processing.getDataType(),
								processing.projectSize, processing.meanProductivity,
								processing.stanDevProductivity));
				Composite monteCarloFrame = new ChartComposite(this,
						SWT.BORDER, monteCarloChart, true);
				// 页面布局
				GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
				monteCarloFrame.setLayoutData(gData);
			}
		}

	}

}
