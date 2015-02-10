package org.japura.debug.tasks;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.japura.gui.CheckComboBox;
import org.japura.gui.event.ListCheckListener;
import org.japura.gui.event.ListEvent;
import org.japura.gui.model.ListCheckModel;
import org.japura.task.messages.notify.TaskEvent;
import org.japura.task.messages.notify.TaskEventType;

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
public class DebugTasksEventPanel extends JPanel{

  private static final long serialVersionUID = 4L;

  private CheckComboBox eventComboBox;
  private CheckComboBox columnComboBox;
  private JPanel northPanel;
  private JButton clearButton;
  private JTable table;
  private List<TaskEvent> taskEvents;
  private List<Column> visibleColumns;
  private JCheckBox lastEventOnlyCheck;
  private List<TaskEvent> removeList;

  public DebugTasksEventPanel() {
	removeList = new ArrayList<TaskEvent>();
	visibleColumns = new ArrayList<Column>();
	ListCheckModel model = getColumnComboBox().getModel();
	for (Column column : Column.values()) {
	  model.addElement(column);
	  if (column.isDefaultEnabled()) {
		model.addCheck(column);
		visibleColumns.add(column);
	  }
	}
	model.addListCheckListener(new ListCheckListener() {
	  @Override
	  public void removeCheck(ListEvent event) {
		updateVisibleColumns();
	  }

	  @Override
	  public void addCheck(ListEvent event) {
		updateVisibleColumns();
	  }

	  private void updateVisibleColumns() {
		visibleColumns.clear();
		for (Object obj : getColumnComboBox().getModel().getCheckeds()) {
		  visibleColumns.add((Column) obj);
		}
		getTable().tableChanged(null);
		getTable().repaint();
	  }

	});

	taskEvents = new ArrayList<TaskEvent>();
	setLayout(new BorderLayout());
	add(new JScrollPane(getTable()), BorderLayout.CENTER);
	add(getNorthPanel(), BorderLayout.NORTH);
  }

  private JCheckBox getLastEventOnlyCheck() {
	if (lastEventOnlyCheck == null) {
	  lastEventOnlyCheck = new JCheckBox();
	  lastEventOnlyCheck.setText("last event only");
	}
	return lastEventOnlyCheck;
  }

  protected JTable getTable() {
	if (table == null) {
	  table = new JTable();
	  table.setModel(new Model());
	}
	return table;
  }

  private JPanel getNorthPanel() {
	if (northPanel == null) {
	  northPanel = new JPanel();
	  FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
	  northPanel.setLayout(fl);
	  northPanel.add(new JLabel("Events:"));
	  northPanel.add(getEventComboBox());
	  northPanel.add(new JLabel("Columns:"));
	  northPanel.add(getColumnComboBox());
	  northPanel.add(getLastEventOnlyCheck());
	  northPanel.add(getClearButton());
	}
	return northPanel;
  }

  private CheckComboBox getEventComboBox() {
	if (eventComboBox == null) {
	  eventComboBox = new CheckComboBox();
	  eventComboBox.setTextFor(CheckComboBox.NONE, "* any item selected *");
	  eventComboBox.setTextFor(CheckComboBox.MULTIPLE, "* multiple items *");
	  eventComboBox.setTextFor(CheckComboBox.ALL, "* all selected *");

	  ListCheckModel model = eventComboBox.getModel();
	  for (TaskEventType te : TaskEventType.values()) {
		model.addElement(te);
		if (te.equals(TaskEventType.AFTER) == false
			&& te.equals(TaskEventType.BEFORE) == false) {
		  model.addCheck(te);
		}
	  }
	}
	return eventComboBox;
  }

  private CheckComboBox getColumnComboBox() {
	if (columnComboBox == null) {
	  columnComboBox = new CheckComboBox();
	  columnComboBox.setTextFor(CheckComboBox.NONE, "* any item selected *");
	  columnComboBox.setTextFor(CheckComboBox.MULTIPLE, "* multiple items *");
	  columnComboBox.setTextFor(CheckComboBox.ALL, "* all selected *");
	}
	return columnComboBox;
  }

  protected JButton getClearButton() {
	if (clearButton == null) {
	  clearButton = new JButton("Clear");
	  clearButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		  taskEvents.clear();
		  getTable().revalidate();
		}
	  });
	}
	return clearButton;
  }

  public void addTaskDebugLog(final TaskEvent event) {
	if (getEventComboBox().getModel().isChecked(event.getEventType()) == false) {
	  return;
	}
	SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
		if (getLastEventOnlyCheck().isSelected()) {
		  String id = event.getTaskId();
		  for (TaskEvent event : taskEvents) {
			if (event.getTaskId().equals(id)) {
			  removeList.add(event);
			}
		  }
		  int index = -1;
		  if (removeList.size() > 0) {
			index = taskEvents.indexOf(removeList.get(0));
		  }
		  taskEvents.removeAll(removeList);
		  removeList.clear();
		  if (index > -1) {
			taskEvents.add(index, event);
		  } else {
			taskEvents.add(event);
		  }

		} else {
		  taskEvents.add(event);
		}
		getTable().revalidate();
		getTable().repaint();
	  }
	});
  }

  private class Model implements TableModel{

	private Date date = new Date();

	@Override
	public int getRowCount() {
	  return taskEvents.size();
	}

	@Override
	public int getColumnCount() {
	  return visibleColumns.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
	  Column column = visibleColumns.get(columnIndex);
	  return column.toString();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
	  return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	  return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	  Column column = visibleColumns.get(columnIndex);
	  TaskEvent event = taskEvents.get(rowIndex);
	  if (column.equals(Column.ID)) {
		return event.getTaskId();
	  }
	  if (column.equals(Column.NAME)) {
		return event.getName();
	  }
	  if (column.equals(Column.CLASS)) {
		return event.getTaskClass();
	  }
	  if (column.equals(Column.EVENT)) {
		return event.getEventType();
	  }
	  if (column.equals(Column.EDT)) {
		if (event.isEdt()) {
		  return "yes";
		}
		return "no";
	  }
	  if (column.equals(Column.TIME_SPENT)) {
		return event.getTimeSpent();
	  }
	  if (column.equals(Column.WAIT_FOR_EDT)) {
		if (event.isWaitForEDT()) {
		  return "yes";
		}
		return "no";
	  }
	  if (column.equals(Column.START_TIME)) {
		date.setTime(event.getStartTime());
		return date.toString();
	  }
	  if (column.equals(Column.TASK_EXECUTOR)) {
		return event.getSource();
	  }
	  if (column.equals(Column.SESSION)) {
		return event.getSession();
	  }
	  return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

	@Override
	public void addTableModelListener(TableModelListener l) {}

	@Override
	public void removeTableModelListener(TableModelListener l) {}

  }

  enum Column {
	ID("Id", false),
	CLASS("Class", true),
	NAME("Name", true),
	EVENT("Event", true),
	EDT("EDT", false),
	TIME_SPENT("TimeSpent(doInBackground)", false),
	WAIT_FOR_EDT("WaitForEDT", false),
	START_TIME("StartTime", false),
	TASK_EXECUTOR("TaskExecutor", true),
	SESSION("Session", false);

	private boolean defaultEnabled;
	private String columnName;

	private Column(String name, boolean defaultEnabled) {
	  this.columnName = name;
	  this.defaultEnabled = defaultEnabled;
	}

	public boolean isDefaultEnabled() {
	  return defaultEnabled;
	}

	public String toString() {
	  return columnName;
	}
  }

}
