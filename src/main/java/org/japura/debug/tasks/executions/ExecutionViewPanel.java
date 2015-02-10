package org.japura.debug.tasks.executions;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.japura.task.messages.notify.TaskExecutionMessage;

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
public class ExecutionViewPanel extends JPanel{

  private static final long serialVersionUID = 4L;

  private TaskPanelListener listener;
  private List<SessionExecutions> executions =
	  new ArrayList<SessionExecutions>();
  private Map<Execution, ExecutionPanel> executionViewPanels =
	  new HashMap<Execution, ExecutionViewPanel.ExecutionPanel>();

  private int executionsLimit = 50;

  public ExecutionViewPanel(TaskPanelListener listener) {
	this.listener = listener;
	setBackground(Color.WHITE);
	setLayout(new MigLayout("wrap 1, ins 10 5 10 5, gap 0 0", "grow", ""));
  }

  public void clear() {
	this.executions.clear();
	refresh();
  }

  public void perform(TaskExecutionMessage msg) {
	Execution e = new Execution(msg);

	if (executions.size() == 0) {
	  SessionExecutions ec = new SessionExecutions(e.getSessionId());
	  ec.add(e);
	  executions.add(ec);
	} else {
	  SessionExecutions ec = executions.get(0);
	  if (ec.getSessionId().equals(e.getSessionId())) {
		ec.add(0, e);
	  } else {
		ec = new SessionExecutions(e.getSessionId());
		ec.add(e);
		executions.add(0, ec);
	  }
	}

	if (executions.size() > executionsLimit) {
	  executions.remove(executions.size() - 1);
	}
	refresh();
  }

  public void updateSelection(Execution e) {
	for (Entry<Execution, ExecutionPanel> entry : executionViewPanels
		.entrySet()) {
	  Execution e2 = entry.getKey();
	  if (e2.equals(e)) {
		entry.getValue().connectorPanel.setOpaque(true);
	  } else {
		entry.getValue().connectorPanel.setOpaque(false);
	  }
	}
	repaint();
  }

  private void refresh() {
	executionViewPanels.clear();
	removeAll();
	if (executions.size() == 0) {
	  return;
	}

	for (int j = 0; j < executions.size(); j++) {

	  SessionExecutions ec = executions.get(j);

	  StringBuilder sb = new StringBuilder();
	  for (int i = 0; i < ec.size() - 1; i++) {
		sb.append("[]");
	  }
	  sb.append("[grow]");

	  add(new LineHorizontal(), "grow x, wrap");

	  JPanel sessionExecutionsPanel = new JPanel();
	  sessionExecutionsPanel.setOpaque(false);
	  sessionExecutionsPanel.setLayout(new MigLayout(
		  "wrap 1,ins 0 0 0 0,  gap  0 0 0 0", "grow", sb.toString()));

	  add(sessionExecutionsPanel, "grow");

	  for (int i = 0; i < ec.size(); i++) {
		Execution e = ec.get(i);
		JPanel executionContainer = new JPanel();
		executionContainer.setOpaque(false);

		if (i == ec.size() - 1) {
		  executionContainer.setLayout(new MigLayout(
			  "wrap 1, ins 0 0 0 0,  gap  0 0", "grow", "[][][grow]"));
		} else {
		  executionContainer.setLayout(new MigLayout(
			  " wrap 1, ins 0 0 0 0,  gap  0 0", "grow", "[][][]"));
		}

		if (j == 0 && i == 0) {
		  executionContainer.add(new EmptySpace(), "");
		} else {
		  executionContainer.add(new LineVertical(), "");
		}

		int level = e.getLevel();

		ExecutionPanel executionPanel =
			new ExecutionPanel(new TaskPanel(e, listener));
		executionViewPanels.put(e, executionPanel);

		if (i == 0) {
		  if (j == 0) {
			executionContainer.add(new LineEnd(level), "grow y, split 2");
			executionContainer.add(executionPanel, "grow x");

		  } else {
			if (e.isParallel()) {
			  executionContainer
				  .add(new LineParallel(level), "grow y, split 2");
			  executionContainer.add(executionPanel, "grow x");
			} else {
			  executionContainer.add(new LineMiddle(level), "grow y, split 2");
			  executionContainer.add(executionPanel, "grow x");
			}
		  }
		} else {
		  if (e.isParallel()) {
			executionContainer.add(new LineParallel(level), "grow y, split 2");
			executionContainer.add(executionPanel, "grow x");
		  } else {
			executionContainer.add(new LineMiddle(level), "grow y, split 2");
			executionContainer.add(executionPanel, "grow x");
		  }
		}
		if (i == ec.size() - 1) {
		  executionContainer.add(new LineVertical(), "grow y");
		  sessionExecutionsPanel.add(executionContainer, "grow");
		} else {
		  executionContainer.add(new LineVertical(), "");
		  sessionExecutionsPanel.add(executionContainer, "grow x");
		}
	  }
	}

	add(new LineHorizontal(), " grow x");

	revalidate();
  }

  private static class ConnectorPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public ConnectorPanel() {
	  setBackground(Color.lightGray);
	  setPreferredSize(new Dimension(50, 20));
	}
  }

  private static class ExecutionPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private ConnectorPanel connectorPanel;

	public ExecutionPanel(TaskPanel tp) {
	  connectorPanel = new ConnectorPanel();
	  setOpaque(false);
	  setLayout(new MigLayout(" ins 0 0 0 0", "[]15[grow]"));
	  add(tp);
	  add(connectorPanel, "grow x");
	  connectorPanel.setOpaque(false);
	}

  }

}
