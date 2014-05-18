/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupavisioJ.FrameConfigLang;

import SupavisioJ.MainFrame.MainFrame;
import ij.IJ;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author vivien
 */
public class FrameConfigLang extends javax.swing.JFrame {
    
    private MainFrame parentWindow;
    private String languageName;
    /**
     * Creates new form FrameConfigLang
     */
    public FrameConfigLang(MainFrame parentWindow, String languageName) {
        this.parentWindow=parentWindow;
        this.languageName=languageName;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxLang = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Please choose your language :");

        jComboBoxLang.setModel(new javax.swing.DefaultComboBoxModel(getLanguages()));
        if (languageName!=null){
            String[] availableLang=getLanguages();
            for (int i=0;i<availableLang.length;i++){
                if(availableLang[i].equals(languageName)){
                    jComboBoxLang.setSelectedIndex(i);
                }
            }
        }
        jComboBoxLang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxLang, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxLangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLangActionPerformed
        int selection = jComboBoxLang.getSelectedIndex();
        if (selection>=0){
            String language = getLanguages()[selection];
            String nameFile = parentWindow.getNameFileLang(language);
            try{
                BufferedWriter buff = new BufferedWriter(new FileWriter("plugins/SupavisioJ/resources/language/default",false));//Création du fichier si non existant et append=false si existant
                buff.write(nameFile);
                buff.write("\n");
                buff.flush();   // buffer is released
                buff.close();   // buffer & stream are closed. 
                IJ.log("Please close and reopen SupavisioJ to apply the new settings");
            }
            catch(IOException e){}
        }
    }//GEN-LAST:event_jComboBoxLangActionPerformed

    public String[] getLanguages(){
        return parentWindow.getLanguages();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBoxLang;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
