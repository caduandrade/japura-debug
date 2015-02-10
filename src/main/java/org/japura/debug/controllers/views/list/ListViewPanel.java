package org.japura.debug.controllers.views.list;

import java.awt.Component;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import org.japura.Application;
import org.japura.controller.Controller;

public class ListViewPanel extends JSplitPane{

  private static final long serialVersionUID = 1L;

  private InfoViewPanel infoViewPanel;
  private JScrollPane scrollPaneList;
  private JScrollPane scrollPaneInfo;
  private JList list;

  protected ListViewPanel() {
	setLeftComponent(getScrollPaneList());
	setRightComponent(getScrollPaneInfo());
	setResizeWeight(.5d);
  }

  public JScrollPane getScrollPaneInfo() {
	if (scrollPaneInfo == null) {
	  scrollPaneInfo = new JScrollPane(getInfoViewPanel());
	}
	return scrollPaneInfo;
  }

  public JScrollPane getScrollPaneList() {
	if (scrollPaneList == null) {
	  scrollPaneList = new JScrollPane(getList());
	}
	return scrollPaneList;
  }

  public InfoViewPanel getInfoViewPanel() {
	if (infoViewPanel == null) {
	  infoViewPanel = new InfoViewPanel();
	}
	return infoViewPanel;
  }

  public JList getList() {
	if (list == null) {
	  list = new JList();
	  list.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	  list.setCellRenderer(new ListRenderer());
	}
	return list;
  }

  private class ListRenderer extends DefaultListCellRenderer{
	private static final long serialVersionUID = 197732829250426432L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
												  int index,
												  boolean isSelected,
												  boolean cellHasFocus) {

	  JLabel comp =
		  (JLabel) super.getListCellRendererComponent(list, value, index,
			  isSelected, cellHasFocus);
	  Controller controller = (Controller) value;
	  boolean fullName = true;
	  comp.setText(controllerToString(controller, fullName));
	  return comp;
	}
  }

  public void update() {
	SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
		Collection<Controller> list =
			Application.getControllerManager().getAll();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultListModel listModel = new DefaultListModel();

		for (Controller c : list) {
		  if (c.getParentId() == null) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(c);
			root.add(node);
			listModel.addElement(c);
			addChild(c, listModel);
		  }
		}

		getList().setModel(listModel);
	  }
	});
  }

  private void addChild(Controller parent, DefaultListModel listModel) {
	Collection<Controller> list = parent.getChildren();
	if (list.size() > 0) {
	  for (Controller child : list) {
		Controller c = (Controller) child;
		listModel.addElement(child);
		addChild(c, listModel);
	  }
	}
  }

  private static String controllerToString(Controller controller,
										   boolean fullName) {
	String name = null;
	if (fullName) {
	  name = controller.getClass().getName();
	} else {
	  name = controller.getClass().getSimpleName();
	}
	return name + " [ " + controller.stringToDebugComponent() + " ]";
  }
}
