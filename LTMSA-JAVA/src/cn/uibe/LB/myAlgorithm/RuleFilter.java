package cn.uibe.LB.myAlgorithm;

import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleFilter {

	public LinkedHashMap<Location, Integer> FilterResult(
			LinkedHashMap<Location, Integer> locationMap, Vector Corpus,
			Vector Rule) {

		// ����ÿ������
		for (int k = 0; k < Rule.size(); k++) {
			// ȡ��������дʵľ���
			String regex = ((Vector) Rule.get(k)).get(1).toString();
			for (Location lct : locationMap.keySet()) {
				// ��ȡλ��
				int row = lct.getRow();
				int column = lct.getColumn();
				// ��ϴ��ǩ
				Vector vector = (Vector) Corpus.get(row);
				String sentence = TagClean(vector);
				// ��ȡ��λ�ö�Ӧ����д�
				String rowsentiString = vector.get(column).toString();
				String sentiString=rowsentiString.substring(0,rowsentiString.lastIndexOf('/'));
				// ���������ʽ�еģ�sentistring��
				regex.replace("(sentistring)", sentiString);
				// ������ʽƥ��
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(sentence);
				// ƥ����ת����
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
