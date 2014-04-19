package SupavisioJ.CustomWindowImage;

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
    
        private Button button1;
       
        public CustomWindowImage(ImagePlus imp) {
            super(imp);
            setLayout(new FlowLayout());
            addPanel();
        }
    
        private void addPanel() {
            Panel panel = new Panel();
            panel.setLayout(new GridLayout(2,1));
            button1 = new Button(" Caculate spectra");
            button1.addActionListener(this);
            panel.add(button1);
            add(panel);
            pack();
        }
      
        public void actionPerformed(ActionEvent e) {
            Object b = e.getSource();
            if (b==button1) {
                IJ.log("Bouton marche");
            }
            ImageCanvas ic = imp.getCanvas();
            if (ic!=null)
                ic.requestFocus();
        }
        
    } // CustomWindow inner class