import SupavisioJ.MainFrame.MainFrame;
import ij.plugin.*;
import javax.swing.SwingUtilities;


public class Supavisio_J implements PlugIn {      

  public void run(String arg) {	
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
          MainFrame supavisioFrame = new MainFrame();
          supavisioFrame.setVisible(true);	
      }
    });
  }

}