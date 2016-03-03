package cn.uibe.LB.myAlgorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;

public class ComputePolarity {

	public ArrayList<String[]> polarityComputation(Vector Corpus,
			LinkedHashMap<Location, Integer> locationMap) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		for (int i = 0; i < Corpus.size(); i++) {
			String[] result = new String[2];
			String sentence = RuleFilter.TagClean((Vector) Corpus.get(i));
			result[0] = sentence;
			result[1] = "0";
			results.add(result);
		}

		for (Location lct : locationMap.keySet()) {
			int row = lct.getRow();
			String[] result = results.get(row);
			result[1] += locationMap.get(lct);
			results.set(row, result);
		}

		return results;

	}
}
