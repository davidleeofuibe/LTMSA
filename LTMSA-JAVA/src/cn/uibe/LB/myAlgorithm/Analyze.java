package cn.uibe.LB.myAlgorithm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import cn.uibe.LB.Interface.IAnalyze;

public class Analyze implements IAnalyze{



	@Override
	public ArrayList<String[]> integrateAnalyze(Vector SentiLibrary, Vector Corpus,Vector rules) {
		SentiLocation sentilocation=new SentiLocation();
		RuleFilter rulefilter=new RuleFilter();
		ComputePolarity computepolarity=new ComputePolarity();
		LinkedHashMap<Location, Integer> result=sentilocation.locateSentiword(SentiLibrary, Corpus);
		LinkedHashMap<Location,Integer> result2=rulefilter.FilterResult(result, Corpus, rules);
		return computepolarity.polarityComputation(Corpus, result2);
	}

	@Override
	public ArrayList<String[]> aspectAnalyze(Vector corpus,
			Vector SentiLibrary) {
		Parse parse=new Parse();
		Vector<String[]> aspectVector;
		try {
			aspectVector = parse.sentiParse(corpus);
			AspectCount aspectcount=new AspectCount();
			return aspectcount.AspectPolarity(aspectVector, SentiLibrary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	
}
