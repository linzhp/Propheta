package calibration;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import data.database.dataAccess.NodeBasicInfoAccess;
import data.database.dataEntities.Entity;

import gui.tabs.ParameterArea;

public class CalibInput extends ParameterArea {
	public static final int ID = -2;
	public CalibInput(Composite parent){
		super(parent, null);
		form.setText("参数校准");
		Table table = new Table(form.getBody(), SWT.BORDER | SWT.MULTI);
		NodeBasicInfoAccess access = new NodeBasicInfoAccess();
		try {
			ArrayList<Entity> historicalNodes = access.findAllWhere("realEffort is not null and realSLOC is not null");
			for(Entity node:historicalNodes){
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText((String)node.get("name"));
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
