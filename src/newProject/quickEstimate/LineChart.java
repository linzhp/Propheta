package newProject.quickEstimate;

import java.awt.Font;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class LineChart {

	public static JFreeChart createChart(IntervalXYDataset dataSet) {
		JFreeChart chart = ChartFactory.createXYLineChart("工作量蒙特卡罗模拟图",
				"工时(Hour)", "", dataSet, PlotOrientation.VERTICAL,
				false, false, false);

		// 设置了字体，才能显示中文
		Font font = new Font("黑体", SWT.Paint, 14);
		// 图片标题
		TextTitle title = chart.getTitle();
		title.setFont(font);
		// 图形的绘制结构对象
		XYPlot xyplot = chart.getXYPlot();
		// X 轴
		ValueAxis domainAxis = xyplot.getDomainAxis();
		domainAxis.setLabelFont(font);// 轴标题
		domainAxis.setTickLabelFont(font);// 轴数值
		// Y 轴
		ValueAxis rangeAxis = xyplot.getRangeAxis();
		rangeAxis.setLabelFont(font);// 轴标题
		rangeAxis.setTickLabelFont(font);// 轴数值

		xyplot.setForegroundAlpha(0.85F);
		return chart;
	}

	public static IntervalXYDataset createDataSet(double size, double piE, double piD) {
		HistogramDataset histogramdataset = new HistogramDataset();
		// 此处为输入的1万个点。
		Random generator  = new Random();
		final int NUM_SAMPLES = 5000000;
		double[] ad = new double[NUM_SAMPLES];
		for(int i = 0; i < ad.length; i++)
		{
			//工作量＝规模/生产率
			ad[i] = size/((generator.nextGaussian()+ piE) * piD );
		}
		//20表示bins（即条形柱的个数）
		histogramdataset.addSeries("", ad, 100);
		return histogramdataset;
	}
}