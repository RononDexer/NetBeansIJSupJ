package SupavisioJ.resources.lib;

import ij.IJ;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleEdge;

public class CustomChartPanel extends ChartPanel implements MouseListener { 

    private XYPlotSp parentXYPlotSp; 
    private JFreeChart chart = null; 

    /** 
    * constructor 
    * @param chart 
    */ 
    public CustomChartPanel(JFreeChart chart, XYPlotSp parentXYPlotSp) { 
      super(chart); 
      this.chart = chart;  
      this.parentXYPlotSp=parentXYPlotSp;
    } 

    private boolean isACheckBoxSelected(){
        return !parentXYPlotSp.getCheckBoxSelected().isEmpty();
    }
    
    /** 
    * override the mouse pressed method 
    * Handles a 'mouse pressed' event. 
    * @param mEvt information about the event. 
    */ 
    public void mousePressed(MouseEvent mEvt) { 
      if (isACheckBoxSelected()) { 
	setMouseZoomable(false,true); //zoom desactivation
	super.mousePressed(mEvt);//call to the initial method 
	float startValueX = getPointInChart(mEvt,false); //to have correspondance with actual values
	setMouseZoomable(true,false); //zomm activation
        ArrayList<JCheckBox> checkBoxsSelected = parentXYPlotSp.getCheckBoxSelected();
        for (JCheckBox checkBox : checkBoxsSelected) {
            JTextField minField = parentXYPlotSp.getField(checkBox,"Min");
            minField.setText(String.valueOf(startValueX));
        }
      } 
      else{ 
	super.mousePressed(mEvt); //call to the initial method 
      } 
    } 

    /** 
    * override the mouse released method 
    * Handles a 'mouse released' event. 
    * @param mEvt information about the event. 
    */ 
    public void mouseReleased(MouseEvent mEvt) { 
      if (isACheckBoxSelected()) {  
	setMouseZoomable(false,true); 
	super.mouseReleased(mEvt); 
        float endValueX = getPointInChart(mEvt,true);
	setMouseZoomable(true,false); 
        ArrayList<JCheckBox> checkBoxsSelected = parentXYPlotSp.getCheckBoxSelected();
        for (JCheckBox checkBox : checkBoxsSelected) {
            JTextField minField = parentXYPlotSp.getField(checkBox,"Min");
            float minVal = Float.valueOf(minField.getText());
            if (endValueX>minVal){
                JTextField maxField = parentXYPlotSp.getField(checkBox,"Max");
                maxField.setText(String.valueOf(endValueX));
            }
        }
      } 
      else { 
	super.mouseReleased(mEvt); 
      } 
    } 

    /** 
    * Receives chart x,y axis. 
    * @param mEvt mouse event. 
    */ 
    public float getPointInChart(MouseEvent mEvt, boolean rightIncluded) { 
      Insets insets = getInsets(); 
      int mouseX = (int) ((mEvt.getX() - insets.left) / this.getScaleX()); 
      int mouseY = (int) ((mEvt.getY() - insets.top) / this.getScaleY()); 
      IJ.log("x = " + mouseX + ", y = " + mouseY); 

      Point2D pointOfInterest = this.translateScreenToJava2D(new Point(mouseX, mouseY)); 
      XYPlot plot = (XYPlot) chart.getPlot(); 
      ChartRenderingInfo info = this.getChartRenderingInfo(); 
      Rectangle2D dataArea = info.getPlotInfo().getDataArea(); 

      ValueAxis domainAxis = plot.getDomainAxis(); 
      RectangleEdge domainAxisEdge = plot.getDomainAxisEdge(); 
      ValueAxis rangeAxis = plot.getRangeAxis(); 
      RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge(); 

      double chartX = domainAxis.java2DToValue(pointOfInterest.getX(), dataArea, domainAxisEdge); 
      double chartY = rangeAxis.java2DToValue(pointOfInterest.getY(), dataArea, rangeAxisEdge); 
      //here we want only chartX
      float valueX=parentXYPlotSp.getRealXValue(chartX,rightIncluded);
      return valueX;
    } 

}