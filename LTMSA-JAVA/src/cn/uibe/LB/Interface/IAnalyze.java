package cn.uibe.LB.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public interface IAnalyze {
	public ArrayList<String[]> integrateAnalyze(Vector SentiLibrary,
			Vector Corpus,Vector Rule);
	public ArrayList<String[]> aspectAnalyze(Vector corpus, Vector sentiWords);
}
