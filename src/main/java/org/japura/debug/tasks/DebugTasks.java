package org.japura.debug.tasks;

import javax.swing.SwingUtilities;

import org.japura.Application;
import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.DefaultController;
import org.japura.debug.Debug;
import org.japura.message.Message;
import org.japura.task.messages.notify.TaskEventMessage;
import org.japura.task.messages.notify.TaskExecutionMessage;

/**
 * <P>
 * Copyright (C) 2011-2013 Carlos Eduardo Leite de Andrade
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
public class DebugTasks extends DefaultController<DebugTasksPanel> implements
	Debug<DebugTasksPanel>{

  private DebugTasksPanel component;

  public DebugTasks(Context context, Controller parentController) {
	super(context, parentController);
	Application.getTaskManager().setNotifyMessagesEnabled(true);
  }

  @Override
  public DebugTasksPanel getComponent() {
	if (component == null) {
	  component = new DebugTasksPanel();
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
  public String getTitle() {
	return "Tasks";
  }

  @Override
  public void subscribe(Message message, Object publisher) {
	super.subscribe(message, publisher);

	if (message instanceof TaskEventMessage) {
	  final TaskEventMessage tem = (TaskEventMessage) message;
	  SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		  getComponent().getEventPanel().addTaskDebugLog(tem.getTaskEvent());
		}
	  });
	}

	if (message instanceof TaskExecutionMessage) {
	  final TaskExecutionMessage tem = (TaskExecutionMessage) message;
	  SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run() {
		  getComponent().getExecutionsPanel().getExecutionPanel().perform(tem);
		}
	  });
	}
  }

}
