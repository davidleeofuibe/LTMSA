package cn.uibe.LB.myAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class AspectCount {

	public ArrayList<String[]> AspectPolarity(Vector aspectVector,
			Vector sentiWords) {

		// 声明泛型hashmap，指定键类型为String，值类型为int[]
		ArrayList<String[]> aspectAnalyze = new ArrayList<String[]>();
		// acquire target
		for (int i = 0; i < aspectVector.size(); i++) {
			String[] key = (String[]) aspectVector.get(i);
			String keyAspect = key[0];
			String sentiword = key[1];
			String number = key[2];
			String polarity = Polarity(sentiword, sentiWords);
			if (polarity != null) {
				String[] aspect = { keyAspect, polarity, number };
				aspectAnalyze.add(aspect);
			}

		}
		return aspectAnalyze;
	}

	private String Polarity(String sentiword, Vector sentiWords) {

		for (int i = 0; i < sentiWords.size(); i++) {
			Vector vector = (Vector) sentiWords.get(i);
			if (vector.get(1).equals(0)) {
				String polarity = "0";
				return polarity;
			}
			if (vector.get(1).equals(1)) {
				String polarity = "1";
				return polarity;
			}
			if (vector.get(1).equals(-1)) {
				String polarity = "-1";
				return polarity;
			}
		}
		return "0";

	}

}
