package estimation.sizeEstimate;

import gui.ParameterArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import dataManager.dataEntities.COCOMO;

public class COCOMOSize extends ParameterArea{

	// 两个种数据的PI估算变量
	private Button codeNew, codeReuse, codeMaintain;
	private Spinner textNewSize, textReuseSize,textReuseAT,textReuseDM,textReuseCM,textReuseIM,textReuseAA,textReuseSU,textReuseUNFM,textModifySize,textAddSize,textMaintSU,textMaintUNFM;
	private StackLayout inputStack;
	private Composite compNew, compReuse, compMaintain;
	private Composite dataArea;
	
	public COCOMOSize(Composite parent){
		super(parent);
		createSize(form.getBody());
	}
	private Composite createSize(Composite parent)
	{
		Composite comSize = toolkit.createComposite(parent);
		GridLayout layout = new GridLayout(4, false);
		comSize.setLayout(layout);
		
		//生成三个可选按钮
		createRadioButtons(comSize);
		//生成确定按钮
		Button ok = toolkit.createButton(comSize, "确定", SWT.PUSH);
		ok.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		ok.setEnabled(true);
		ok.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				COCOMOSizeResults results = new COCOMOSizeResults(COCOMOSize.this);
				results.show();
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		//生成参数输入区
		GridData gd = new GridData();
		gd.horizontalSpan = 4;
		dataArea = toolkit.createComposite(comSize);
		dataArea.setLayoutData(gd);
		inputStack = new StackLayout();
		dataArea.setLayout(inputStack);
		
		// 用户输入新开发代码的规模
		createCompNew();
		
		// 用户输入重用代码计算公式的参数
		createCompReuse();
		
		// 用户输入维护代码计算公式的参数
		createCompMaintain();
		
		return comSize;
	}
	private void createRadioButtons(Composite parent) {
		// 估算新开发的代码
		codeNew = toolkit.createButton(parent, "新开发代码", SWT.RADIO);
		codeNew.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				// 将text sizeText置顶
				inputStack.topControl = compNew;
				dataArea.layout();
			}
		});
		// 估算重用代码
		codeReuse = toolkit.createButton(parent, "重用的代码", SWT.RADIO);
		codeReuse.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				// 将composite compReuse置顶
				inputStack.topControl = compReuse;
				dataArea.layout();
			}
		});
		// 估算维护的代码
		codeMaintain = toolkit.createButton(parent, "维护的代码", SWT.RADIO);
		codeMaintain.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				// 将composite compMaintain置顶
				inputStack.topControl = compMaintain;
				dataArea.layout();
			}
		});
	}

	private void createCompMaintain() {
		compMaintain = toolkit.createComposite(dataArea);
		compMaintain.setLayout(new GridLayout(2,false));
		
		textAddSize = createDataField(compMaintain, "新增代码量(LOC)：", 1000, 0, Spinner.LIMIT, 0);
		textModifySize = createDataField(compMaintain, "修改代码量(LOC)：", 1000, 0, Spinner.LIMIT, 0);
		textMaintSU = createDataField(compMaintain, "代码可理解度(范围10-50，10表示代码很好理解)：", 10, 10, 50, 0);
		textMaintUNFM = createDataField(compMaintain, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：", 0, 0, 10, 1);
	}

	private void createCompReuse() {
		compReuse = toolkit.createComposite(dataArea);
		compReuse.setLayout(new GridLayout(2,false));
		
		textReuseSize = createDataField(compReuse, "新增和修改的代码量(LOC)：", 1000, 0, Spinner.LIMIT, 0);
		textReuseAT = createDataField(compReuse, "自动转换的代码比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseDM = createDataField(compReuse, "设计修改比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseCM = createDataField(compReuse, "代码修改比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseIM = createDataField(compReuse, "集成工作量比例(百分值，范围1-100)：", 0, 0, 100, 0);
		textReuseAA = createDataField(compReuse, "代码适用程度(范围0-8，0表示完全适用):", 0, 0, 8, 0);
		textReuseSU = createDataField(compReuse, "代码可理解度(范围10-50，10表示代码很好理解)：", 10, 10, 50, 0);
		textReuseUNFM = createDataField(compReuse, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：", 0, 0, 10, 1);
	}

	private void createCompNew() {
		compNew = toolkit.createComposite(dataArea);
		compNew.setLayout(new GridLayout(2,false));
		textNewSize = createDataField(compNew, "请输入新开发的代码量(LOC)：", 1000, 0, Spinner.LIMIT, 0);
	}

	private Spinner createDataField(Composite parent, String desc, int selection, int minimum, int maximum, int digits ){
		Label label = toolkit.createLabel(parent, desc);
		Spinner inputSpinner = new Spinner(parent, SWT.BORDER);
		inputSpinner.setMinimum(minimum);
		inputSpinner.setMaximum(maximum);
		inputSpinner.setSelection(selection);
		inputSpinner.setDigits(digits);
		inputSpinner.setSize(30, 20);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		inputSpinner.setLayoutData(gd);
		return inputSpinner;
	}

	public int getEstimatedSize() {
		
		if (codeNew.getSelection())
			return Integer.parseInt(textNewSize.getText());
		else if(codeReuse.getSelection())
		{
			Double SLOC =  Double.parseDouble(textReuseSize.getText());
			Double AT = Double.parseDouble(textReuseAT.getText());
			Double DM = Double.parseDouble(textReuseDM.getText());
			Double CM = Double.parseDouble(textReuseCM.getText());
			Double IM = Double.parseDouble(textReuseIM.getText());
			Double AA = Double.parseDouble(textReuseAA.getText());
			Double SU = Double.parseDouble(textReuseSU.getText());
			Double UNFM = Double.parseDouble(textReuseUNFM.getText());
			return COCOMO.getReuseSize(SLOC, AT, DM, CM, IM, AA, SU, UNFM);
		}
		else
		{
			Double addSLOC =  Double.parseDouble(textAddSize.getText());
			Double modifySLOC = Double.parseDouble(textModifySize.getText());
			Double SU = Double.parseDouble(textMaintSU.getText());
			Double UNFM = Double.parseDouble(textMaintUNFM.getText());
			return COCOMO.getMaintainSize(addSLOC, modifySLOC, SU, UNFM);
		}
			
	}
}

