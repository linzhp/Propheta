package newProject.detailedEstimate;

import java.util.ArrayList;
import java.util.HashMap;

import newProject.quickEstimate.Chart;
import newProject.quickEstimate.PIFactorPage;
import newProject.quickEstimate.SizePage;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import dataManager.COCOMO;
import dataManager.CSBSG;
import entity.EstimateNode;
import gui.GUI;

public class DetailedEstimateWizard extends Wizard {
	private static Composite resultView = null;
	
	public void addPages() {
		addPage(new COCOMOPage());
	}

	private COCOMOPage getCOCOMOPage()
	{
		return (COCOMOPage) getPage(COCOMOPage.PAGE_NAME);
	}
	private int getSize() {
		return getCOCOMOPage().getSize();
	}

	private HashMap<String, String> getFactorsSF() {
		return getCOCOMOPage().getFactorsSF();
	}
	
	private HashMap<String, String> getFactorsEM() {
		return getCOCOMOPage().getFactorsEM();
	}
	
	public boolean canFinish() {
		if (getContainer().getCurrentPage() instanceof COCOMOPage
				&& getCOCOMOPage().canFinish())
			return true;	
		else
			return false;
	}
	public boolean performFinish() {
		if (this.canFinish()) {
			
			EstimateNode en=new EstimateNode("未命名估算项目");
			GUI.getTreeArea().addEstimateProjet(en);
			
			HashMap<String,String> factorsSF = getFactorsSF();
			HashMap<String,String> factorsEM = getFactorsEM();
			Double size = (double)getSize();
			Double[] effort = COCOMO.getModuleEffortTime(size, factorsSF, factorsEM);
			
			//在GUI.getContentArea()上生成显示结果的static composite：resultView
			if (resultView != null)
				resultView.dispose();
			GUI.getContentArea().disposeCurrentPage();
			resultView = new Composite(GUI.getContentArea(), SWT.NONE);
			GridLayout layout = new GridLayout(1, false);
			layout.verticalSpacing = 10;
			resultView.setLayout(layout);

			//显示同论文总结出的CSBSG公式，计算得到的结果
			Label result = new Label(resultView, SWT.NONE);
			result.setText("根据公式计算出   PM为：" + effort[0]+ "(人/月)\n"+
					"\t\t TDEV为：" + effort[1]+"(月)");
			
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
