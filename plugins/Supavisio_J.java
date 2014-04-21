import SupavisioJ.DataFilePIXE.DataFilePIXE;
import javax.swing.JFileChooser;
import SupavisioJ.ConvertListFiles.FrameC.FrameC;
import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.Spectra.Spectra;
import SupavisioJ.ImageGenerated.ImageGenerated;
import ij.*;
import ij.plugin.*;
import java.io.*;
import javax.swing.SwingUtilities;
import lib.XYPlotSp;


public class Supavisio_J implements PlugIn {
      

  public void run(String arg) {	
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
	DataFilePIXE filePx=selectFiles();
	ADC adc1 = filePx.open(); 
        Spectra spectra1= new Spectra(adc1);
        spectra1.plotSpectra("Essai","essai").showVisible();
        IJ.log("création de l'image");
        float[][] fl = new float[1][2];
        fl[0][0]=255;
        fl[0][1]=355;
        ImageGenerated[] img= spectra1.generatePicture(fl);
        IJ.log("affichage de l'image");
        img[0].show("essai");
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