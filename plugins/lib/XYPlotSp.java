package lib;
import java.awt.Color;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JCheckBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import ij.*;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.jfree.chart.ChartUtilities;

public class XYPlotSp extends JFrame {
    
    private String titleGraph;
    
    public XYPlotSp(final String titleWindow,final String titleGraph2, double[] energiesX, double[] dataY) {
        super(titleWindow);
        this.titleGraph=titleGraph2;
        //create the dataset
        final XYDataset dataset = createDataset(energiesX,dataY);
        //create the chart
        final JFreeChart chart = createChart(dataset,titleGraph2);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        initComponents(chartPanel);
    }
    /**
     * Creates the dataset.
     * 
     * @return a sample dataset.
     */
    private static XYDataset createDataset(double[] energiesX,double[] dataY) {
        final XYSeries series1 = new XYSeries("Spectre 1");
        for(int i=0;i<energiesX.length;i++){
            series1.add(energiesX[i],dataY[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        return dataset;
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private static JFreeChart createChart(final XYDataset dataset, final String titleGraph2) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            titleGraph2,      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // plot orientation (pretty obvious this one)
            false,                    // if true show the legeng (useful for several lignes)
            true,                     // tooltips
            false                     // URLs
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        //final StandardLegend legend = (StandardLegend) chart.getLegend();
        //legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        //code for points and no lines on the chart
        //final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesLinesVisible(0, false);
        //renderer.setSeriesShapesVisible(1, false);
        //plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
    }
    
    public void showVisible() {
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
    
    public void windowClosing(final WindowEvent evt){
        if(evt.getWindow() == this){
            dispose();
        }
    }
    
    private void initComponents(ChartPanel chartPanel) {
        for(int i=0;i<3;i++){
            JComponent[] buttonsToAdd= new JComponent[3];
            JCheckBox jCheckBox1 = new JCheckBox();
            jCheckBox1.setText("NA");
            buttonsToAdd[0] = jCheckBox1;
            buttonsToAdd[1] = new JTextField();
            buttonsToAdd[2] = new JTextField();
            vectButtonsSupp.add(buttonsToAdd);
        }
        jButton1 = new javax.swing.JButton();
        jButton1.setText("Plus ...");
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        //Layout horizontal
        ParallelGroup paralGroupHor=layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup grpChartPanel=layout.createSequentialGroup();
        grpChartPanel.addComponent(chartPanel);
        paralGroupHor.addGroup(grpChartPanel);
        SequentialGroup grp1 = layout.createSequentialGroup();
        grp1.addContainerGap();
        for (int i=0; i<3;i++){
            JComponent[] tablJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tablJComp[0];
            JTextField textFieldCurrent1= (JTextField) tablJComp[1];
            JTextField textFieldCurrent2= (JTextField) tablJComp[2];
            grp1.addComponent(checkBoxCurrent, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE);
            grp1.addPreferredGap(ComponentPlacement.RELATED);
            grp1.addComponent(textFieldCurrent1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);
            grp1.addPreferredGap(ComponentPlacement.RELATED);
            grp1.addComponent(textFieldCurrent2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);

            if (i!=3){
                grp1.addGap(18, 18, 18);
            }
            else{
                grp1.addContainerGap(25, Short.MAX_VALUE);
            }
        }
        paralGroupHor.addGroup(grp1);
        
        SequentialGroup grpButtonMore = layout.createSequentialGroup();
        grpButtonMore.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        grpButtonMore.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE);
        grpButtonMore.addGap(299, 299, 299);
        paralGroupHor.addGroup(GroupLayout.Alignment.TRAILING, grpButtonMore);
        
        //Layout vertical 
        
        ParallelGroup grpIntChannel = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        for (int i=0;i<3;i++){
            JComponent[] tablJComp = (JComponent[]) vectButtonsSupp.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tablJComp[0];
            JTextField textFieldCurrent1= (JTextField) tablJComp[1];
            JTextField textFieldCurrent2= (JTextField) tablJComp[2];
            grpIntChannel.addComponent(checkBoxCurrent);
            grpIntChannel.addComponent(textFieldCurrent1,GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE);
            grpIntChannel.addComponent(textFieldCurrent2,GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE);
        }
       
        ParallelGroup paralGroup2Vert=layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        SequentialGroup grpAll = layout.createSequentialGroup();
        ParallelGroup gpChart = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        gpChart.addComponent(chartPanel);
        grpAll.addGroup(gpChart);
        grpAll.addContainerGap(10, Short.MAX_VALUE).addGroup(grpIntChannel);
        grpAll.addPreferredGap(ComponentPlacement.RELATED);
        grpAll.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE);
        grpAll.addContainerGap();
      
        paralGroup2Vert.addGroup(GroupLayout.Alignment.TRAILING,grpAll);
        
        layout.setHorizontalGroup(paralGroupHor);
        layout.setVerticalGroup(paralGroup2Vert);
        pack();
    }                 

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO create 3 group of boutons at each evt
        IJ.log("fonctionnalité non codée pour le moment");
        //for(int i=0;i<3;i++){
        //    JComponent[] buttonsToAdd= new JComponent[3];
        //    buttonsToAdd[0]=new JCheckBox();
        //    buttonsToAdd[1] = new JTextField();
        //    buttonsToAdd[2] = new JTextField();
        //    vectButtonsSupp.add(buttonsToAdd);
        //}
        
    }                                        
    // Variables declaration - do not modify 
    private Vector vectButtonsSupp=new Vector();
    private javax.swing.JButton jButton1;
    // End of variables declaration                   
}
