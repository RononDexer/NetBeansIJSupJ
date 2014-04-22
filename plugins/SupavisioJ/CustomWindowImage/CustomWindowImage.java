package SupavisioJ.CustomWindowImage;

import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomWindowImage extends ImageWindow implements ActionListener {
    
        private Button buttonCalc;
        private ImageGenerated selectedImage;
       
        public CustomWindowImage(ImagePlus imp, ImageGenerated selectedImage) {
            super(imp);
            this.selectedImage = selectedImage;
            setLayout(new FlowLayout());
            addPanel();
            }


    
        private void addPanel() {
            Panel panel = new Panel();
            panel.setLayout(new GridLayout(2,1));
            buttonCalc = new Button(" Calculate spectra");
            buttonCalc.addActionListener(this);
            panel.add(buttonCalc);
            add(panel);
            pack();
            
        }
      
        public void actionPerformed(ActionEvent e) {
            Object b = e.getSource();
            if (b==buttonCalc) {
                Spectra spectraCalc=selectedImage.generateSpectraFromRoi();
                spectraCalc.plotSpectra("Spectre calculé", "Spectre calculé").showVisible();
            }
            ImageCanvas ic = imp.getCanvas();
            if (ic!=null)
                ic.requestFocus();
        }
        
    } // CustomWindow inner class