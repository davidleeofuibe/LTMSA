package cn.uibe.LB.Business;

import java.io.IOException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public abstract class AFileOperation {
	
	public Vector ColNameVector;
	public Vector vectorData;
	public void openFile(String filpath) throws IOException{};

}
