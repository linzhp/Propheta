package gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {
	//设置XY类型图的显示风格
	private static void setXYChartFont(JFreeChart chart)
	{
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
	}
	
	//设置Category类型图的显示风格
	private static void setCategoryChartFont(JFreeChart chart)
	{
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
	}
	
	// 生成快速估算中，工作量的蒙特卡罗图
	public static JFreeChart createXYLineChart(String titile, String XAxis, String YAxis, IntervalXYDataset dataSet) {
		JFreeChart chart = ChartFactory.createXYLineChart(titile,
				XAxis, YAxis, dataSet, PlotOrientation.VERTICAL, false,
				false, false);
		setXYChartFont(chart);
		return chart;
	}
	// 生成详细估算中工作量的条形图
	public static JFreeChart createBarChart(String titile, String XAxis, String YAxis, CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(titile, // chart title
				XAxis, // domain axis label
				YAxis, // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				false, // tooltips
				false // URLs
				);
		setCategoryChartFont(chart);
		return chart;
	}

	// 生成详细估算中的进度的甘特图
	public static JFreeChart createGanttChart(String titile, String XAxis, String YAxis, 
			IntervalCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createGanttChart(titile, // chart title
				XAxis, // domain axis label
				YAxis, // range axis label
				dataset, // data
				false, // include legend
				false, // tooltips
				false // urls
				);
		setCategoryChartFont(chart);
		return chart;
	}

	//生成详细估算中的人员分布图
	public static JFreeChart createStepLineChart(String titile, String XAxis, String YAxis, CategoryDataset dataset) {
        CategoryItemRenderer renderer = new CategoryStepRenderer(true);
        CategoryAxis domainAxis = new CategoryAxis(XAxis);
        ValueAxis rangeAxis = new NumberAxis(YAxis);
        CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);
        JFreeChart chart = new JFreeChart(titile, plot);
        chart.removeLegend();
        
        setCategoryChartFont(chart);
		return chart;
    }
	

	// 生成快速估算中蒙特卡罗图的数据集
	public static IntervalXYDataset createQuickDataSet(String dataType,
			double size, double piE, double piD) {
		HistogramDataset histogramdataset = new HistogramDataset();
		// 此处为输入的1万个点。
		Random generator = new Random();
		final int NUM_SAMPLES = 5000000;
		double[] efforts = new double[NUM_SAMPLES];
		if (dataType.contains("csbsg"))
			for (int i = 0; i < efforts.length; i++) {
				// 工作量＝规模/生产率
				// efforts[i] = generator.nextGaussian();
				efforts[i] = size / ((generator.nextGaussian() + piE) * piD);
			}
		else
			for (int i = 0; i < efforts.length; i++) {
				// 工作量＝规模*生产率
				efforts[i] = size * ((generator.nextGaussian() + piE) * piD);
			}
		// 100表示bins（即条形柱的个数）
		histogramdataset.addSeries("", efforts, 100);
		return histogramdataset;
	}

	// 生成快速估算中工作量的散点图数据集
	public static XYDataset createQuickEffortXYDataset(ArrayList<Double[]> array) {
		XYSeries xyseries = new XYSeries("");
		for (int i = 0; i < array.size(); i++)
			// xyseries.add(规模, 工作量);
			xyseries.add(array.get(i)[0], array.get(i)[1]);

		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		xySeriesCollection.addSeries(xyseries);

		return xySeriesCollection;
	}

	
	// 生成详细估算中工作量条形图与人员分布图的数据集
	public static CategoryDataset createCategoryDataset(
			String[] categories, Double[] values) {
		String series = "value";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < categories.length; i++)
			dataset.addValue(values[i], series, categories[i]);
		return dataset;

	}

	// 生成详细估算中进度的甘特图数据集
	public static IntervalCategoryDataset createScheduleCategoryDataset(
			String[] tasks, Double[] scheduleTimes) {
		final TaskSeries s1 = new TaskSeries("Scheduled");
		// 设置项目初始日期：2010.1.1
		Date startDate = date(1, Calendar.JANUARY, 2010);
		Date finishDate = new Date();
		for (int i = 0; i < tasks.length; i++) {
			finishDate = getScheduleDate(startDate, scheduleTimes[i]);
			s1.add(new Task(tasks[i], new SimpleTimePeriod(startDate,
					finishDate)));
			startDate = finishDate;
		}
		final TaskSeriesCollection collection = new TaskSeriesCollection();
		collection.add(s1);
		return collection;
	}

	private static Date date(final int day, final int month, final int year) {

		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		final Date result = calendar.getTime();
		return result;

	}

	private static Date getScheduleDate(Date startDate, Double schedule) {
		Date scheduleDate = new Date();
		// schedule 以月为单位
		scheduleDate
				.setTime((long) (startDate.getTime() + (schedule * 30 * 86400000)));
		return scheduleDate;
	}

	

	// public Chart(String title) {
	// super(title);
	//
	// final String[] phasesTex = { "计划与需求", "产品设计", "编码", "集成与测试" };
	// Double[] d = { 1.2, 3.2, 9.2, 2.0 };
	// IntervalCategoryDataset dataset = createScheduleCategoryDataset(
	// phasesTex, d);
	// JFreeChart chart = createScheduleGanttChart(dataset);
	// ChartPanel chartPanel = new ChartPanel(chart, false);
	// chartPanel.setPreferredSize(new Dimension(500, 270));
	// setContentPane(chartPanel);
	// }
	//
	// public static void main(String[] args) {
	// Chart demo = new Chart("Gantt Chart Demo 1");
	// demo.pack();
	// RefineryUtilities.centerFrameOnScreen(demo);
	// demo.setVisible(true);
	// }

}