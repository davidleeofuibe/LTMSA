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
		 // �������
		String[][] rowData=null;
			String[] columnNames = { "����", "����" }; // ����			
			int age;
			int count = 0;
			for(int i=0;i<rs.size();i++){ 
				//������ѯ���
				rowData[count][0] = rs.get(i)[0]; // ��ʼ����������
				rowData[count][1] = rs.get(i)[1];
				count++;
			}
			JTable table = new JTable(rowData, columnNames); // ʵ�������
			// table.getColumn("����").setMaxWidth(25); //�����п�
			return table;


	}

}
