package org.japura.debug;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.japura.gui.TitlePanel;
import org.japura.util.info.InfoNode;

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
public class InfoNodeUtil{

  public static TitlePanel buildTitlePanel(String title,
										   Collection<InfoNode> nodes,
										   String... excludeIdentifiers) {
	TitlePanel titlePanel = new TitlePanel(title);
	titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));

	List<InfoNode> leafs = new ArrayList<InfoNode>();
	List<InfoNode> groups = new ArrayList<InfoNode>();
	for (InfoNode node : nodes) {
	  boolean ignore = false;
	  if (node.getIdentifier() != null) {
		for (String identifier : excludeIdentifiers) {
		  if (identifier.equals(node.getIdentifier())) {
			ignore = true;
			break;
		  }
		}
	  }
	  if (ignore) {
		continue;
	  }
	  if (node.isLeaf()) {
		leafs.add(node);
	  } else {
		groups.add(node);
	  }
	}

	JPanel container = new JPanel();
	container.setBackground(Color.WHITE);
	container.setLayout(new MigLayout("ins 10 10 10 10,wrap 2",
		"[align right][grow]"));
	for (InfoNode node : leafs) {
	  JLabel label = new JLabel();
	  label.setText(node.getName() + ":");
	  JTextArea textArea = new JTextArea();
	  textArea.setOpaque(false);
	  textArea.setText(node.getValue());
	  textArea.setWrapStyleWord(true);
	  textArea.setLineWrap(true);
	  textArea.setPreferredSize(new Dimension(10, 10));
	  container.add(label);
	  container.add(textArea, "wmin 10, grow x");
	}

	for (InfoNode node : groups) {
	  TitlePanel panel =
		  buildTitlePanel(node.getName(), node.getChildren(),
			  excludeIdentifiers);
	  container.add(panel, "span x, grow x");
	}

	titlePanel.add(container);
	return titlePanel;
  }

}
