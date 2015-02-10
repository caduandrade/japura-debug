package org.japura.debug.controllers.views.context;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.japura.Application;
import org.japura.controller.Context;

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
public class ContextViewPanel extends JPanel{

  private static final long serialVersionUID = 2L;

  private JPanel containerPanel;

  protected ContextViewPanel() {
	containerPanel = new JPanel();
	containerPanel.setLayout(new GridBagLayout());
	containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	containerPanel.setBackground(Color.WHITE);

	setLayout(new BorderLayout());
	JScrollPane sp = new JScrollPane(containerPanel);
	add(sp);
  }

  protected void updateView(final boolean withScreenshot) {
	SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		containerPanel.removeAll();
		Collection<Context> contexts =
			Application.getControllerManager().getContexts();
		for (Context context : contexts) {
		  containerPanel
			  .add(new ContextComponent(context, withScreenshot), gbc);
		  gbc.insets = new Insets(20, 0, 0, 0);
		}
		containerPanel.revalidate();
		repaint();
	  }
	});

  }

}
