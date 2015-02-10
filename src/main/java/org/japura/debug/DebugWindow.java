package org.japura.debug;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.japura.Application;
import org.japura.debug.controllers.DebugControllerManager;
import org.japura.debug.tasks.DebugTasks;

/**
 * 
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
 * 
 */
public final class DebugWindow extends JDialog{

  private static final long serialVersionUID = 3L;
  private JTabbedPane tabbedPane;

  public DebugWindow() {
	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	setModal(false);
	setSize(800, 500);
	setTitle("Japura - Debug");
	add(getTabbedPane());
  }

  private JTabbedPane getTabbedPane() {
	if (tabbedPane == null) {
	  tabbedPane = new JTabbedPane();

	  tabbedPane.addTab("",
		  Application.getControllerManager().get(DebugControllerManager.class)
			  .getComponent());
	  tabbedPane.addTab("",
		  Application.getControllerManager().get(DebugTasks.class)
			  .getComponent());

	}
	return tabbedPane;
  }

  protected void updateTabTitles() {
	SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
		String title =
			Application.getControllerManager()
				.get(DebugControllerManager.class).getTitle();
		getTabbedPane().setTitleAt(0, title);
		title =
			Application.getControllerManager().get(DebugTasks.class).getTitle();
		getTabbedPane().setTitleAt(1, title);
	  }
	});
  }

}
