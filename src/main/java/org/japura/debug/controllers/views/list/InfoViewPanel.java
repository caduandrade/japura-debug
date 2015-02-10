package org.japura.debug.controllers.views.list;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

import org.japura.debug.InfoNodeUtil;
import org.japura.util.info.InfoProvider;

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

  public void action(DefaultMutableTreeNode node) {
	removeAll();
	if (node != null) {
	  Object userObject = node.getUserObject();

	  if (userObject instanceof InfoProvider) {
		InfoProvider infoProvider = (InfoProvider) userObject;
		String name = userObject.getClass().getSimpleName();
		add(InfoNodeUtil.buildTitlePanel(name, infoProvider.getInfoNodes()),
			"grow x");
	  }

	}
	revalidate();
	if (getParent() != null) {
	  getParent().repaint();
	}
  }
}
