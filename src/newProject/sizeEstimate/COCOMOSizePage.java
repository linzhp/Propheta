package newProject.sizeEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import dataManager.COCOMO;

public class COCOMOSizePage extends WizardPage {
	public static final String PAGE_NAME = "COCOMOSize";

	private Button codeNew, codeReuse, codeMaintain;
	private Text textNewSize, textReuseSize,textReuseAT,textReuseDM,textReuseCM,textReuseIM,textReuseAA,textReuseSU,textReuseUNFM,textModifySize,textAddSize,textMaintSU,textMaintUNFM;
	private StackLayout inputStack;
	private Composite compNew, compReuse, compMaintain;
	private Composite dataArea;

	public COCOMOSizePage() {
		super(PAGE_NAME, "COCOMOSizePage", null);
		setDescription("请选择一种代码类型进行估算：");
	}

	public void createControl(Composite parent) {
		// 此方法能启动canFlipToNextPage()方法,判断next按钮是否可用
		setPageComplete(false);

		Composite topLevel = new Composite(parent, SWT.NONE);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.justify = true;
		topLevel.setLayout(layout);

		createRadioButtons(topLevel);

		dataArea = new Composite(topLevel, SWT.NONE);
		inputStack = new StackLayout();
		dataArea.setLayout(inputStack);
		
		// 用户输入新开发代码的规模
		createCompNew();
		
		// 用户输入重用代码计算公式的参数
		createCompReuse();
		
		// 用户输入维护代码计算公式的参数
		createCompMaintain();
		
		setControl(topLevel);
	}

	private void createRadioButtons(Composite topLevel) {
		// 估算新开发的代码
		codeNew = new Button(topLevel, SWT.RADIO);
		codeNew.setText("新开发代码");
		codeNew.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				// 将text sizeText置顶
				inputStack.topControl = compNew;
				dataArea.layout();
			}
		});
		// 估算重用代码
		codeReuse = new Button(topLevel, SWT.RADIO);
		codeReuse.setText("重用的代码");
		codeReuse.setBounds(10, 30, 200, 20);
		codeReuse.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				// 将composite compReuse置顶
				inputStack.topControl = compReuse;
				dataArea.layout();
			}
		});
		// 估算维护的代码
		codeMaintain = new Button(topLevel, SWT.RADIO);
		codeMaintain.setText("维护的代码");
		codeMaintain.setBounds(10, 50, 200, 20);
		codeMaintain.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
				// 将composite compMaintain置顶
				inputStack.topControl = compMaintain;
				dataArea.layout();
			}
		});
	}

	private void createCompMaintain() {
		compMaintain = new Composite(dataArea, SWT.NONE);
		compMaintain.setLayout(new GridLayout(2,false));
		
		textAddSize = createDataField(compMaintain, "新增代码量(LOC)：");
		textModifySize = createDataField(compMaintain, "修改代码量(LOC)：");
		textMaintSU = createDataField(compMaintain, "代码可理解度(范围10-50，10表示代码很好理解)：");
		textMaintUNFM = createDataField(compMaintain, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：");
	}

	private void createCompReuse() {
		compReuse = new Composite(dataArea, SWT.NONE);
		compReuse.setLayout(new GridLayout(2,false));
		
		textReuseSize = createDataField(compReuse, "新增和修改的代码量(LOC)：");
		textReuseAT = createDataField(compReuse, "自动转换的代码比例(百分值，范围1-100)：");
		textReuseDM = createDataField(compReuse, "设计修改比例(百分值，范围1-100)：");
		textReuseCM = createDataField(compReuse, "代码修改比例(百分值，范围1-100)：");
		textReuseIM = createDataField(compReuse, "集成工作量比例(百分值，范围1-100)：");
		textReuseAA = createDataField(compReuse, "代码适用程度(范围0-8，0表示完全适用)");
		textReuseSU = createDataField(compReuse, "代码可理解度(范围10-50，10表示代码很好理解)：");
		textReuseUNFM = createDataField(compReuse, "开发人员不熟悉度(范围0-1，0表示完全熟悉)：");
	}

	private void createCompNew() {
		compNew = new Composite(dataArea, SWT.NONE);
		compNew.setLayout(new GridLayout(2,false));
		textNewSize = createDataField(compNew, "请输入新开发的代码量(LOC)：");
	}

	private Text createDataField(Composite parent, String desc){
		Label label = new Label(parent, SWT.NONE);
		label.setText(desc);
		Text input = new Text(parent, SWT.BORDER);
		input.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		return input;
	}
	
	public boolean canFlipToNextPage() {
		
		try {
			getSize();
		} catch (NumberFormatException e) {
			return false;
		}
		return isPageComplete() && getNextPage() != null;
	}

	public int getSize() {
		
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
