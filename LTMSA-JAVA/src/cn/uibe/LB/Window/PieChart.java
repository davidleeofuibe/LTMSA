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
        // 通过工程创建3D饼图  
        JFreeChart pieChart = ChartFactory.createPieChart("Sentiment Analysis",  
                createDataset(), true, true, false);  
        pieChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,  
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);  
        // 得到3D饼图的plot对象  
        PiePlot piePlot = (PiePlot) pieChart.getPlot();  
        // 设置透明度  
        piePlot.setForegroundAlpha(0.5f);  
        piePlot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));  
        // 设置标题字体  
        pieChart.getTitle().setFont(new Font("隶书", Font.BOLD, 20));  
        // 设置图例类别字体  
       Legend legend = pieChart.getLegend();
     //修改图例的字体     legend.setItemFont(new Font("宋体", Font.BOLD, 14));  
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
