/*
 * Copyright (C) 2008-2015 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.gui.actions;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.ujmp.core.Matrix;
import org.ujmp.core.filematrix.FileFormat;
import org.ujmp.core.interfaces.GUIObject;

public class LinkMatrixToFileAction extends AbstractObjectAction {
	private static final long serialVersionUID = -5948577771230452714L;

	public LinkMatrixToFileAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "to File...");
		putValue(Action.SHORT_DESCRIPTION, "link a matrix to a file on disk");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
	}

	public Object call() {
		try {
			File file = null;
			FileFormat fileFormat = null;
			JFileChooser chooser = new JFileChooser();

			for (FileFormat f : FileFormat.values()) {
				chooser.addChoosableFileFilter(f.getFileFilter());
			}

			chooser.setFileFilter(FileFormat.CSV.getFileFilter());
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setDialogTitle("Link");

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				FileFilter filter = chooser.getFileFilter();
				for (FileFormat f : FileFormat.values()) {
					if (filter.equals(f.getFileFilter())) {
						fileFormat = f;
					}
				}

			}

			if (file == null)
				return null;

			Matrix m = Matrix.Factory.linkTo().file(file).asDenseCSV();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
