package org.japura.debug.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.japura.Application;
import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.controller.messages.ControllerRegisterMessage;
import org.japura.controller.messages.ControllerUnregisterMessage;
import org.japura.debug.Debug;
import org.japura.debug.controllers.views.context.ContextViewController;
import org.japura.debug.controllers.views.list.ListViewPanelController;
import org.japura.debug.controllers.views.tree.TreeViewController;
import org.japura.message.Message;

/**
 * 
 * Copyright (C) 2013 Carlos Eduardo Leite de Andrade
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
 * 
 */
public class DebugControllerManager extends
	DefaultController<DebugControllerManagerPanel> implements
	Debug<DebugControllerManagerPanel>{

  private DebugControllerManagerPanel component;

  public DebugControllerManager(Context context, Controller parentController) {
	super(context, parentController);
	createChild(ListViewPanelController.class);
	createChild(ContextViewController.class);
	createChild(TreeViewController.class);
	update();
  }

  @Override
  public String getTitle() {
	int count = Application.getControllerManager().count();
	return "Controllers - count: " + count;
  }

  @Override
  public DebugControllerManagerPanel getComponent() {
	if (component == null) {
	  component = new DebugControllerManagerPanel();

	  component.getListButton().addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		  ListViewPanelController c = getChild(ListViewPanelController.class);
		  getComponent().updateViewComponent(c.getComponent());
		}
	  });

	  component.getTreeButton().addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		  TreeViewController c = getChild(TreeViewController.class);
		  getComponent().updateViewComponent(c.getComponent());
		}
	  });

	  component.getContextButton().addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		  ContextViewController c = getChild(ContextViewController.class);
		  getComponent().updateViewComponent(c.getComponent());
		}
	  });

	  TreeViewController c = getChild(TreeViewController.class);
	  component.updateViewComponent(c.getComponent());
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

  @Override
  public void subscribe(Message message, Object publisher) {
	super.subscribe(message, publisher);

	if (message instanceof ControllerRegisterMessage) {
	  ControllerRegisterMessage crm = (ControllerRegisterMessage) message;
	  if (crm.getRegisteredController().getGroupId().equals(getGroupId())) {
		return;
	  }
	  update();
	}

	if (message instanceof ControllerUnregisterMessage) {
	  update();
	}
  }

  public void update() {
	ContextViewController cv = getChild(ContextViewController.class);
	if (cv != null) {
	  cv.updateView();
	}

	ListViewPanelController lv = getChild(ListViewPanelController.class);
	if (lv != null) {
	  lv.updateView();
	}

	TreeViewController tv = getChild(TreeViewController.class);
	if (tv != null) {
	  tv.updateView();
	}
  }

}
