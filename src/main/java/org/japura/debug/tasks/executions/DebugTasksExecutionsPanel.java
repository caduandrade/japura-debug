package org.japura.debug.tasks.executions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class DebugTasksExecutionsPanel extends JPanel{

  private static final long serialVersionUID = 1L;
  private TaskPanelListener listener;
  private ExecutionViewPanel executionPanel;
  private InfoViewPanel infoPanel;
  private JScrollPane infoPanelScrollPane;
  private JSplitPane splitPane;
  private JButton clearButton;

  public DebugTasksExecutionsPanel() {
	setLayout(new MigLayout("ins 0 0 0 0, wrap 1", "grow", "[][grow]"));
	add(getClearButton());
	add(getSplitPane(), "grow");
  }

  public JSplitPane getSplitPane() {
	if (splitPane == null) {
	  splitPane = new JSplitPane();
	  splitPane.setLeftComponent(new JScrollPane(getExecutionPanel()));
	  splitPane.setRightComponent(getInfoPanelScrollPane());
	  splitPane.setResizeWeight(0d);
	}
	return splitPane;
  }

  public JScrollPane getInfoPanelScrollPane() {
	if (infoPanelScrollPane == null) {
	  infoPanelScrollPane = new JScrollPane(getInfoPanel());
	}
	return infoPanelScrollPane;
  }

  public JButton getClearButton() {
	if (clearButton == null) {
	  clearButton = new JButton();
	  clearButton.setText("Clear");
	  clearButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		  getInfoPanel().clear();
		  getExecutionPanel().clear();
		  repaint();
		}
	  });
	}
	return clearButton;
  }

  public ExecutionViewPanel getExecutionPanel() {
	if (executionPanel == null) {
	  executionPanel = new ExecutionViewPanel(getListener());
	}
	return executionPanel;
  }

  public InfoViewPanel getInfoPanel() {
	if (infoPanel == null) {
	  infoPanel = new InfoViewPanel();
	}
	return infoPanel;
  }

  public TaskPanelListener getListener() {
	if (listener == null) {
	  listener = new TaskPanelListener() {
		@Override
		public void action(Execution e) {
		  getExecutionPanel().updateSelection(e);
		  getInfoPanel().action(e);
		  SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
			  infoPanelScrollPane.getVerticalScrollBar().setValue(
				  infoPanelScrollPane.getVerticalScrollBar().getMinimum());
			}
		  });

		}
	  };
	}
	return listener;
  }
}
