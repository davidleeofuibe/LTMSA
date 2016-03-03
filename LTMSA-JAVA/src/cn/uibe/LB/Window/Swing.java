package cn.uibe.LB.Window;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import cn.uibe.LB.Bean.Algorithm;
import cn.uibe.LB.Bean.Library;
import cn.uibe.LB.Business.Analyzewithalgo;
import cn.uibe.LB.Business.csvFileOperation;
import cn.uibe.LB.Business.jsonFileOperation;

public class Swing {

	JFrame mFrame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	Action a;
	HashMap<String, Algorithm> algoInfo = new HashMap<String, Algorithm>();
	Analyzewithalgo analyze = new Analyzewithalgo();
	ArrayList<String[]> integrateresult = new ArrayList<String[]>();
	ArrayList<String[]> aspectresult = new ArrayList<String[]>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Swing sw = new Swing();
		sw.mFrame.setVisible(true);
	}

	

	public Swing() {
		Initialize();

	}

	@SuppressWarnings("unchecked")
	private void Initialize() {
		a = new Action();

		mFrame = new JFrame();
		mFrame.setResizable(false);
		mFrame.setBounds(100, 100, 669, 513);
		mFrame.setTitle("LTMSA");

		JTabbedPane tabbedPane = new JTabbedPane();
		mFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel Panel1 = new JPanel();
		JPanel Panel2 = new JPanel();
		JPanel Panel3 = new JPanel();
		Panel1.setLayout(null);
		ArrayList deleteList = new ArrayList();

		/*----------------------------------词库管理模块构建----------------------------------------*/

		/*--------------------文件导入模块---------------------------*/
		// tab1:词库管理
		tabbedPane.addTab("词库管理", Panel1);

		// 格式
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setText("\u683C\u5F0F");
		textArea.setBounds(10, 25, 44, 24);
		Panel1.add(textArea);
		textArea.setOpaque(false);

		// 类型
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("\u7C7B\u578B");
		textArea_1.setBounds(10, 63, 44, 24);
		Panel1.add(textArea_1);
		textArea_1.setOpaque(false);

		// 文件格式 Cb
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(64, 26, 125, 21);
		Panel1.add(comboBox);
		comboBox.addItem(".csv");
		comboBox.addItem(".json");

		// 类型 Cb
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(64, 64, 125, 21);
		Panel1.add(comboBox_1);
		comboBox_1.addItem("情感词库");
		comboBox_1.addItem("规则库");
		comboBox_1.addItem("语料库");

		// 导入
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setText("\u5BFC\u5165\uFF1A");
		textArea_2.setBounds(10, 116, 44, 24);
		Panel1.add(textArea_2);
		textArea_2.setOpaque(false);

		// 文件名
		Label label = new Label("\u6587\u4EF6\u540D");
		label.setBounds(110, 146, 91, 23);
		Panel1.add(label);

		// 打开文件 Btn
		Button button = new Button("\u6253\u5F00\u6587\u4EF6");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				comboBox.setEnabled(false);
				comboBox_1.setEnabled(false);

				a.OpenfileDialogue(comboBox.getSelectedItem().toString());
				if (a.fileName != null) {
					label.setText(a.fileName);
				}
			}

		});
		button.setBounds(10, 146, 76, 23);
		Panel1.add(button);

		// 显示打开内容的Jtable
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBounds(344, 10, 309, 366);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(344, 10, 309, 366);
		Panel1.add(scrollPane);

		// 导入库列表
		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String selected = list.getSelectedValue().toString();
				table.setModel(a.libMap.get(selected).model);
			}
		});
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(37, 299, 209, 116);
		Panel1.add(list);

		// 导入 Btn
		Button button_1 = new Button("\u5BFC\u5165");
		button_1.addActionListener(new ActionListener() {
			// 如果libMap中存在该库类型，则更改该库对象的AFileOperation，如果不存在，则增加这个对象，同时在库列表当中增加
			public void actionPerformed(ActionEvent arg0) {
				if (a != null) {
					if (a.libMap.containsKey(comboBox_1.getSelectedItem()
							.toString())) {
						Library library = a.libMap.get(comboBox_1
								.getSelectedItem().toString());
						if (JOptionPane.showConfirmDialog(null,
								"该库已经存在，确定覆盖原有库吗", "警告",
								JOptionPane.YES_NO_CANCEL_OPTION) == 0) {

							DefaultTableModel model;
							try {
								model = a.importData(a.fileAbPath, library);
								library.model = model;
								library.datavector = library.fileoperation.vectorData;
								table.setModel(model);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "读取文件失败",
										"错误", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						Library library = new Library();
						// 实际上更新library对象的AfileOperation
						DefaultTableModel model;
						try {
							model = a.importData(a.fileAbPath, library);
							library.model = model;
							library.datavector = library.fileoperation.vectorData;
							a.libMap.put(comboBox_1.getSelectedItem()
									.toString(), library);
							table.setModel(model);
							listModel.addElement(comboBox_1.getSelectedItem()
									.toString());
							comboBox.setEnabled(true);
							comboBox_1.setEnabled(true);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "读取文件失败", "错误",
									JOptionPane.ERROR_MESSAGE);
						}

					}

				}

			}
		});
		button_1.setBounds(50, 175, 76, 23);
		Panel1.add(button_1);

		// 取消Btn
		Button button_2 = new Button("\u53D6\u6D88");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox.setEnabled(true);
				comboBox_1.setEnabled(true);
				label.setText("文件名");
			}
		});
		button_2.setBounds(159, 175, 76, 23);
		Panel1.add(button_2);

		Label label_1 = new Label(
				"\u60A8\u5DF2\u7ECF\u5BFC\u5165\u7684\u5E93\u6709\uFF1A");
		label_1.setBounds(37, 252, 146, 23);
		Panel1.add(label_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 222, 334, 2);
		Panel1.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(333, 0, 13, 456);
		Panel1.add(separator_1);

		// 删除button
		Button button_4 = new Button("删除");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefaultTableModel) table.getModel()).removeRow(table
						.getSelectedRow());
			}
		});
		button_4.setBounds(556, 382, 76, 23);
		Panel1.add(button_4);

		// 添加button
		Button button_3 = new Button("添加");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				dtm.addRow(new Object[dtm.getColumnCount()]);
			}
		});
		button_3.setBounds(462, 382, 76, 23);
		Panel1.add(button_3);

		/*------------------------------------情感分析模块------------------------------*/
		Panel2.setLayout(null);
		tabbedPane.addTab("情感分析", Panel2);

		JLabel lblNewLabel = new JLabel("\u7B97\u6CD5\u9009\u53D6");
		lblNewLabel.setBounds(111, 23, 54, 15);
		Panel2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5206\u6790\u7B97\u6CD5");
		lblNewLabel_1.setBounds(21, 63, 54, 15);
		Panel2.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u7B97\u6CD5\u63CF\u8FF0");
		lblNewLabel_2.setBounds(21, 108, 54, 15);
		Panel2.add(lblNewLabel_2);

		TextArea textArea_3 = new TextArea();
		textArea_3.setBounds(81, 108, 197, 209);
		Panel2.add(textArea_3);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectitem = comboBox_2.getSelectedItem().toString();
				textArea_3.setText(algoInfo.get(selectitem).getDescription());
			}
		});
		comboBox_2.setBounds(85, 60, 140, 21);
		Panel2.add(comboBox_2);

		Button button_23 = new Button("\u5F00\u59CB\u5206\u6790");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectitem = comboBox_2.getSelectedItem().toString();
				try {
					integrateresult = analyze.integrateAnalyze(
							algoInfo.get(selectitem).getFilepath(),
							a.libMap.get("情感词库").datavector,
							a.libMap.get("语料库").datavector,
							a.libMap.get("规则库").datavector);

					integrateresult=analyze.aspectAnalyze(algoInfo.get(selectitem).getFilepath(), a.libMap.get("语料库").datavector,
							a.libMap.get("情感词库").datavector);
				 
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_23.setBounds(243, 359, 76, 23);
		Panel2.add(button_23);

		Label label_2 = new Label("\u5BFC\u5165\u65B0\u7B97\u6CD5");
		label_2.setBounds(427, 23, 69, 23);
		Panel2.add(label_2);

		Label label_3 = new Label("\u7B97\u6CD5\u540D");
		label_3.setBounds(336, 55, 69, 23);
		Panel2.add(label_3);

		Label label_4 = new Label("\u7B97\u6CD5\u63CF\u8FF0");
		label_4.setBounds(336, 108, 69, 23);
		Panel2.add(label_4);

		textField_1 = new JTextField();
		textField_1.setBounds(411, 60, 157, 21);
		Panel2.add(textField_1);
		textField_1.setColumns(10);

		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBounds(456, 137, 4, 24);
		Panel2.add(textArea_4);

		TextArea textArea_5 = new TextArea();
		textArea_5.setBounds(411, 108, 197, 209);
		Panel2.add(textArea_5);

		Button button_24 = new Button("\u5BFC\u5165");
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 获取算发明和算法描述
				String algoName = textArea_4.getText();
				String algoDescription = textArea_5.getText();
				// 打开文件
				a.OpenfileDialogue(".json");
				// 封装算法对象
				Algorithm algo = new Algorithm();
				algo.setName(algoName);
				algo.setDescription(algoDescription);
				algo.setFilepath(a.fileAbPath);
				// 放入到算法集合中
				algoInfo.put(algoName, algo);
				// 写入json文件完成注册
				jsonFileOperation op = new jsonFileOperation();
				try {
					op.registerAlgo(algo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		button_24.setBounds(566, 359, 76, 23);
		Panel2.add(button_24);

		try {
			jsonFileOperation jsonop = new jsonFileOperation();
			algoInfo = jsonop.openAlgoJSON("algo.json");
			Algorithm[] algos = (Algorithm[]) algoInfo.values().toArray();
			for (int i = 0; i < algos.length; i++) {
				Algorithm algo = algos[i];
				comboBox_2.addItem(algo.getName());
				textArea_3.setText(algos[0].getDescription());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*----------------------结果展现模块---------------------------------*/

		Panel3.setLayout(null);
		tabbedPane.addTab("结果展现", Panel3);
		// Pane3.setOpaque(false);

		Label label_5 = new Label("\u9009\u62E9\u4E3B\u9898\uFF1A");
		label_5.setBounds(10, 28, 69, 23);
		Panel3.add(label_5);

		Label label_6 = new Label("\u9009\u62E9\u5C5E\u6027\uFF1A");
		label_6.setBounds(10, 79, 69, 23);
		Panel3.add(label_6);

		Vector v1 = new Vector();
		v1.add("abc");
		ComboBoxModel model1 = new DefaultComboBoxModel(v1);
		JComboBox comboBox_3 = new JComboBox(model1);
		comboBox_3.setBounds(85, 28, 148, 21);
		Panel3.add(comboBox_3);

		Vector v2 = new Vector(5);
		for (int i = 0; i < 5; i++) {
			v2.add("abc");
		}
		ComboBoxModel model2 = new DefaultComboBoxModel(v2);
		JComboBox comboBox_4 = new JComboBox(model2);
		comboBox_4.setBounds(85, 79, 148, 21);
		Panel3.add(comboBox_4);

		Button button_5 = new Button("\u5C55\u73B0");
		button_5.setBounds(189, 242, 76, 23);
		Panel3.add(button_5);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// piechart display
				PieChart piechart = new PieChart();
				JFreeChart chart = piechart.createPieChart();
				JPanel panel = new ChartPanel(chart);
				panel.setBounds(303, 55, 355, 206);
				Panel3.add(panel);
				// data display
				ArrayList<String[]> rs = null;
				Display dp = new Display();
				JTable table = new JTable();
				try {
					table = dp.Display(rs);
					table.setBounds(303, 277, 355, 165);
					Panel3.add(table);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Label label_7 = new Label("\u60C5\u611F\u5206\u6790\u7ED3\u679C");
		label_7.setBounds(315, 28, 94, 23);
		Panel3.add(label_7);

		Button button_6 = new Button("\u5BFC\u51FA");
		button_6.setBounds(499, 28, 76, 23);
		Panel3.add(button_6);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
				int re = chooser.showOpenDialog(mFrame);
				if (re == JFileChooser.APPROVE_OPTION) {
					String savePath = chooser.getSelectedFile().getPath();
					csvFileOperation op = new csvFileOperation();
					op.saveCSVFile(savePath);
				}
			}
		});
	}
}
