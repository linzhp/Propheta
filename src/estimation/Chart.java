package estimation;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;

public class Chart{
	public static JFreeChart createMonteCarloChart(IntervalXYDataset dataSet) {
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

	public static IntervalXYDataset createQuickDataSet(String dataType, double size, double piE, double piD) {
		HistogramDataset histogramdataset = new HistogramDataset();
		// 此处为输入的1万个点。
		Random generator  = new Random();
		final int NUM_SAMPLES = 5000000;
		double[] efforts = new double[NUM_SAMPLES];
		if(dataType == "csbsg")
			for(int i=0; i<efforts.length; i++)
			{
				//工作量＝规模/生产率
				efforts[i] = size/((generator.nextGaussian() + piE) * piD);
			}
		else
			for(int i=0; i<efforts.length; i++)
			{
				//工作量＝规模/生产率
				efforts[i] = size * ((generator.nextGaussian() + piE) * piD);
			}
		//100表示bins（即条形柱的个数）
		histogramdataset.addSeries("", efforts, 100);
		return histogramdataset;
	}
	
	public static JFreeChart createQuickEffortScatterChart(XYDataset dataSet, Double mean, Double median) {
		JFreeChart chart = ChartFactory.createScatterPlot("规模相近的历史项目的工作量分布",
				"规模(KLOC)", "工作量(Hour)", dataSet, PlotOrientation.VERTICAL,
				false, false, false);
		// 设置了字体，才能显示中文
		Font font = new Font("黑体", SWT.Paint, 14);
		// 图片标题
		TextTitle title = chart.getTitle();
		title.setFont(font);
		// 图片副标题
		TextTitle subTitle = new TextTitle("工作量平均值：" + mean + "  中位数：" + median );  
		subTitle.setFont(new Font("宋体", SWT.Paint, 12));
		subTitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		chart.addSubtitle(subTitle);
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

	public static XYDataset createQuickEffortXYDataset(ArrayList<Double[]> array) {
		XYSeries xyseries = new XYSeries("");
		for (int i = 0; i < array.size(); i++)
			//xyseries.add(规模, 工作量);
			xyseries.add(array.get(i)[0], array.get(i)[1]);

		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		xySeriesCollection.addSeries(xyseries);

		return xySeriesCollection;
	}
	
	public static JFreeChart createEffortBarChart(String title, String domainAxis, CategoryDataset dataset) {   
        JFreeChart chart = ChartFactory.createBarChart(   
        	title,       // chart title   
        	domainAxis,               // domain axis label   
            "工作量(人.月)",                  // range axis label   
            dataset,                  // data   
            PlotOrientation.VERTICAL, // orientation   
            false,                     // include legend   
            false,                     // tooltips   
            false                     // URLs  
        );   
   
     // 设置了字体，才能显示中文
		Font font = new Font("黑体", SWT.Paint, 12);
		// 图片标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", SWT.Paint, 14));
		// 图形的绘制结构对象
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		// X 轴
		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		categoryAxis.setMaximumCategoryLabelLines(2);
		categoryAxis.setLabelFont(font);// 轴标题
		categoryAxis.setTickLabelFont(font);// 轴数值
		// Y 轴
		ValueAxis valueAxis = categoryPlot.getRangeAxis();
		valueAxis.setLabelFont(font);// 轴标题
		valueAxis.setTickLabelFont(font);// 轴数值

		categoryPlot.setForegroundAlpha(0.85F);
		return chart;
    }   
	public static CategoryDataset createEffortCategoryDataset(
			String[] categories, Double[] efforts) {
		String series = "efforts";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < categories.length; i++)
			dataset.addValue(efforts[i], series, categories[i]);
		return dataset;

	}
	
//	public Chart(String title) {   
//		super(title);   
//		String[] s = {"first", "second"};
//		Double[] d = {1.2,3.2};
//        CategoryDataset dataset = createEffortCategoryDataset(s, d);   
//        JFreeChart chart = createEffortBarChart(dataset);   
//        ChartPanel chartPanel = new ChartPanel(chart, false);   
//        chartPanel.setPreferredSize(new Dimension(500, 270));   
//        setContentPane(chartPanel);   
//    }   
//	
//	public static void main(String[] args) {   
//		Chart demo = new Chart("Bar Chart Demo 1");   
//        demo.pack();   
//        RefineryUtilities.centerFrameOnScreen(demo);   
//        demo.setVisible(true);   
//    }   

}