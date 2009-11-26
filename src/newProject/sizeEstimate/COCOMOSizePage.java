package newProject.sizeEstimate;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

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

		// 估算新开发的代码
		codeNew = new Button(topLevel, SWT.RADIO);
		codeNew.setText("新开发代码");
		codeNew.setBounds(10, 10, 200, 20);
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

		dataArea = new Composite(topLevel, SWT.NONE);
		inputStack = new StackLayout();
		dataArea.setLayout(inputStack);
		dataArea.setBounds(30, 80, 400, 200);
		
		// 用户手动输入新开发代码的规模
		compNew = new Composite(dataArea, SWT.NONE);
		compNew.setLayout(new GridLayout(2,false));
		Label labNewDes = new Label(compNew, SWT.NONE);
		labNewDes.setText("请输入新开发的代码量(LOC)：");
		textNewSize = new Text(compNew, SWT.BORDER);
		textNewSize.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textNewSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		// 用户输入重用代码计算公式的参数
		compReuse = new Composite(dataArea, SWT.NONE);
		compReuse.setLayout(new GridLayout(2,false));
		
		Label labReuseSize = new Label(compReuse, SWT.NONE);
		labReuseSize.setText("新增和修改的代码量(LOC)：");
		textReuseSize = new Text(compReuse, SWT.BORDER);
		textReuseSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
	
		Label labReuseAT = new Label(compReuse, SWT.CENTER);
		labReuseAT.setText("自动转换的代码比例(百分值，范围1-100)：");
		textReuseAT = new Text(compReuse, SWT.BORDER);
		textReuseAT.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseDM = new Label(compReuse, SWT.CENTER);
		labReuseDM.setText("设计修改比例(百分值，范围1-100)：");
		textReuseDM = new Text(compReuse, SWT.BORDER);
		textReuseDM.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseCM = new Label(compReuse, SWT.CENTER);
		labReuseCM.setText("代码修改比例(百分值，范围1-100)：");
		textReuseCM = new Text(compReuse, SWT.BORDER);
		textReuseCM.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseIM = new Label(compReuse, SWT.CENTER);
		labReuseIM.setText("集成工作量比例(百分值，范围1-100)：");
		textReuseIM = new Text(compReuse, SWT.BORDER);
		textReuseIM.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseAA = new Label(compReuse, SWT.CENTER);
		labReuseAA.setText("代码适用程度(范围0-8，0表示完全适用)");
		textReuseAA = new Text(compReuse, SWT.BORDER);
		textReuseAA.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseSU = new Label(compReuse, SWT.CENTER);
		labReuseSU.setText("代码可理解度(范围10-50，10表示代码很好理解)：");
		textReuseSU = new Text(compReuse, SWT.BORDER);
		textReuseSU.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labReuseUNFM = new Label(compReuse, SWT.CENTER);
		labReuseUNFM.setText("开发人员不熟悉度(范围0-1，0表示完全熟悉)：");
		textReuseUNFM = new Text(compReuse, SWT.BORDER);
		textReuseUNFM.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		// 用户输入维护代码计算公式的参数
		compMaintain = new Composite(dataArea, SWT.NONE);
		compMaintain.setLayout(new GridLayout(2,false));
		
		Label labAddSize = new Label(compMaintain, SWT.NONE);
		labAddSize.setText("新增代码量(LOC)：");
		textAddSize = new Text(compMaintain, SWT.BORDER);
		textAddSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
	
		Label labModifySize = new Label(compMaintain, SWT.CENTER);
		labModifySize.setText("修改代码量(LOC)：");
		textModifySize = new Text(compMaintain, SWT.BORDER);
		textModifySize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labMaintSU = new Label(compMaintain, SWT.CENTER);
		labMaintSU.setText("代码可理解度(范围10-50，10表示代码很好理解)：");
		textMaintSU = new Text(compMaintain, SWT.BORDER);
		textMaintSU.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		Label labMaintUNFM = new Label(compMaintain, SWT.CENTER);
		labMaintUNFM.setText("开发人员不熟悉度(范围0-1，0表示完全熟悉)：");
		textMaintUNFM = new Text(compMaintain, SWT.BORDER);
		textMaintUNFM.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(true);
			}
		});
		
		setControl(topLevel);
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
