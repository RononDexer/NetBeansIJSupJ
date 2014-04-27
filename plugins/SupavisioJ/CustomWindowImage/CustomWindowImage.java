package SupavisioJ.CustomWindowImage;

import SupavisioJ.ImageGenerated.ImageGenerated;
import SupavisioJ.Spectra.Spectra;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.YesNoCancelDialog;
import ij.io.FileSaver;
import ij.macro.Interpreter;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class CustomWindowImage extends ImageWindow implements ActionListener {
    
    private Button buttonCalc;
    private Button buttonSave;
    private Button buttonSaveAll;
    private ImageGenerated selectedImage;

    public CustomWindowImage(ImagePlus imp, ImageGenerated selectedImage) {
        super(imp);
        this.selectedImage = selectedImage;
        setLayout(new FlowLayout());
        addPanel();
    }

    
    private void addPanel() {
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(3,1));
        buttonCalc = new Button("Calculate spectra");
        buttonCalc.addActionListener(this);
        buttonSave = new Button("Save");
        buttonSave.addActionListener(this);
        buttonSaveAll = new Button("Save All");
        buttonSaveAll.addActionListener(this);
        panel.add(buttonCalc);
        panel.add(buttonSave);
        panel.add(buttonSaveAll);
        add(panel);
        pack();  
    }

    public void actionPerformed(ActionEvent e) {
        Object b = e.getSource();
        if (b==buttonCalc) {
            Spectra spectraCalc=selectedImage.generateSpectraFromRoi();
            spectraCalc.plotSpectra("SupavisioJ", "Spectre calculé "+spectraCalc.getFileName()).showVisible();
        }
        if (b==buttonSave) {
            String directory=selectDirectory();
            if (directory!=null)
                selectedImage.save(directory);
        }
        if (b==buttonSaveAll) {
            //save all images generate from one spectra
            String directory=selectDirectory();
            if (directory!=null)
                selectedImage.saveAll(directory);
        }
        ImageCanvas ic = imp.getCanvas();
        if (ic!=null)
            ic.requestFocus();
    }

    private String selectDirectory(){
        File selectedFile = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showDialog(null,"Choisir dossier");
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
             // if the user accidently click a file, then select the parent directory.
            if (!selectedFile.isDirectory()) {
                selectedFile = selectedFile.getParentFile();
            }
        }
        if (selectedFile!=null)
            return selectedFile.getAbsolutePath()+"/";
        return null;
    }

    public boolean close() { //code taken (in part) from ImageWindow
        boolean isRunning = running || running2;
        running = running2 = false;
        boolean virtual = imp.getStackSize()>1 && imp.getStack().isVirtual();
        if (isRunning) IJ.wait(500);
        if (ij==null || IJ.getApplet()!=null || Interpreter.isBatchMode() || IJ.macroRunning() || virtual)
                imp.changes = false;
        if (imp.changes) {
                String msg;
                String name = imp.getTitle();
                if (name.length()>22)
                        msg = "Save changes to\n" + "\"" + name + "\"?";
                else
                        msg = "Save changes to \"" + name + "\"?";
                YesNoCancelDialog d = new YesNoCancelDialog(this, "ImageJ", msg);
                if (d.cancelPressed())
                        return false;
                else if (d.yesPressed()) {
                        FileSaver fs = new FileSaver(imp);
                        if (!fs.save()) return false;
                }
        }
        closed = true;
        WindowManager.removeWindow(this);
        setVisible(false);
        if (ij!=null && ij.quitting())  // this may help avoid thread deadlocks
                return true;
        dispose();
        return true;
    }
    
} 