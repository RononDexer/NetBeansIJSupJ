import SupavisioJ.MainFrame.MainFrame;
import ij.plugin.*;
import javax.swing.SwingUtilities;

/**
 * Supavisio_J is the first class run by ImageJ when the user launch the SupavisioJ plugin.
 */
public class Supavisio_J implements PlugIn {
  /**
  * function run by ImageJ when the user launch the SupavisioJ plugin.
  * This function will open a new window using the MainFrame class
  */
  public void run(String arg) {	
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
          MainFrame supavisioFrame = new MainFrame();
          supavisioFrame.setVisible(true);	
      }
    });
  }

}