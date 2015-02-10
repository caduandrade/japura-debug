package org.japura.debug;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.annotations.ContextName;
import org.japura.controller.annotations.GroupName;
import org.japura.controller.annotations.Singleton;
import org.japura.debug.controllers.DebugControllerManager;
import org.japura.debug.tasks.DebugTasks;

/**
 * <P>
 * Copyright (C) 2012-2013 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 */
@Singleton
@ContextName(name = "DEBUG")
@GroupName(name = "DebugWindow")
public class DebugWindowController extends DefaultController<DebugWindow>{

  private DebugWindow component;

  public DebugWindowController(Context context, Controller parentController) {
	super(context, parentController);
	setControllerName("DebugWindow");
	createChild(DebugControllerManager.class);
	createChild(DebugTasks.class);
  }

  @Override
  protected DebugWindow getComponent() {
	if (component == null) {
	  component = new DebugWindow();

	  component.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
		  free();
		}
	  });
	}
	return component;
  }

  @Override
  public boolean isComponentInstancied() {
	if (component != null) {
	  return true;
	}
	return false;
  }

  public void show() {
	getComponent().setVisible(true);
	getComponent().updateTabTitles();
  }

}
