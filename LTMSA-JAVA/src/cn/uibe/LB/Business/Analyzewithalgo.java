package cn.uibe.LB.Business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import cn.uibe.LB.Interface.IAnalyze;

public class Analyzewithalgo {

	public ArrayList<String[]> integrateAnalyze(String filepath,
			Vector SentiLibrary, Vector Corpus,Vector Rule) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {

		 URL url1 = new URL("file://"+filepath);  
         URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()  
                 .getContextClassLoader());  
         Class<?> myClass1 = myClassLoader1.loadClass("cn.uibe.LB.myAlgorithm");  
         IAnalyze analyze = (IAnalyze) myClass1.newInstance();  
           return analyze.integrateAnalyze(SentiLibrary, Corpus, Rule);
	}
	
	public  ArrayList<String[]> aspectAnalyze(String filepath,Vector corpus,Vector SentiLibrary) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		 URL url1 = new URL("file://"+filepath);  
         URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()  
                 .getContextClassLoader());  
         Class<?> myClass1 = myClassLoader1.loadClass("cn.uibe.LB.myAlgorithm");  
         IAnalyze analyze = (IAnalyze) myClass1.newInstance();  
           return analyze.aspectAnalyze(corpus,SentiLibrary);
	}

	
	
}
