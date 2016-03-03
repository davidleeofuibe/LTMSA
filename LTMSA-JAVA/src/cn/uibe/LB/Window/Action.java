package cn.uibe.LB.Window;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import cn.uibe.LB.Bean.Library;
import cn.uibe.LB.Business.AFileOperation;
import cn.uibe.LB.Business.csvFileOperation;
import cn.uibe.LB.Business.jsonFileOperation;

//界面的交互逻辑类
public class Action {

	String fileAbPath;
	String fileName;
	Map<String, Library> libMap;

	public Action() {
		libMap = new HashMap<String, Library>();
	}

	// 打开文件按钮对话框,通过打开文件窗口,获取到文件的路径和文件名称

	public void OpenfileDialogue(String fileType) {
		JFileChooser filechooser = new JFileChooser();

		if (fileType.equals(".json")) {

			filechooser.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					if (f.getName().toLowerCase().endsWith(".json"))
						return true;
					else
						return false;
				}

				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "*.json(json文件)";
				}

			});
		} else if (fileType.equals(".csv")) {

			filechooser.setFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					if (f.getName().toLowerCase().endsWith(".csv"))
						return true;
					else
						return false;
				}

				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "*.csv(csv文件)";
				}

			});
		}

		filechooser.showDialog(null, "打开文件");
		File selectedFile = filechooser.getSelectedFile();
		if (selectedFile != null) {
			fileAbPath = selectedFile.getAbsolutePath();
			fileName = selectedFile.getName();
		}
	}

	// 文件操作工厂类，获取到对文件的操作对象
	private AFileOperation FileOperationFactory(String filepath) {

		String fileType = filepath.substring(filepath.lastIndexOf('.'));
		AFileOperation fileoperation;
		switch (fileType) {
		case ".csv":
			fileoperation = new csvFileOperation();
			return fileoperation;
		case ".json":
			fileoperation = new jsonFileOperation();
			return fileoperation;
		}
		return null;
	}

	public DefaultTableModel importData(String filepath, Library library)
			throws IOException {
		AFileOperation fileoperation = FileOperationFactory(filepath);
		fileoperation.openFile(filepath);
		library.fileoperation = fileoperation;
		DefaultTableModel model = new DefaultTableModel(
				fileoperation.vectorData, fileoperation.ColNameVector);
		return model;
	}
	
	



}
