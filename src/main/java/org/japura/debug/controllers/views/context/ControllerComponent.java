package org.japura.debug.controllers.views.context;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.japura.controller.Controller;

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
class ControllerComponent extends JPanel{

  private static final long serialVersionUID = 2L;

  public ControllerComponent(Controller controller) {
	setBackground(new Color(190, 220, 250));
	Border out = BorderFactory.createLineBorder(Color.BLACK, 2);
	Border in = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	setBorder(BorderFactory.createCompoundBorder(out, in));
	setLayout(new GridLayout(5, 1));
	if (controller.isRoot()) {
	  add(new JLabel("CONTROLLER (ROOT)"));
	} else {
	  add(new JLabel("CONTROLLER"));
	}
	add(new JLabel("Package: " + controller.getClass().getPackage().getName()));
	add(new JLabel("Class: " + controller.getClass().getSimpleName()));
	add(new JLabel("Name: " + controller.getControllerName()));
	add(new JLabel("Id: " + controller.getControllerId()));
  }

}
