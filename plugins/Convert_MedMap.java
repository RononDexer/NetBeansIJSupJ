import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import java.text.DecimalFormat;

public class Convert_MedMap implements PlugIn {

	public void run(String arg) {
		for (int i=1;i<184;i++){
                    DecimalFormat myFormat = new java.text.DecimalFormat("000");
                    try{
                        IJ.run("Text Image... ", "open=E:\\Manips\\2011\\AIFIRA\\05-Mai\\configSTIM"+myFormat.format(i)+".medMap.txt");
                        ImagePlus imp = IJ.getImage();
                        //calibration mai 2011
                        //mass=1330.75-channel*2.925
                        IJ.run(imp, "Multiply...", "value=-2.925");
                        IJ.run(imp, "Add...", "value=1330.75");
                        IJ.run(imp, "Brightness/Contrast...", "");
                        IJ.run(imp, "Enhance Contrast", "saturated=0.35");
                        IJ.saveAs(imp, "Tiff", "E:\\Manips\\2011\\AIFIRA\\05-Mai\\configSTIM"+myFormat.format(i)+".QMap.tif");
                    }
                    catch (Exception e){
                     }
                }
        }
}

