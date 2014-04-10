import SupavisioJ.DataFilePIXE.DataFilePIXE;
import javax.swing.JFileChooser;
import SupavisioJ.ConvertListFiles.FrameC.FrameC;
import ij.*;
import ij.plugin.*;
import java.io.*;
import javax.swing.SwingUtilities;


public class Supavisio_J implements PlugIn {
      

  public void run(String arg) {	
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        IJ.log("Veuillez ouvrir un fichier pixe");
	DataFilePIXE filePx=selectFiles();
	filePx.open();
	IJ.log("Lecture terminee");
        IJ.log("Veuillez ouvrir un fichier lst");
	FrameC frame_C = new FrameC();
	frame_C.setVisible(true);
        IJ.log("Lecture terminée");
      }
    });
  }
  
  public DataFilePIXE selectFiles(){
    DataFilePIXE filePx=null;
    try{
      JFileChooser jF = new JFileChooser();//création dun nouveau filechosser
      jF.setApproveButtonText("OK"); //intitulé du bouton
      jF.setMultiSelectionEnabled(false);
      
      jF.showOpenDialog(null); //affiche la boite de dialogue
      
      File selectedFile = jF.getSelectedFile(); 
      filePx=new DataFilePIXE(selectedFile.getAbsolutePath());
    }
    catch (Exception e){
      IJ.log("Erreur");
    }
    IJ.log("Fichier sélectionné à : ");
    IJ.log(filePx.getPath());
    return filePx;
  }

}