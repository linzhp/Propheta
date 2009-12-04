package entity;

import java.util.List;

public interface TreeNode {

	public String getName();
	public List getChildren();
	public TreeNode getParent();
	public boolean hasChildren();
	public boolean isRoot();
	public boolean isLeaf();
}
