package calibration;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.math.stat.regression.SimpleRegression;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import data.database.dataAccess.BusinessAreaAccess;
import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataAccess.ConstantsAccess;
import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.database.dataEntities.Entity;
import data.file.COCOMOProperties;
import estimation.COCOMO;

import gui.tabs.ParameterArea;
import gui.widgets.ParameterCombo;

public class CalibInput extends ParameterArea {
	private class SaveAction extends Action{
		public SaveAction(){
			super("保存");
		}
		
		@Override
		public void run(){
			try {
				constants.set("A", Double.valueOf(aText.getText()));
				constants.set("B", Double.valueOf(bText.getText()));
				constants.set("集成因子", Double.valueOf(itgText.getText()));
			} catch (NumberFormatException e) {
				msg.setMessage("数字格式错误，请重新输入");
				msg.open();
			} catch (SQLException e) {
				msg.setMessage(e.getMessage());
				msg.open();
			}
		}
	}
	private class CalibAction extends Action {
		public CalibAction(){
			super("校准");
		}
		
		@Override
		public void run() {
			TableItem[] items = table.getItems();
			SimpleRegression regression = new SimpleRegression();
			for(TableItem it:items){
				if(!it.getChecked())
					continue;
				
				double lnSize = Math.log(Double.valueOf(it.getText(1))/1000);
				double lnEffort = Math.log(Double.valueOf(it.getText(2)));
				double sumSF = Double.valueOf(it.getText(3));
				double productEM =Double.valueOf(it.getText(4));
				regression.addData(lnSize, lnEffort-0.01*sumSF*lnSize-Math.log(productEM));
				
			}
			DecimalFormat df = new DecimalFormat("0.00");
			double intercept = regression.getIntercept();
			double slope = regression.getSlope();
			if(intercept != intercept+0 || slope != slope+0)
			{
				msg.setMessage("无法进行校准，A和B值将被重置为默认值");
				msg.open();
				resetAction.run();
			}else{
				try {
					double a = Math.exp(intercept);
					String aString = df.format(a);
					constants.set("A", a);
					aText.setText(aString);
					String bString = df.format(regression.getSlope());
					constants.set("B", slope);
					bText.setText(bString);				
				} catch (SQLException e) {
					msg.setMessage(e.getMessage());
					msg.open();
					e.printStackTrace();
				}
			}
		}
	}
	
	private class ResetAction extends Action{
		public ResetAction(){
			super("重置");
		}
		
		@Override
		public void run() {
			try {
				String a = COCOMOProperties.readValue("A");
				constants.set("A", Double.valueOf(a));
				aText.setText(a);
				String b = COCOMOProperties.readValue("B");
				constants.set("B", Double.valueOf(b));
				bText.setText(b);
			} catch (Exception e) {
				msg.setMessage(e.getMessage());
				msg.open();
				e.printStackTrace();
			}
		}
	}
	
	private class ComboChanged implements SelectionListener {
		Double A, B;
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {	
			widgetSelected(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				A = busiAccess.get(busiCombo.getText(), "A");
				B = busiAccess.get(busiCombo.getText(), "B");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			aText.setText(A.toString());
			bText.setText(B.toString());
		}
	}

	public static final int ID = -2;
	private Text aText, bText, itgText;
	private Combo busiCombo;
	private Table table;
	private ResetAction resetAction;
	private ConstantsAccess constants;
	private BusinessAreaAccess busiAccess;
	private MessageBox msg;
	public CalibInput(Composite parent){
		super(parent, null);
		constants = new ConstantsAccess();
		msg = new MessageBox(getShell());
		form.setText("参数调整");
		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(new SaveAction());
		toolBarManager.add(new CalibAction());
		resetAction = new ResetAction();
		toolBarManager.add(resetAction);
		toolBarManager.update(true);
		showParams();
		chooseHistoricalProjects();
	}

	private void showParams() {
		Composite panel = toolkit.createComposite(form.getBody());
		panel.setLayout(new GridLayout(2, false));
		try {
			toolkit.createLabel(panel, "业务领域:");
			busiAccess = new BusinessAreaAccess();
			Object[] textsObject = busiAccess.getAllName().toArray();
			String[] textsString = new String[textsObject.length];
			for(int i = 0; i < textsObject.length; i++)
				textsString[i] = (String) textsObject[i];
//			busiCombo = new ParameterCombo(panel, textsString, textsString, 0);
//			busiCombo.addSelectionListener(new ComboChanged());
			busiCombo = new Combo(panel, SWT.READ_ONLY);
			busiCombo.setItems(textsString);
			for(int i=0;i<textsString.length;i++){
				busiCombo.setData(textsString[i], textsString[i]);
			}
			busiCombo.addSelectionListener(new ComboChanged());
			
			toolkit.createLabel(panel, "A =");
			aText = toolkit.createText(panel, String.valueOf(constants.get("A")),SWT.BORDER);
			
			toolkit.createLabel(panel, "B =");
			bText = toolkit.createText(panel, String.valueOf(constants.get("B")),SWT.BORDER);
			
			toolkit.createLabel(panel, "集成因子=");
			itgText = toolkit.createText(panel, String.valueOf(constants.get("集成因子")),SWT.BORDER);
		} catch (SQLException e) {
			msg.setMessage(e.getMessage());
			msg.open();
			e.printStackTrace();
		}
	}
	
	private void chooseHistoricalProjects() {
		toolkit.createLabel(form.getBody(), "请选择历史项目");
		table = new Table(form.getBody(), SWT.BORDER | SWT.CHECK);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		String[] headers = {"节点名","实际规模（SLOC）", "实际工作量（PM）", "\u03A3SF", "\u03A0EM"};
		for(String h:headers){
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (h);
		}
		refresh();
		for(int i=0;i<headers.length;i++){
			table.getColumn(i).pack();
		}
	}

	@Override
	public void refresh() {
		table.removeAll();
		NodeBasicInfoAccess access = new NodeBasicInfoAccess();
		CocomoEstimationAccess cAccess = new CocomoEstimationAccess();
		try {
			ArrayList<Entity> historicalNodes = access.findAllWhere("realEffort >0 and realSLOC >0");
			for(Entity node:historicalNodes){
				TableItem item = new TableItem(table, SWT.NONE);
				item.setChecked(true);
				item.setText(0,(String)node.get("name"));
				item.setText(1,String.valueOf(node.get("realSLOC")));
				item.setText(2, String.valueOf(node.get("realEffort")));
				CocomoEstimationRecord cocomo = cAccess.getCocomoEstimationByNodeID((Integer)node.get("id"));
				if(cocomo!=null){
					item.setText(3, String.valueOf(cocomo.get("sumSF")));
					Double productEM = (Double)cocomo.get("productEM");
					Double sced = COCOMO.getSCEDValue((String)cocomo.get("SCED"));
					item.setText(4, String.valueOf(productEM*sced));
				}
			}
		} catch (SQLException e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
	}
	
	@Override
	public int getTabID(){
		return ID;
	}
}
