package org.japura.debug.tasks.executions;

import java.awt.Color;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.japura.debug.InfoNodeUtil;

public class InfoViewPanel extends JPanel{

  private static final long serialVersionUID = 1L;

  public InfoViewPanel() {
	setBackground(Color.lightGray);
	setLayout(new MigLayout("ins 20 20 20 20, gap 15 15 15 15, wrap 1", "grow",
		""));
  }

  public void clear() {
	action(null);
  }

  public void action(Execution e) {
	removeAll();
	if (e != null) {
	  add(InfoNodeUtil.buildTitlePanel("Task", e.getTaskInfoNodes()), "grow x");
	  add(InfoNodeUtil.buildTitlePanel("Task Session", e.getSessionInfoNodes()),
		  "grow x");
	  add(InfoNodeUtil.buildTitlePanel("Task Executor",
		  e.getExecutorInfoNodes()), "grow x");

	  if (e.getContextInfoNodes() != null) {
		add(InfoNodeUtil.buildTitlePanel("Context", e.getContextInfoNodes()),
			"grow x");
	  }
	}
	revalidate();
	if (getParent() != null) {
	  getParent().repaint();
	}
  }
}
