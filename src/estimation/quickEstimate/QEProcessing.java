package estimation.quickEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import gui.tabs.NewResultTabAction;
import gui.tabs.TabContentArea;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.swt.widgets.Composite;
import data.database.dataEntities.NodeBasicInformation;
import data.database.dataEntities.QuickEstimationRecord;

import estimation.CSBSG;
import estimation.ISBSG;

public class QEProcessing extends NewResultTabAction {
	private QEInput quickEstimate;
	int projectSize;
	// 由公式计算得到的effort值
	Double formulaEffort;
	// 由历史数据得到的effort值
	Double historyEffort;
	Double meanProductivity;
	Double stanDevProductivity;

	public QEProcessing(QEInput estimate) {
		super(estimate.getNode());
		quickEstimate = estimate;
	}

	@Override
	protected Composite createContents(Composite parent) {
		HashMap<String, String> factors;
		// 根据条件搜索到的生产率数组
		ArrayList<Double> arrayPI;
		// for the statistic of median,mean and standard deviation
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// 快速估算数据处理
		if (quickEstimate.getDataType() == "csbsg") {
			// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
			factors = quickEstimate.getCSBSGFactors();
			projectSize = quickEstimate.getCSBSGSize();
			System.out.println("projectSize = " + projectSize);
			arrayPI = CSBSG.getProductivity(projectSize, factors);
			formulaEffort = CSBSG.getEqnEffort((double) projectSize, factors);
			for (double PI : arrayPI)
				stats.addValue(PI);
			historyEffort = projectSize / stats.getPercentile(50);
		} else {
			// 此处factors为指向quickEstimate.getFactors()的指针，factors的改变会影响
			factors = quickEstimate.getISBSGFactors();
			projectSize = quickEstimate.getISBSGSize();
			System.out.println("projectSize = " + projectSize);
			arrayPI = ISBSG.getPDR(projectSize, factors);
			formulaEffort = ISBSG.getEqnPDR(factors) * projectSize;
			for (double PI : arrayPI)
				stats.addValue(PI);
			historyEffort = stats.getPercentile(50) * projectSize;
		}

		if (arrayPI.size() > 1) {
			meanProductivity = stats.getMean();
			stanDevProductivity = stats.getStandardDeviation();
		} else {
			meanProductivity = 0.0;
			stanDevProductivity = 0.0;
			if (arrayPI.size() == 0)
				historyEffort = 0.0;
		}

		// 快速估算结果
		Composite resultView = new QEResults(parent, this);
		

		// 存储数据
		NodeBasicInformation.updateEstType(quickEstimate.getnodeID(), "quick");
		QuickEstimationRecord.saveQuickEstimation(quickEstimate.getnodeID(),
				quickEstimate.getDataType(), formulaEffort, historyEffort,
				meanProductivity, stanDevProductivity);
		return resultView;
	}

	public String getDataType(){
		return quickEstimate.getDataType();
	}

	@Override
	protected String getTabTitle() {
		return getNode().getName()+"快速估算结果";
	}

	@Override
	protected Class<? extends TabContentArea> pageClass() {
		return QEResults.class;
	}
}
