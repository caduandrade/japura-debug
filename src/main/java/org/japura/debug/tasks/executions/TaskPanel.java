package org.japura.debug.tasks.executions;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import org.japura.task.TaskStatus;

/**
 * <P>
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
 */
public class TaskPanel extends JPanel{

  private static final long serialVersionUID = 1L;

  private Execution e;
  private TaskPanelListener listener;

  public TaskPanel(Execution e, TaskPanelListener listener) {
	this.e = e;
	this.listener = listener;
	TaskStatus status = e.getStatus();

	setBackground(Color.WHITE);

	if (status.equals(TaskStatus.ERROR)) {
	  updateBorder(Color.RED);
	} else if (status.equals(TaskStatus.CANCELED)) {
	  updateBorder(Color.YELLOW);
	} else if (status.equals(TaskStatus.DONE)) {
	  updateBorder(Color.GREEN);
	} else if (status.equals(TaskStatus.DISCARDED)) {
	  updateBorder(Color.ORANGE);
	} else {
	  updateBorder(Color.BLACK);
	}

	setLayout(new MigLayout());
	add(new JLabel("Class: " + e.getTaskClass()), "wrap");
	add(new JLabel("Name: " + e.getTaskName()), "wrap");

	MouseAdapter ma = new MouseAdapter() {
	  @Override
	  public void mouseClicked(MouseEvent e) {
		TaskPanel.this.listener.action(getE());
	  }
	};
	addMouseListener(ma);
  }

  public Execution getE() {
	return e;
  }

  private void updateBorder(Color color) {
	Border in = BorderFactory.createLineBorder(Color.GRAY, 1);
	Border out = BorderFactory.createLineBorder(color, 3);
	Border in2 = BorderFactory.createCompoundBorder(out, in);
	Border out2 = BorderFactory.createLineBorder(Color.GRAY, 1);
	setBorder(BorderFactory.createCompoundBorder(out2, in2));
  }

}
