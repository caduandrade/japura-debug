package org.japura.debug.controllers.views.list;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ChildController;

@ChildController
public class ListViewPanelController extends DefaultController<ListViewPanel>{

  private ListViewPanel component;

  public ListViewPanelController(Context context, Controller parentController) {
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
  public ListViewPanel getComponent() {
	if (component == null) {
	  component = new ListViewPanel();
	}
	return component;
  }

  public void updateView() {
	getComponent().update();
  }

}
