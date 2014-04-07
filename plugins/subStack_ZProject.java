/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author deves
 */

import ij.plugin.*;
import javax.swing.SwingUtilities;
import subStackZProject.FrameZ;

public class subStack_ZProject implements PlugIn{
public void run(String arg) {

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
			FrameZ frame_Z = new FrameZ();
			frame_Z.setVisible(true);
                        

			}
		});
	}
}
