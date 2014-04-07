

import ConvertListFiles.FrameC.FrameC;
import ConvertListFiles.FrameE.FrameE;
import ij.plugin.*;
import javax.swing.SwingUtilities;



public class Convert_List_Files implements PlugIn {
   
    public void run(String arg) {
          
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
			FrameC frame_C = new FrameC();
			frame_C.setVisible(true);
                        //FrameE frame_E = new FrameE();
                        //frame_E.setLocation(400,200);
			//frame_E.setVisible(true);
			
			}
		});
	}
}