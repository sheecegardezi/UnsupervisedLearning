package clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentMetaData {
	String catagory;
	String DocID;
	String predictedCatagory;
	ArrayList<TermMetaData> terms;
	double[] featureVector;
	int clusterID;

	public DocumentMetaData(String DocID, String catagory) {
		this.DocID = DocID;
		this.catagory = catagory;
		this.terms = new ArrayList<TermMetaData>();
		this.featureVector = new double[Constants.NUMBER_OF_TOP_WORDS];
		this.predictedCatagory = "";
		this.clusterID = 0;
	}

	double[] getFeatureVector() {
		return this.featureVector;
	}

	void addTerm(TermMetaData term) {
		this.terms.add(term);
	}

	public void printTerms() {
		for (TermMetaData term : terms) {
			System.out.println(term);
		}
	}

	public void printFeatureVector() {
		System.out.print("Feature Vector: ");
		for (double no : featureVector) {
			System.out.print(no + " ");
		}
		System.out.println("");
	}

	public void setClusterNo(int id) {
		this.clusterID = id;
	}

	public int getClusterNo() {
		return this.clusterID;
	}

	public String getCatagory() {
		return this.catagory;
	}

	public String getPredictedCatagory() {
		return this.predictedCatagory;
	}
	
	public void setPredictedCatagory(String predictedCatagory) {
		this.predictedCatagory=predictedCatagory;		
	}

	public boolean isPredictionCorrect() {
//		System.out.println("Actual catagory:"+catagory+" : "+"Predicted catagory:"+predictedCatagory);
		return (catagory.equals(predictedCatagory));
	}

	public void constructFeatureVector(ArrayList<TermMetaData> topWordListTerms) throws IOException {

		int featureVectorIndex=-1;

//		int featureAdded=0;
//		ArrayList<Double> tempFeatureVector =new  ArrayList<Double>();

		
		
		for(TermMetaData termIntopWordList : topWordListTerms){
			featureVectorIndex=featureVectorIndex+1;
			for(TermMetaData termInDoc:terms){
				
//				System.out.println(termInDoc.term+" WC:"+termIntopWordList.term+" Result:"+termInDoc.term.equals(termIntopWordList.term));

				
				if(termInDoc.term.equals(termIntopWordList.term)){
//					featureAdded++;
					this.featureVector[featureVectorIndex]=termInDoc.get_tf_idf();
					
//					System.out.println(termInDoc.term+" WC:"+termIntopWordList.term+" Result:"+termInDoc.term.equals(termIntopWordList.term));
//					System.out.println("Feature Added:"+featureAdded +"INdex:"+featureVectorIndex+" Value:"+termInDoc.get_tf_idf()+" "+this.featureVector[featureVectorIndex]);
//					System.out.println(Arrays.toString(this.featureVector));
//					System.in.read();

				}
				

			}
		}
	
//
//		System.out.println("Feature Added:"+featureAdded);
//		System.out.println(Arrays.toString(this.featureVector));
//		System.in.read();
//		System.out.println("Next Vector::");
//		System.in.read();
//


	}

	
}
