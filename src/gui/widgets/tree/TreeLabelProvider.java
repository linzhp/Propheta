package gui.widgets.tree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import estimation.entity.EstimateNode;

public class TreeLabelProvider implements ILabelProvider {

	private Image rootImage=null;
	private Image nodeImage=null;
	private Image leafImage=null;
	
	
	public TreeLabelProvider(){
		try {
			rootImage=new Image(null,new FileInputStream("./icons/icon_root.gif"));
			nodeImage=new Image(null,new FileInputStream("./icons/icon_node.gif"));
			leafImage=new Image(null,new FileInputStream("./icons/icon_leaf.gif"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Image getImage(Object arg0) {
		
		EstimateNode en=(EstimateNode)arg0;
		if(en.isRoot()){
			return rootImage;
		}else{
			if(en.isLeaf()){
				return leafImage;
			}else{
				return nodeImage;
			}
		}
	}

	@Override
	public String getText(Object arg0) {
		EstimateNode en=(EstimateNode)arg0;
		return en.getName();
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		if(rootImage!=null){
			rootImage.dispose();
		}
		if(nodeImage!=null){
			nodeImage.dispose();
		}
		if(leafImage!=null){
			leafImage.dispose();
		}
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

}
