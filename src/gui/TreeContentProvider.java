package gui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import entity.EstimateNode;

public class TreeContentProvider implements ITreeContentProvider{
	
	
	@Override
	public Object[] getChildren(Object arg0) {
		if(arg0 instanceof EstimateNode){
			EstimateNode en=(EstimateNode)arg0;
			return en.children.toArray();
		}else{
			return new Object[0];
		}
		
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		if(arg0 instanceof EstimateNode){
			EstimateNode en=(EstimateNode)arg0;
			if(en.getChildren().size()>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public Object[] getElements(Object arg0) {
		if(arg0 instanceof EstimateNode){
			EstimateNode en=(EstimateNode)arg0;
			return en.children.toArray();
		}else if(arg0 instanceof List){
			return ((List)arg0).toArray();
		}else{
			return new Object[0];
		}
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
