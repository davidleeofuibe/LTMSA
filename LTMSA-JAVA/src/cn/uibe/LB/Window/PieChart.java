package cn.uibe.LB.Window;


import java.awt.Font;
import java.awt.RenderingHints;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.Legend;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.DefaultPieDataset;


public class PieChart {
	public  JFreeChart createPieChart() {  
        // ͨ�����̴���3D��ͼ  
        JFreeChart pieChart = ChartFactory.createPieChart("Sentiment Analysis",  
                createDataset(), true, true, false);  
        pieChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);  
        // �õ�3D��ͼ��plot����  
        PiePlot piePlot = (PiePlot) pieChart.getPlot();  
        // ����͸����  
        piePlot.setForegroundAlpha(0.5f);  
        piePlot.setLabelFont((new Font("����", Font.PLAIN, 12)));  
        // ���ñ�������  
        pieChart.getTitle().setFont(new Font("����", Font.BOLD, 20));  
        // ����ͼ���������  
       Legend legend = pieChart.getLegend();
     //�޸�ͼ��������     legend.setItemFont(new Font("����", Font.BOLD, 14));  
        ChartFrame frame = new ChartFrame("TestPieChart", pieChart);  
        frame.pack();  
        RefineryUtilities.centerFrameOnScreen(frame);  
       // frame.setVisible(true);  
       return pieChart;
    }  
	
	public  DefaultPieDataset createDataset() {  
        DefaultPieDataset pieDataset = new DefaultPieDataset();  
        pieDataset.setValue("Positive", 2.80);  
        pieDataset.setValue("Negative", 3.63);  
        pieDataset.setValue("Neutral", 2.84);  
        return pieDataset;  
    } 
}
