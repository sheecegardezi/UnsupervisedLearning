package clustering;

/** Simple Unigram analysis of data.
 * 
 * @author Syed Gardezi
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class MetaDataBuilder {

	Map<String, TermMetaData> terms;
	ArrayList<DocumentMetaData> docs;
	ArrayList<String> topWordList;
	ArrayList<TermMetaData> topWordListTerms;


	void calculateTopScoringterms() {

		SortedMap<String, Double> freqMap = new TreeMap<String, Double>();

		for (String key : terms.keySet()) {
			freqMap.put(terms.get(key).getTerm(), terms.get(key).get_tf_idf());
		}

		List<Map.Entry<String, Double>> entries = new ArrayList<Map.Entry<String, Double>>(freqMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
				return Double.compare(b.getValue(), a.getValue());
			}
		});
		int count = 0;
		for (Map.Entry<String, Double> e : entries) {
			// This loop prints entries. You can use the same loop
			// to get the keys from entries, and add it to your target list.
			if (count >= Constants.NUMBER_OF_TOP_WORDS) {
				break;
			}
			count++;
			topWordList.add(e.getKey());
			topWordListTerms.add(terms.get(e.getKey()));
			
//			System.out.println("Top word List:");
//			System.out.println(e.getKey()+" :"+e.getValue());
			
		}
	}

	public void printFeatures() {
		int counter = 0;
		for (String term : topWordList) {
			counter++;
			System.out.println(counter + ": " + term);
		}
	}
	
	void printDocTerms(){
		int counter=0;
		for(DocumentMetaData doc: docs){
			doc.printTerms();
			counter++;
			if(counter>2){
				break;
			}
		}
	}

	public MetaDataBuilder(String directory, int num_top_words, boolean ignore_stop_words) throws IOException {

		this.terms = new TreeMap<String, TermMetaData>();
		this.docs = new ArrayList<DocumentMetaData>();
		this.topWordList = new ArrayList<String>();
		this.topWordListTerms=new ArrayList<TermMetaData>();


		ArrayList<File> files = FileFinder.GetAllFiles(directory, "", true);

		for (File f : files) {

			String docID=f.getName();
			String catagory = f.getParentFile().getName();
			DocumentMetaData doc = new DocumentMetaData(docID,catagory);
			String file_content = DocUtils.ReadFile(f);

			Utilities.ConvertToFeatureMap(file_content, f.getName(), terms,doc);

			docs.add(doc);

			// if (++file_count % 500 == 0)
			// System.out.println("Read " + file_count + " files.");
		}

		int totalDocuments = files.size();

		//calculate tf_idf of each term
		for (Entry<String, TermMetaData> entry : terms.entrySet()) {
			terms.get(entry.getKey()).calculate_tf_idf(totalDocuments);
			
//			System.out.print("tf_idf of "+terms.get(entry.getKey()).getTerm()+" :");
//			System.out.println(terms.get(entry.getKey()).get_tf_idf());
		}

		//get the top scoring term based on tf_idf
		calculateTopScoringterms();
		
//		System.out.println("Top word List:");
//		for(String word : topWordList){
//		System.out.println(word);
//		}
		
		//construct the vector based on tf_idf
		for(DocumentMetaData doc:docs){
			
			doc.constructFeatureVector(topWordListTerms);
//			System.out.println(Arrays.toString(doc.featureVector));
			
//			System.out.println("Feature Vector of Doc"+doc.DocID+":");
//			for(double no:doc.featureVector){
//				System.out.print(" "+no+" ");
//			}
//			System.out.println("");
		}
	}

	public ArrayList<DocumentMetaData> getDocs() {
		return docs;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		MetaDataBuilder UB = new MetaDataBuilder(Constants.DATA_DIRECTORY, Constants.NUMBER_OF_TOP_WORDS,Constants.REMOVE_STOP_WORDS);
//		 UB.printFeatures();
//		UB.printDocTerms();
		System.out.println("Done!");
	}

	

}