package cn.uibe.LB.myAlgorithm;

import java.util.LinkedHashMap;
import java.util.Vector;

public class SentiLocation {

	public LinkedHashMap<Location, Integer> locateSentiword(Vector SentiLibrary, Vector Corpus){
		LinkedHashMap<Location,Integer> locationMap=new LinkedHashMap<Location, Integer>();
		
		for(int i=0;i<SentiLibrary.size();i++){
			for(int j=0;j<Corpus.size();j++){
				//得到情感词
				String sentiWord=((Vector)SentiLibrary.get(i)).get(0).toString();
				Vector vector=(Vector)Corpus.get(j);
				for(int k=0;k<vector.size();k++){
					if(vector.get(k).toString()==sentiWord){
						Location location=new Location();
						location.setRow(j);
						location.setColumn(k);
						Integer polarity=(Integer)(((Vector)SentiLibrary.get(i)).get(1));
						locationMap.put(location, polarity);
					}
				}
			}
		}
		
		return locationMap;
	}
}
