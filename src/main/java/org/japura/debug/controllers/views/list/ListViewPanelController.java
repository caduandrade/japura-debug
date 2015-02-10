package org.japura.debug.controllers.views.list;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class ListViewPanelController extends DefaultController<ListViewPanel> {

  public ListViewPanelController(Context context, Controller parentController) {
    super(context, parentController);
  }

  @Override
  public ListViewPanel buildComponent() {
    return new ListViewPanel();
  }

  public void updateView() {
    getComponent().update();
  }

}
