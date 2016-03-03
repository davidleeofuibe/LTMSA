package cn.uibe.LB.myAlgorithm;

import java.util.Vector;

public class GetCorpusWithoutAnnotations {
	public String[] GetCorpus(Vector<Vector> Corpusdt) {

        String[] OriginalCorpus = new String[Corpusdt.size()];

        //获取分词前微博
        for (int i = 0; i < Corpusdt.size(); i++)
        {
            int j = 0;
            StringBuffer sb = new StringBuffer();
            while (j < Corpusdt.get(i).size())
            {
                     sb.append(getpurestring((String)Corpusdt.get(i).get(j)));
                    j++;
                
            }
            OriginalCorpus[i]=sb.toString();
        }
        return OriginalCorpus;
    }

    public static String getpurestring(String originalstringsection){
        if (originalstringsection == "")
        {
            return "";
        }
        else {
            int lastindex=originalstringsection.lastIndexOf('/');
            if(lastindex<0){
            return "";}
            else{
            String temp=originalstringsection.substring(0,originalstringsection.lastIndexOf('/'));
            return temp;}
        }
}
}

