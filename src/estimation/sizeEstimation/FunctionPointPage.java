package estimation.sizeEstimation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

import static gui.Helper.*;

public class FunctionPointPage extends BaseSizePage {
	public static final String NAME="功能点估算";
	private final int[][] WEIGHTS = {
			{3,4,6},
			{4,5,7},
			{3,4,6},
			{7,10,15},
			{5,7,10}};
	private Spinner[][] inputs = new Spinner[5][3];
	
	public FunctionPointPage(){
		super(NAME);
		setTitle(NAME);
	}

	@Override
	protected int getSize() {
		int size = 0;
		for(int i=0;i<5;i++)
			for(int j=0;j<3;j++){
				size += inputs[i][j].getSelection()*WEIGHTS[i][j];
			}
		return size;
	}

	@Override
	public void createControl(Composite parent) {
		Composite pane = new Composite(parent, SWT.NONE);
		pane.setLayout(new GridLayout(4, false));
		createLabel(pane, "程序特性");
		createLabel(pane, "低复杂度");
		createLabel(pane, "中复杂度");
		createLabel(pane, "高复杂度");
		createFeature(pane, 0, "外部输入");
		createFeature(pane, 1, "外部输出");
		createFeature(pane, 2, "外部查询");
		createFeature(pane, 3, "内部逻辑文件");
		createFeature(pane, 4, "内部接口文件");
		this.setControl(pane);
	}
	
	private void createFeature(Composite pane, int row, String name){
		createLabel(pane, name);
		for(int i=0;i<3;i++){
			inputs[row][i]=createSpinner(pane, 0, 0);
		}
	}
}
