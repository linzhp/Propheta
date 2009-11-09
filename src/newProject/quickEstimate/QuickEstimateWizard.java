package newProject.quickEstimate;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import run.Application;

public class QuickEstimateWizard extends Wizard {
	private JFreeChart jfreechart = null;
	private static ChartComposite frame = null;
	public void addPages() {
		addPage(new SizePage());
		addPage(new PIPage());
	}

	private SizePage getSizePage() {
		return (SizePage) getPage(SizePage.PAGE_NAME);
	}

	private PIPage getPIPage() {
		return (PIPage) getPage(PIPage.PAGE_NAME);
	}

	private double getSize() {
		return (double) getSizePage().getSize();
	}

	private double getPIE() {
		return getPIPage().getPIE();
	}

	private double getPID() {
		return getPIPage().getPID();
	}

	public boolean performFinish() {
		if (this.canFinish()) {
			if(frame != null)
				frame.dispose();
			jfreechart = HistogramChart.createChart(HistogramChart.createDataset(getSize(), getPIE(), getPID()));
			Composite displayArea = Application.getInstance().getMainContent();

			frame = new ChartComposite(displayArea,SWT.NONE, jfreechart, true);
			Rectangle size = displayArea.getClientArea();
			frame.setSize(size.width, size.height);
			
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
