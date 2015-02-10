package org.japura.debug.controllers.views.context;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class ContextViewController extends DefaultController<ContextViewPanel>{

  private ContextViewPanel component;

  public ContextViewController(Context context, Controller parentController) {
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
  public ContextViewPanel getComponent() {
	if (component == null) {
	  component = new ContextViewPanel();
	}
	return component;
  }

  public void updateView() {
	getComponent().updateView(true);
  }

}
