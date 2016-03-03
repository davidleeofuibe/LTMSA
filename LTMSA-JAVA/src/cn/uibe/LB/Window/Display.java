package cn.uibe.LB.Window;

import java.sql.*;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;

public class Display extends JFrame {

//	private static final String[][][][] String = null;

	// implements ActionListener
	public JTable Display(ArrayList<String[]> rs) throws SQLException {
		 // 表格数据
		String[][] rowData=null;
			String[] columnNames = { "语料", "极性" }; // 列名			
			int age;
			int count = 0;
			for(int i=0;i<rs.size();i++){ 
				//遍历查询结果
				rowData[count][0] = rs.get(i)[0]; // 初始化数组内容
				rowData[count][1] = rs.get(i)[1];
				count++;
			}
			JTable table = new JTable(rowData, columnNames); // 实例化表格
			// table.getColumn("年龄").setMaxWidth(25); //设置行宽
			return table;


	}

}
