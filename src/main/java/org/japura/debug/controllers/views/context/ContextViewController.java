package org.japura.debug.controllers.views.context;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class ContextViewController extends DefaultController<ContextViewPanel> {

  public ContextViewController(Context context, Controller parentController) {
    super(context, parentController);
  }

  @Override
  public ContextViewPanel buildComponent() {
    return new ContextViewPanel();
  }

  public void updateView() {
    getComponent().updateView(true);
  }

}
