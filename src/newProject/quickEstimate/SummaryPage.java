package newProject.quickEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

public class SummaryPage extends WizardPage {
	public static final String PAGE_NAME = "Summary";
	private double size = 20000, piE =0, piD = 1;
	private JFreeChart jfreechart = null;
	private ChartComposite frame = null;
	public SummaryPage() {
		super(PAGE_NAME, "Summary Page", null);
	}

	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new FillLayout());
		jfreechart = HistogramChart.createChart(HistogramChart.createDataset(size, piE, piD));
		frame = new ChartComposite(topLevel, SWT.NONE, jfreechart, true);
		frame.pack();

		setControl(topLevel);
		setPageComplete(true);
	}
	
	public void updateData(double p_size, double p_piE, double p_piD) {
		size = p_size;
		piE = p_piE;
		piD = p_piD;
		jfreechart = HistogramChart.createChart(HistogramChart.createDataset(size, piE, piD));
		frame.setChart(jfreechart);
	}

}
