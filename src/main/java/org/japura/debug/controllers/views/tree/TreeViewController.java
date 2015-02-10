package org.japura.debug.controllers.views.tree;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class TreeViewController extends DefaultController<TreeViewPanel>{

  private TreeViewPanel component;

  public TreeViewController(Context context, Controller parentController) {
	super(context, parentController);
  }

  @Override
  public boolean isComponentInstancied() {
	if (component != null) {
	  return true;
	}
	return false;
  }

  @Override
  public TreeViewPanel getComponent() {
	if (component == null) {
	  component = new TreeViewPanel();
	}
	return component;
  }

  public void updateView() {
	getComponent().update();
  }

}
