package calibration;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.Entity;
import data.file.COCOMOProperties;

import gui.tabs.ParameterArea;

public class CalibInput extends ParameterArea {
	public static final int ID = -2;
	private Label aLabel, bLabel;
	public CalibInput(Composite parent){
		super(parent, null);
		form.setText("参数校准");
		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(new Action() {
			@Override
			public void run() {
				
			}
		});
		showParams();
		chooseHistoricalProjects();
	}

	private void showParams() {
		Composite panel = toolkit.createComposite(form.getBody());
		panel.setLayout(new GridLayout(2, false));
		toolkit.createLabel(panel, "A=");
		aLabel = toolkit.createLabel(panel, COCOMOProperties.readValue("A"));
		toolkit.createLabel(panel, "B=");
		bLabel = toolkit.createLabel(panel, COCOMOProperties.readValue("B"));
	}
	
	private void chooseHistoricalProjects() {
		toolkit.createLabel(form.getBody(), "请选择历史项目");
		Table table = new Table(form.getBody(), SWT.BORDER | SWT.CHECK);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		String[] headers = {"节点名","实际规模（SLOC）", "实际工作量（PM）"};
		for(String h:headers){
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (h);
		}
		NodeBasicInfoAccess access = new NodeBasicInfoAccess();
		try {
			ArrayList<Entity> historicalNodes = access.findAllWhere("realEffort >0 and realSLOC >0");
			for(Entity node:historicalNodes){
				TableItem item = new TableItem(table, SWT.NONE);
				item.setChecked(true);
				item.setText(0,(String)node.get("name"));
				item.setText(1,String.valueOf(node.get("realSLOC")));
				item.setText(2, String.valueOf(node.get("realEffort")));
			}
		} catch (SQLException e) {
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage(e.getMessage());
			messageBox.open();
			e.printStackTrace();
		}
		for(int i=0;i<headers.length;i++){
			table.getColumn(i).pack();
		}
	}
	
	@Override
	public int getTabID(){
		return ID;
	}
}
