package org.japura.debug.controllers.views.context;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.japura.Application;
import org.japura.controller.Controller;
import org.japura.controller.Group;
import org.japura.controller.messages.ScreenShotMessage;
import org.japura.message.Subscriber;
import org.japura.message.SubscriberFilter;

/**
 * 
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
 * 
 */
class GroupComponent extends JPanel{

  private static final long serialVersionUID = 1L;

  public GroupComponent(Group group, boolean withScreenshot) {
	setBackground(new Color(200, 220, 190));
	Border out = BorderFactory.createLineBorder(Color.BLACK, 2);
	Border in = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	setBorder(BorderFactory.createCompoundBorder(out, in));

	setLayout(new BorderLayout(0, 5));

	JPanel northPanel = new JPanel();
	northPanel.setOpaque(false);
	northPanel.setLayout(new GridLayout(3, 1));
	northPanel.add(new JLabel("GROUP"));
	northPanel.add(new JLabel("Name: " + group.getName()));
	northPanel.add(new JLabel("Id: " + group.getId()));

	JPanel centerPanel = new JPanel();
	centerPanel.setOpaque(false);
	centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

	Collection<Controller> controllers = group.getAll();
	JPanel controllersPanel = new JPanel();
	controllersPanel.setOpaque(false);
	controllersPanel.setLayout(new GridLayout(controllers.size(), 1, 0, 10));
	for (Controller controller : controllers) {
	  controllersPanel.add(new ControllerComponent(controller));
	}

	centerPanel.add(controllersPanel);

	if (withScreenshot) {
	  ScreenShotMessage ssm = new ScreenShotMessage();
	  Controller rootController = group.getRootController();
	  ssm.addSubscriberFilter(new Filter(rootController));
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
	  centerPanel.add(preview);
	}

	add(northPanel, BorderLayout.NORTH);
	add(centerPanel, BorderLayout.CENTER);
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
