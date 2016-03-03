package cn.uibe.LB.myAlgorithm;

import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFilter {

	public LinkedHashMap<Location, Integer> FilterResult(
			LinkedHashMap<Location, Integer> locationMap, Vector Corpus,
			Vector Rule) {

		// 对于每条规则
		for (int k = 0; k < Rule.size(); k++) {
			// 取出含有情感词的句子
			String regex = ((Vector) Rule.get(k)).get(1).toString();
			for (Location lct : locationMap.keySet()) {
				// 获取位置
				int row = lct.getRow();
				int column = lct.getColumn();
				// 清洗标签
				Vector vector = (Vector) Corpus.get(row);
				String sentence = TagClean(vector);
				// 获取该位置对应的情感词
				String rowsentiString = vector.get(column).toString();
				String sentiString=rowsentiString.substring(0,rowsentiString.lastIndexOf('/'));
				// 替代正则表达式中的（sentistring）
				regex.replace("(sentistring)", sentiString);
				// 正则表达式匹配
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(sentence);
				// 匹配则反转极性
				if (matcher.matches()) {
					locationMap.replace(lct, locationMap.get(lct) * (-1));
				}
			}
		}

		return locationMap;
	}

	public static String TagClean(Vector TagSten) {
		String sentence = new String();
		for (int j = 0; j < TagSten.size(); j++) {
			String cell = TagSten.get(j).toString();
			sentence += cell.substring(0, cell.lastIndexOf('/'));
		}
		return sentence;
	}
}
