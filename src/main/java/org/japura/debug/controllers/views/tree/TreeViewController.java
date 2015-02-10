package org.japura.debug.controllers.views.tree;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class TreeViewController extends DefaultController<TreeViewPanel> {

  public TreeViewController(Context context, Controller parentController) {
    super(context, parentController);
  }

  @Override
  public TreeViewPanel buildComponent() {
    return new TreeViewPanel();
  }

  public void updateView() {
    getComponent().update();
  }

}
