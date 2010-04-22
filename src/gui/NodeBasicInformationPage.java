package gui;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.EstimateNode;
import data.file.Languages;
import estimation.sizeEstimation.SizeEstimationWizard;
import gui.tabs.ParameterArea;

/**
 * 节点基本信息页面
 * @author Administrator
 *
 */
public class NodeBasicInformationPage extends ParameterArea{

	private class FieldChanged implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {	
			widgetSelected(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			setIsInformationChanged(true);
			saveButton.setEnabled(true);
		}
	}


	private Text texNodeName;
	private Spinner spnTeamSize,spnDuration,spnFP, spnEstSLOC, spnRealSLOC,spnRealEffort;
	private Combo cmbBusinessArea,cmbDevelopType,cmbDevelopPlatform,cmbLanguageType,cmbLanguage;
	private Composite SLOCComposite, buttonComposite;
	private Button setSLOCButton,saveButton;
	
	/**
	 * 构造器
	 * @param nodeID 关联的节点ID
	 */
	public NodeBasicInformationPage(EstimateNode node, Composite parent){
		super(parent,node);
		this.setFormText("节点基本信息");
		
		//构造页面控件
		createContents(this.form.getBody());
		
		//读取存储的节点信息并显示
				
		bindNodeBaiscInformation(node);
	}
	
	
	
