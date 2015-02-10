package org.japura.debug.controllers.views.context;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.japura.controller.Context;
import org.japura.controller.Controller;
import org.japura.controller.Group;

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
class ContextComponent extends JPanel{

  private static final long serialVersionUID = 2L;

  public ContextComponent(Context context, boolean withScreenshot) {
	setBackground(new Color(250, 230, 170));
	Border out = BorderFactory.createLineBorder(Color.BLACK, 2);
	Border in = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	setBorder(BorderFactory.createCompoundBorder(out, in));

	setLayout(new BorderLayout(0, 0));

	JPanel panel = new JPanel();
	panel.setOpaque(false);
	panel.setLayout(new GridLayout(3, 1));
	panel.add(new JLabel("CONTEXT"));
	panel.add(new JLabel("Name: " + context.getName()));
	panel.add(new JLabel("Id: " + context.getId()));

	HashSet<Group> groups = new HashSet<Group>();
	for (Controller controller : context.getAll()) {
	  groups.add(controller.getGroup());
	}

	JPanel panel2 = new JPanel();
	panel2.setOpaque(false);
	panel2.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = GridBagConstraints.RELATIVE;
	gbc.weightx = 1;
	gbc.insets = new Insets(15, 0, 0, 0);
	gbc.anchor = GridBagConstraints.WEST;
	for (Group group : groups) {
	  panel2.add(new GroupComponent(group, withScreenshot), gbc);
	}

	add(panel, BorderLayout.NORTH);
	add(panel2, BorderLayout.CENTER);
  }

}
