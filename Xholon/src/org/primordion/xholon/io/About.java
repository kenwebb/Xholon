/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.io;

import com.google.gwt.user.client.Window;

//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;

import org.primordion.xholon.base.Xholon;

/**
 * Display brief information about Xholon and the current Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on November 29, 2006)
 */
public class About extends Xholon implements IAbout {

	/*
	 * @see org.primordion.xholon.io.IAbout#about(java.lang.String, java.lang.String, int, int)
	 */
	public void about(String title, String text, int width, int height)
	{
	  Window.alert(text);
		/*final JFrame aboutFrame = new JFrame(title);
		JPanel aboutPanel = new JPanel();
		aboutPanel.setBackground(Color.WHITE);
		JTextArea aboutText = new JTextArea();
		aboutText.setEditable(false);
		aboutText.setLineWrap(true);
		aboutText.setSize(width, height);
		aboutText.setText(text);
		JButton ok = new JButton("OK");
		ok.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aboutFrame.dispose();
				}
			}
		);
		aboutPanel.add(aboutText);
		aboutPanel.add(ok);
		aboutFrame.getContentPane().add(aboutPanel);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aboutFrame.setSize(width+10, height+10);
		aboutFrame.setVisible(true);*/
	}
}