	/**
	 * 初始化界面控件
	 * @param parent
	 */
	private void createContents(Composite parent){
		GridLayout layout=new GridLayout(4,false);
		parent.setLayout(layout);
		
		toolkit.createLabel(parent, "节点名称:", SWT.NONE);		
		texNodeName=toolkit.createText(parent, "", SWT.BORDER);
		texNodeName.setEditable(false);
		GridData gd=new GridData();
		gd.horizontalSpan=3;
		gd.horizontalAlignment=SWT.FILL;
		texNodeName.setLayoutData(gd);
		
		toolkit.createLabel(parent, "团队规模:", SWT.NONE);	
		spnTeamSize=createSpinner(parent,5,0);
			
		
		toolkit.createLabel(parent, "项目周期:", SWT.NONE);		
		spnDuration=createSpinner(parent,180,0);
		
		toolkit.createLabel(parent, "代码行数:", SWT.NONE);		
		
		
		
		//代码行输入面板
		SLOCComposite=toolkit.createComposite(parent,SWT.NONE);
		gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		SLOCComposite.setLayoutData(gd);
		SLOCComposite.setLayout(new GridLayout(2,false));
		
		spnEstSLOC = createSpinner(SLOCComposite, 0, 0);
		gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		spnEstSLOC.setLayoutData(gd);
		
		setSLOCButton=toolkit.createButton(SLOCComposite, "估算", SWT.NONE);
		setSLOCButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setIsInformationChanged(true);
				saveButton.setEnabled(true);
				
				//设置SLOC wizard
				SizeEstimationWizard slocWizard = new SizeEstimationWizard();
				WizardDialog wdialog=new WizardDialog(Display.getCurrent().getActiveShell(),slocWizard);
			    wdialog.open();
			    spnEstSLOC.setSelection(slocWizard.getSize());
			}});
	
		toolkit.createLabel(parent, "功能点数目:", SWT.NONE);			
		spnFP=createSpinner(parent,200,0);
		
		toolkit.createLabel(parent, "业务领域:", SWT.NONE);	
		String[] texts = new String[]{ "电信", "金融", "流通", "保险", "交通", "媒体", "卫生", "制造",
				"政府", "能源" };
		String[] values =new String[]{ "Telecom", "Finance", "Retail", "General","Transport", "Media",
				"HealthCare", "Manufacturing","PublicAdmin", "Energy" };
		cmbBusinessArea=createCombo(parent,texts,values,0);
		
		toolkit.createLabel(parent, "开发类型:", SWT.NONE);
		texts = new String[]{"新开发", "二次开发", "优化", "其它" };
		values =new String[]{"NewDevelopment", "ReDevelopment", "Enhancement", "Other"};
		cmbDevelopType=createCombo(parent,texts,values,0);
		
		toolkit.createLabel(parent, "开发平台:", SWT.NONE);
		texts = new String[]{"大型机", "中型机", "个人计算机", "混合"};
		values =new String[]{"MF", "MR", "PC", "Multi"};
		cmbDevelopPlatform=createCombo(parent,texts,values,0);
		
		toolkit.createLabel(parent, "语言类型:", SWT.NONE);	
		texts = new String[]{"第二代语言", "第三代语言", "第四代语言", "应用代"};
		values =new String[]{"2GL", "3GL", "4GL", "ApG"};
		cmbLanguageType=createCombo(parent,texts,values,0);
		
		toolkit.createLabel(parent, "开发语言:", SWT.NONE);
		texts = values = Languages.getLanguages();
		cmbLanguage=createCombo(parent,texts,values,0);
		
		toolkit.createLabel(parent, "实际代码行数：");
		spnRealSLOC = createSpinner(parent, (Integer)node.get("realSLOC"),0);
		
		toolkit.createLabel(parent, "实际工作量：");
		int selection = (int) (((Double)node.get("realEffort"))*100);
		spnRealEffort = createSpinner(parent, selection,2);
		//操作按钮面板
		buttonComposite=new Composite(parent, SWT.NONE);
		gd=new GridData();
		gd.horizontalSpan=3;
		buttonComposite.setLayoutData(gd);
		buttonComposite.setLayout(new FillLayout());
		
		//保存按钮
		saveButton=new Button(buttonComposite,SWT.PUSH);
		saveButton.setText("保存");
		saveButton.setEnabled(false);
		saveButton.addSelectionListener(new SelectionListener(){
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {			
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				saveNodeBasicInformation();
				setIsInformationChanged(false);
				saveButton.setEnabled(false);
			}
		});
		
	}
	
	
	/**
	 * 构建Spinner控件
	 */
	private  Spinner createSpinner(Composite parent, int selectedValue, int decimal){
		Spinner spn=new Spinner(parent, SWT.BORDER);
		spn.setMaximum(Spinner.LIMIT);
		spn.setSelection(selectedValue);
		spn.setDigits(decimal);
		GridData gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		spn.setLayoutData(gd);
		spn.addSelectionListener(new FieldChanged());
		
		return spn;
	}
	
	
	/**
	 * 构建Combo控件
	 * @param parent
	 * @param texts
	 * @param values
	 * @param selectedIndex
	 * @return
	 */
	private Combo createCombo(Composite parent, String[] texts, String[] values, int selectedIndex){
		Combo cb=new Combo(parent, SWT.READ_ONLY);
		cb.setItems(texts);
		for(int i=0;i<texts.length;i++){
			cb.setData(texts[i], values[i]);
		}
		//cb.select(selectedIndex);
		
		GridData gd=new GridData();
		gd.horizontalAlignment=SWT.FILL;
		cb.setLayoutData(gd);
		
		cb.addSelectionListener(new FieldChanged());
		
		return cb;
	}
	
	
	/**
	 * 获取节点信息
	 * @return
	 */
	public void setAttributes(){
		node.set("teamSize",this.spnTeamSize.getSelection());
		node.set("duration",this.spnDuration.getSelection());
		node.set("functionPoints",this.spnFP.getSelection());				
		node.set("businessArea",(String)this.cmbBusinessArea.getData(this.cmbBusinessArea.getText()));
		node.set("developmentType",(String)this.cmbDevelopType.getData(this.cmbDevelopType.getText()));
		node.set("developmentPlatform",(String)this.cmbDevelopPlatform.getData(this.cmbDevelopPlatform.getText()));
		node.set("languageType",this.cmbLanguageType.getData(this.cmbLanguageType.getText()));
		node.set("language",this.cmbLanguage.getData(this.cmbLanguage.getText()));
		node.set("estSLOC",this.spnEstSLOC.getSelection());
		
		node.set("realSLOC", Integer.valueOf(spnRealSLOC.getText()));
		node.set("realEffort", Double.valueOf(spnRealEffort.getText()));
	}
	
	
	/**
	 * 显示节点信息
	 * @param nbi
	 */
	public void bindNodeBaiscInformation(EstimateNode nbi){
		if(nbi!=null){
			this.texNodeName.setText((String)nbi.get("name"));
			this.spnTeamSize.setSelection(((Integer)nbi.get("teamSize")));
			this.spnDuration.setSelection((Integer)nbi.get("duration"));
			this.spnEstSLOC.setSelection((Integer)nbi.get("estSLOC"));			
			this.spnFP.setSelection((Integer)nbi.get("functionPoints"));
			this.initCombo(this.cmbBusinessArea, (String)nbi.get("businessArea"));
			this.initCombo(this.cmbDevelopType, (String)nbi.get("developmentType"));
			this.initCombo(this.cmbDevelopPlatform, (String)nbi.get("developmentPlatform"));
			this.initCombo(this.cmbLanguageType, (String)nbi.get("languageType"));
			this.initCombo(this.cmbLanguage, (String)nbi.get("language"));
		}
	}	
	
	private void initCombo(Combo cmb, String value){
		if(value!=null){
			int itemNum=cmb.getItemCount();
			for(int i=0;i<itemNum;i++){
				if(value.equals(cmb.getData(cmb.getItem(i)))){
					cmb.select(i);
					break;
				}
			}
		}
	}
	
	
	/**
	 * 保存节点基本信息
	 */
	public void saveNodeBasicInformation(){
		setAttributes();
		NodeBasicInfoAccess nbi_access=new NodeBasicInfoAccess();
		nbi_access.update(node);
	}
}
