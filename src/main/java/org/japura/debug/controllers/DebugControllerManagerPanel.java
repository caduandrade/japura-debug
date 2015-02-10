package org.japura.debug.controllers;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * 
 * Copyright (C) 2011-2013 Carlos Eduardo Leite de Andrade
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
public class DebugControllerManagerPanel extends JPanel{

  private static final long serialVersionUID = 3L;

  private JPanel buttonsPanel;
  private JRadioButton treeButton;
  private JRadioButton listButton;
  private JRadioButton contextButton;

  private JPanel viewContainer;

  public DebugControllerManagerPanel() {
	setLayout(new BorderLayout());

	setLayout(new BorderLayout());
	add(getButtonsPanel(), BorderLayout.NORTH);

	add(getViewContainer(), BorderLayout.CENTER);
  }

  public JPanel getViewContainer() {
	if (viewContainer == null) {
	  viewContainer = new JPanel();
	  viewContainer.setLayout(new BorderLayout());
	}
	return viewContainer;
  }

  private JPanel getButtonsPanel() {
	if (buttonsPanel == null) {
	  buttonsPanel = new JPanel();
	  buttonsPanel.setLayout(new GridBagLayout());
	  GridBagConstraints gbc = new GridBagConstraints();
	  gbc.gridy = 0;
	  gbc.weightx = 0;

	  gbc.gridx = 0;
	  buttonsPanel.add(new JLabel("Views:"), gbc);

	  gbc.gridx = 1;
	  buttonsPanel.add(getTreeButton(), gbc);

	  gbc.gridx = 2;
	  buttonsPanel.add(getListButton(), gbc);

	  gbc.gridx = 3;
	  gbc.weightx = 1;
	  gbc.anchor = GridBagConstraints.WEST;
	  buttonsPanel.add(getContextButton(), gbc);

	  ButtonGroup bg = new ButtonGroup();
	  bg.add(getListButton());
	  bg.add(getTreeButton());
	  bg.add(getContextButton());
	}
	return buttonsPanel;
  }

  public JRadioButton getListButton() {
	if (listButton == null) {
	  listButton = new JRadioButton("List");
	}
	return listButton;
  }

  public JRadioButton getTreeButton() {
	if (treeButton == null) {
	  treeButton = new JRadioButton("Tree");
	  treeButton.setSelected(true);
	}
	return treeButton;
  }

  public JRadioButton getContextButton() {
	if (contextButton == null) {
	  contextButton = new JRadioButton("Contexts/Groups");
	  contextButton.setSelected(true);
	}
	return contextButton;
  }

  public void updateViewComponent(JComponent component) {
	getViewContainer().removeAll();
	getViewContainer().add(component);
	getViewContainer().revalidate();
	repaint();
  }

}
