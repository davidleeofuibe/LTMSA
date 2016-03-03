package cn.uibe.LB.Business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.uibe.LB.Bean.Algorithm;

public class jsonFileOperation extends AFileOperation {
	JSONObject jb;

	@SuppressWarnings("unchecked")
	
	public void openFile(String filepath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filepath);
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tempString = null;
		String lastString = null;
		while ((tempString=reader.readLine()) != null) {
			lastString += tempString;
		}
		jb = JSONObject.fromObject(lastString);
		JSONArray ja = jb.getJSONArray("rules");
		ColNameVector=new Vector();
		ColNameVector.add("index");
		ColNameVector.add("regex");
		vectorData=new Vector();
		for (int i = 0; i < ja.size(); i++) {
			Vector vector=new Vector();
			vector.add (ja.getJSONObject(i).getString("index"));
			vector.add(ja.getJSONObject(i).getString("regex"));
			vectorData.add(vector);
		}
	}

	public HashMap<String,Algorithm> openAlgoJSON(String filepath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filepath);
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tempString=new String();
		String lastString = new String();
		while ((tempString=reader.readLine()) != null) {
			lastString += tempString;
		}
		jb = JSONObject.fromObject(lastString);
		JSONArray ja = jb.getJSONArray("algos");
		HashMap<String,Algorithm> algomap=new HashMap<String,Algorithm>();
		for(int i=0;i<jb.size();i++){
			Algorithm algo=new Algorithm();
			algo.setName(ja.getJSONObject(i).getString("name"));
			algo.setDescription(ja.getJSONObject(i).getString("description"));
			algo.setFilepath(ja.getJSONObject(i).getString("filepath"));
			algomap.put(algo.getName(), algo);
		}
		return algomap;
	}
	
	public void registerAlgo(Algorithm algo) throws IOException{
		FileInputStream fileInputStream = new FileInputStream("algo.json");
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream, "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tempString = null;
		String lastString = null;
		while ((tempString=reader.readLine()) != null) {
			lastString += tempString;
		}
		jb = JSONObject.fromObject(lastString);
		JSONArray ja = jb.getJSONArray("algos");
		JSONObject jb2=JSONObject.fromObject(algo);
		ja.add(jb2);
		BufferedWriter writer = null;
        File file = new File("algo.json");
		writer = new BufferedWriter(new FileWriter(file,false));
           writer.write("\"algos\":["+ja.toString()+"]");
	}
	
}
