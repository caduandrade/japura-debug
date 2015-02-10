package org.japura.debug.controllers.views.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode{

  private boolean expanded;
  
  public Node(){
  }

  
  public Node(Object userObject) {
	super(userObject);
  }

  public boolean isExpanded() {
	return expanded;
  }

  public void setExpanded(boolean expanded) {
	this.expanded = expanded;
  }

}
