package newProject.quickEstimate;

import java.awt.Font;
import java.io.IOException;
import java.util.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

public class HistogramChart {
	
	public static IntervalXYDataset createDataset(double size, double piE, double piD) {
		HistogramDataset histogramdataset = new HistogramDataset();
		// 此处为输入的1万个点。
		Random generator  = new Random();
		double[] ad = new double[10000];
		for(int i = 0; i < 10000; i++)
		{
			//工作量＝规模/生产率
			ad[i] = size/((generator.nextGaussian()+ piE) * piD );
		}
		//20表示bins（即条形柱的个数）
		histogramdataset.addSeries("", ad, 100);
		return histogramdataset;
	}

	public static JFreeChart createChart(IntervalXYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createHistogram("工作量正态分布图",
				"工时(Hour)", "频率", intervalxydataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		//设置了字体，才能显示中文
        Font font = new Font("黑体", SWT.Paint, 14);
        // 图片标题
        TextTitle title = jfreechart.getTitle();         
        title.setFont(font); 
        // 图形的绘制结构对象 
        XYPlot xyplot = jfreechart.getXYPlot();
		 // X 轴
        ValueAxis domainAxis = xyplot.getDomainAxis();
		 domainAxis.setLabelFont(font);// 轴标题
		 domainAxis.setTickLabelFont(font);// 轴数值
		 // Y 轴
		 ValueAxis rangeAxis = xyplot.getRangeAxis();
		 rangeAxis.setLabelFont(font);// 轴标题
		 rangeAxis.setTickLabelFont(font);// 轴数值
		
		xyplot.setForegroundAlpha(0.85F);
		return jfreechart;
	}

	/*public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) throws IOException {
		JFreeChart chart = createChart(createDataset(20000,8.9,1.5));
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(600, 300);
		shell.setLayout(new FillLayout());
		shell.setText("Test for jfreechart running with SWT");
		ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart, true);
		frame.setDisplayToolTips(false);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}*/
}
