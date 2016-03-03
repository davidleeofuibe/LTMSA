package cn.uibe.LB.myAlgorithm;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;

public class Parse {

	public static Vector<String[]> sentiParse(Vector Corpus)
			throws IOException, SQLException {

		String grammar = "edu/stanford/nlp/models/lexparser/chinesePCFG.ser.gz";
		String[] options = { "-MAX_ITEMS", "200000000" };
		LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
		TreebankLanguagePack tlp = (ChineseTreebankLanguagePack) lp.getOp()
				.langpack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		// Iterable<List<? extends HasWord>> sentences;

		Vector<String[]> aspectVector = new Vector<String[]>();

		for (int i = 0; i < Corpus.size(); i++) {
			Vector singleCorpus = (Vector) Corpus.get(i);
			ReconstructTokenizeResult reconstruct = new ReconstructTokenizeResult();
			String[] arrayofword = reconstruct
					.Reconstructsinglecorpus(singleCorpus);
			List<CoreLabel> rawWords = Sentence.toCoreLabelList(arrayofword);
			Tree parse = lp.parse(rawWords);
			GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
			Object[] tdlArray = tdl.toArray();
			for (int k = 0; k < tdlArray.length; k++) {
				if (tdlArray[k].toString().contains("nsubj")
						|| tdlArray[k].toString().contains("mmod")) {
					String evaluation = tdlArray[k].toString().substring(
							tdlArray[k].toString().indexOf('(') + 1,
							tdlArray[k].toString().indexOf('-') - 1
									- tdlArray[k].toString().indexOf('('));
					String target = tdlArray[k].toString().substring(
							tdlArray[k].toString().indexOf(',') + 1,
							tdlArray[k].toString().lastIndexOf('-') - 1
									- tdlArray[k].toString().indexOf(','));
					Integer number = i;
					String num = number.toString();
					String[] aspect = { target, evaluation, num };
					aspectVector.addElement(aspect);
				}
			}
			i++;
		}
		return aspectVector;
	}

	
	public String[] formChange(Vector<Vector> corpus) {
		String[] corString = new String[corpus.size()];
		for (int m = 0; m < corpus.size(); m++) {
			corString[m] = "";
		}
		for (int i = 0; i < corpus.size(); i++) {
			for (int j = 0; j < corpus.get(i).size(); j++) {
				corString[i] = corString[i] + corpus.get(i).get(j);
			}
		}
		return corString;
	}
}
