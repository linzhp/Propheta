package gui.widgets.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import data.database.dataEntities.EstimateNode;


public class TreeContentProvider implements ITreeContentProvider{
	
	
	@Override
	public Object[] getChildren(Object input) {
		if(input instanceof EstimateNode){
			EstimateNode en=(EstimateNode)input;
			return en.getChildren().toArray();
		}else{
			return new Object[0];
		}
		
	}

	@Override
	public Object getParent(Object input) {
		if(input instanceof EstimateNode){
			EstimateNode en=(EstimateNode)input;
			return en.getParent();
		}else{
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object input) {
		if(input instanceof EstimateNode){
			EstimateNode en=(EstimateNode)input;
			return en.hasChildren();
		}else{
			return false;
		}
	}

	@Override
	public Object[] getElements(Object input) {
		return getChildren(input);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

}
