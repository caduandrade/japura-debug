package org.japura.debug.controllers.views.tree;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

import org.japura.Application;
import org.japura.controller.Context;
import org.japura.controller.Controller;

public class TreeViewPanel extends JSplitPane{

  private static final long serialVersionUID = 1L;

  private InfoViewPanel infoViewPanel;
  private JScrollPane scrollPaneTree;
  private JScrollPane scrollPaneInfo;
  private JTree tree;

  protected TreeViewPanel() {
	setLeftComponent(getScrollPaneTree());
	setRightComponent(getScrollPaneInfo());
	setResizeWeight(.5d);
  }

  public JScrollPane getScrollPaneInfo() {
	if (scrollPaneInfo == null) {
	  scrollPaneInfo = new JScrollPane(getInfoViewPanel());
	}
	return scrollPaneInfo;
  }

  public JScrollPane getScrollPaneTree() {
	if (scrollPaneTree == null) {
	  scrollPaneTree = new JScrollPane(getTree());
	}
	return scrollPaneTree;
  }

  public InfoViewPanel getInfoViewPanel() {
	if (infoViewPanel == null) {
	  infoViewPanel = new InfoViewPanel();
	}
	return infoViewPanel;
  }

  public JTree getTree() {
	if (tree == null) {
	  tree = new JTree();
	  tree.setModel(new DefaultTreeModel(new Node()));
	  tree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	  tree.setCellRenderer(new TreeRenderer());
	  tree.setRootVisible(false);
	  tree.setShowsRootHandles(true);
	  tree.addTreeExpansionListener(new TreeExpansionListener() {
		@Override
		public void treeExpanded(TreeExpansionEvent event) {
		  Node node = (Node) event.getPath().getLastPathComponent();
		  node.setExpanded(true);
		}

		@Override
		public void treeCollapsed(TreeExpansionEvent event) {}
	  });
	  tree.getSelectionModel().addTreeSelectionListener(
		  new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
			  TreePath tp = e.getNewLeadSelectionPath();
			  if (tp != null) {
				Node node = (Node) tp.getLastPathComponent();
				getInfoViewPanel().updateInfo(node);
			  } else {
				getInfoViewPanel().updateInfo(null);
			  }
			  SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
				  getScrollPaneInfo().getVerticalScrollBar().setValue(0);
				}
			  });
			}
		  });
	}
	return tree;
  }

  public void update() {
	SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
		DefaultTreeModel currentModel = (DefaultTreeModel) getTree().getModel();
		Node currentRoot = (Node) currentModel.getRoot();
		List<String> expandedNodes = new ArrayList<String>();
		fetchExpandedNodes(currentRoot, expandedNodes);

		Collection<Context> contexts =
			Application.getControllerManager().getContexts();

		Node root = new Node();
		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		for (Context context : contexts) {
		  Node node = new Node(context);
		  root.add(node);
		  addChild(context, node);
		}

		upateTree(treeModel, expandedNodes);
	  }
	});
  }

  private void addChild(Context parent, Node parentNode) {
	Collection<Controller> list = parent.getAll();
	if (list.size() > 0) {
	  for (Controller child : list) {
		if (child.isRoot()) {
		  Node childNode = new Node(child);
		  parentNode.add(childNode);
		  addChild(child, childNode);
		}
	  }
	}
  }

  private void addChild(Controller parent, Node parentNode) {
	Collection<Controller> list = parent.getChildren();
	if (list.size() > 0) {
	  for (Controller child : list) {
		Controller c = (Controller) child;
		Node childNode = new Node(child);
		parentNode.add(childNode);
		addChild(c, childNode);
	  }
	}
  }

  private void fetchExpandedNodes(Node node, List<String> expandedNodes) {
	if (node.isRoot() == false && node.isExpanded()) {
	  Object userObject = node.getUserObject();
	  if (userObject instanceof Controller) {
		Controller controller = (Controller) userObject;
		expandedNodes.add(controller.getControllerId());
	  } else if (userObject instanceof Context) {
		Context context = (Context) userObject;
		expandedNodes.add(context.getId());
	  }
	}

	for (int i = 0; i < node.getChildCount(); i++) {
	  Node cn = (Node) node.getChildAt(i);
	  fetchExpandedNodes(cn, expandedNodes);
	}

  }

  private void upateTree(DefaultTreeModel newModel, List<String> expandedNodes) {
	List<TreePath> pathsToExpand = new ArrayList<TreePath>();
	Node root = (Node) newModel.getRoot();
	addToPathList(root, expandedNodes, pathsToExpand);

	getTree().setModel(newModel);

	for (TreePath tp : pathsToExpand) {
	  getTree().expandPath(tp);
	}
  }

  private void addToPathList(Node node, List<String> expandedNodes,
							 List<TreePath> pathsToExpand) {

	if (node.isRoot() && expandedNodes.size() > 0) {
	  pathsToExpand.add(new TreePath(node.getPath()));
	}

	if (node.isRoot() == false) {
	  Object userObject = node.getUserObject();
	  if (userObject instanceof Controller) {
		Controller controller = (Controller) userObject;
		String id = controller.getControllerId();
		if (expandedNodes.contains(id)) {
		  pathsToExpand.add(new TreePath(node.getPath()));
		}
	  } else if (userObject instanceof Context) {
		Context context = (Context) userObject;
		String id = context.getId();
		if (expandedNodes.contains(id)) {
		  pathsToExpand.add(new TreePath(node.getPath()));
		}
	  }
	}

	for (int i = 0; i < node.getChildCount(); i++) {
	  Node cn = (Node) node.getChildAt(i);
	  addToPathList(cn, expandedNodes, pathsToExpand);
	}

  }

  private class TreeRenderer extends JPanel implements TreeCellRenderer{
	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private Border selectedBorder;
	private Border unselectedBorder;

	public TreeRenderer() {
	  selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 3);

	  Border out = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE);
	  Border in = BorderFactory.createLineBorder(Color.BLACK);
	  unselectedBorder = BorderFactory.createCompoundBorder(out, in);

	  setLayout(new MigLayout("ins 3 3 3 3", "grow", "grow"));
	  setOpaque(false);
	  panel.setLayout(new MigLayout("wrap 1", "", "[]1[]"));
	  panel.add(label1);
	  panel.add(label2);
	  add(panel, "grow");
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
												  boolean sel,
												  boolean expanded,
												  boolean leaf, int row,
												  boolean hasFocus) {
	  if (sel) {
		panel.setBorder(selectedBorder);
	  } else {
		panel.setBorder(unselectedBorder);
	  }
	  Node node = (Node) value;
	  Object userObject = node.getUserObject();
	  if (userObject != null) {
		if (userObject instanceof Context) {
		  Context context = (Context) userObject;
		  label1.setText("Context");
		  label2.setText("Name: " + context.getName());
		  panel.setBackground(new Color(250, 230, 170));
		} else if (userObject instanceof Controller) {
		  Controller controller = (Controller) userObject;
		  Class<?> clss = controller.getClass();
		  label1.setText("Package: " + clss.getPackage().getName());
		  label2.setText("Class: " + clss.getSimpleName());
		  panel.setBackground(new Color(190, 220, 250));
		} else {
		  label1.setText(userObject.toString());
		  label2.setText("");
		  panel.setBackground(Color.WHITE);
		}
	  } else {
		label1.setText("");
		label2.setText("");
		panel.setBackground(Color.WHITE);
	  }
	  return this;
	}

  }

}
