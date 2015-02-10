package org.japura.debug.controllers.views.tree;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import net.miginfocom.swing.MigLayout;

import org.japura.Application;
import org.japura.InfoNodeIdentifiers;
import org.japura.controller.Controller;
import org.japura.controller.Group;
import org.japura.controller.messages.ScreenShotMessage;
import org.japura.debug.InfoNodeUtil;
import org.japura.gui.TitlePanel;
import org.japura.message.Subscriber;
import org.japura.message.SubscriberFilter;
import org.japura.util.info.InfoProvider;

public class InfoViewPanel extends JPanel{

  private static final long serialVersionUID = 1L;

  public InfoViewPanel() {
	setBackground(Color.lightGray);
	setLayout(new MigLayout("ins 20 20 20 20, gap 15 15 15 15, wrap 1", "grow",
		""));
  }

  public void clear() {
	updateInfo(null);
  }

  public void updateInfo(DefaultMutableTreeNode node) {
	removeAll();
	if (node != null) {
	  Object userObject = node.getUserObject();

	  if (userObject instanceof InfoProvider) {
		InfoProvider infoProvider = (InfoProvider) userObject;
		String name = userObject.getClass().getSimpleName();
		add(InfoNodeUtil.buildTitlePanel(name, infoProvider.getInfoNodes()),
			"grow x");
	  }

	  if (userObject instanceof Controller) {
		Controller controller = (Controller) userObject;
		JPanel screenshot = buildScreenshot(controller);
		TitlePanel titlePanel = new TitlePanel("Screenshot");
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.add(screenshot);
		add(titlePanel, "grow x");

		Group group = controller.getGroup();
		add(InfoNodeUtil.buildTitlePanel("Group", group.getInfoNodes(),
			InfoNodeIdentifiers.GROUP_ROOT_CONTROLLER.name()), "grow x");
	  }

	}
	revalidate();
	if (getParent() != null) {
	  getParent().repaint();
	}
  }

  private JPanel buildScreenshot(Controller controller) {
	ScreenShotMessage ssm = new ScreenShotMessage();

	ssm.addSubscriberFilter(new Filter(controller));
	Application.getMessageManager().publish(true, ssm, this);

	Image image = ssm.getScreenShot();

	int s = 400;

	if (image.getWidth(null) > s) {
	  image = image.getScaledInstance(s, -1, Image.SCALE_SMOOTH);
	}

	if (image.getHeight(null) > s) {
	  image = image.getScaledInstance(-1, s, Image.SCALE_SMOOTH);
	}

	JLabel preview = new JLabel();
	preview.setIcon(new ImageIcon(image));
	preview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	JPanel p = new JPanel();
	p.setLayout(new MigLayout("", "grow, align center", "grow, align center"));
	p.add(preview);
	p.setBackground(Color.WHITE);
	return p;
  }

  private static class Filter implements SubscriberFilter{
	private Controller controller;

	public Filter(Controller controller) {
	  this.controller = controller;
	}

	@Override
	public boolean accepts(Subscriber subscriber) {
	  return this.controller.equals(subscriber);
	}
  }
}
