package org.japura.debug.tasks.executions;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <P>
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
 */
class LineVertical extends JComponent{
  // TODO adicionar hora em que iniciou/terminou na task

  private static final long serialVersionUID = 1L;
  private Stroke arrowStroke = new BasicStroke(1f);
  private Stroke stroke = new BasicStroke(1f);

  private int gap = 20;
  private int h = 20;

  public Stroke getStroke() {
	return stroke;
  }

  public void setStroke(Stroke stroke) {
	this.stroke = stroke;
  }

  @Override
  public Dimension getPreferredSize() {
	if (super.isPreferredSizeSet()) {
	  return super.getPreferredSize();
	}

	return new Dimension(gap + 1, h);
  }

  @Override
  public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g.create();

	int x = gap;

	g2.setStroke(getStroke());
	if (h > 0) {
	  g2.drawLine(x, 0, x, getHeight());
	}

  }

  public static void main(String das[]) {
	JFrame f = new JFrame();

	JPanel panel = new JPanel();
	panel.setLayout(new GridLayout(1, 1));

	LineVertical l2 = new LineVertical();

	panel.add(l2);

	f.add(panel);
	f.pack();
	f.setLocationRelativeTo(null);
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}