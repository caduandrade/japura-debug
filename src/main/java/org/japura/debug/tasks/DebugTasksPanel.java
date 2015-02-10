package org.japura.debug.tasks;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.japura.debug.tasks.executions.DebugTasksExecutionsPanel;

/**
 * <P>
 * Copyright (C) 20112-2013 Carlos Eduardo Leite de Andrade
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
public class DebugTasksPanel extends JPanel{

  private static final long serialVersionUID = 4L;

  private DebugTasksExecutionsPanel executionsPanel;
  private DebugTasksEventPanel eventPanel;
  private JRadioButton eventView;
  private JRadioButton executionView;
  private JPanel contentPanel;

  public DebugTasksPanel() {
	setLayout(new MigLayout("", "grow", "[][grow]"));
	this.contentPanel = new JPanel();
	this.contentPanel.setLayout(new BorderLayout());
	add(new JLabel("views:"), "split 3");
	add(getEventView());
	add(getExecutionView(), "wrap");
	add(this.contentPanel, "grow");
	this.contentPanel.add(getEventPanel());
	ButtonGroup bg = new ButtonGroup();
	bg.add(getEventView());
	bg.add(getExecutionView());

	ActionListener l = new ActionListener() {
	  @Override
	  public void actionPerformed(ActionEvent e) {
		updateView();
	  }
	};
	getEventView().addActionListener(l);
	getExecutionView().addActionListener(l);
  }

  private void updateView() {
	this.contentPanel.removeAll();
	if (getEventView().isSelected()) {
	  this.contentPanel.add(getEventPanel());
	} else {
	  this.contentPanel.add(getExecutionsPanel());
	}
	this.contentPanel.revalidate();
	repaint();
  }

  public JRadioButton getEventView() {
	if (eventView == null) {
	  eventView = new JRadioButton();
	  eventView.setText("events");
	  eventView.setSelected(true);
	}
	return eventView;
  }

  public JRadioButton getExecutionView() {
	if (executionView == null) {
	  executionView = new JRadioButton();
	  executionView.setText("executions");
	}
	return executionView;
  }

  public DebugTasksExecutionsPanel getExecutionsPanel() {
	if (executionsPanel == null) {
	  executionsPanel = new DebugTasksExecutionsPanel();
	}
	return executionsPanel;
  }

  public DebugTasksEventPanel getEventPanel() {
	if (eventPanel == null) {
	  eventPanel = new DebugTasksEventPanel();
	}
	return eventPanel;
  }
}
