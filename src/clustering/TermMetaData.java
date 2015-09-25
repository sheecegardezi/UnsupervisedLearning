package clustering;

import java.util.ArrayList;

public class TermMetaData{
	
	String term;
	int frequencyInCurrentDocument;
	int termFrequency;
	double normalizedTermFrequency;
	int DocFrequency;
	ArrayList<Float> normalizedPostions;
	ArrayList<DocumentMetaData> Docs;
	Double tf_idf;
	
	public TermMetaData(){
		this.term="";
		this.normalizedTermFrequency=0;
		this.normalizedPostions = new ArrayList<Float>();
		this.Docs=new ArrayList<DocumentMetaData>();
		this.termFrequency=0;
		this.DocFrequency=0;
		tf_idf=new Double(0);
		this.frequencyInCurrentDocument=0;
	}
	
	public void addNormalizedTermFrequency(double DocumentLength){
		
		if(DocumentLength>0){
			this.normalizedTermFrequency+=((double)frequencyInCurrentDocument/DocumentLength);
				
		}
		frequencyInCurrentDocument=0;
	}
	
	public int getfrequencyInCurrentDocument(){
		return this.frequencyInCurrentDocument;
	}
	public void incfrequencyInCurrentDocument() {
		this.frequencyInCurrentDocument++;
	}
	void calculate_tf_idf(double totalDocuments){
		float weightOfPostion=0;
		int totalPositionsOucored=normalizedPostions.size();
		
		if(totalPositionsOucored>0){

			float sumOfnormalizedPostions=0;
			for(float normalizedPostion:normalizedPostions){
				sumOfnormalizedPostions=sumOfnormalizedPostions+normalizedPostion;
			}
			weightOfPostion=(float)sumOfnormalizedPostions/totalPositionsOucored;
				
		}
		
		if(DocFrequency>0){
			  this.tf_idf=new Double((normalizedTermFrequency)*Math.log10((totalDocuments/DocFrequency)+weightOfPostion));
		}
	}
	
	public double get_tf_idf() {
		return this.tf_idf.doubleValue();
	}
	public int getTermFrequency() {
		return termFrequency;
	}
	public void setTermFrequency(int termFrequency) {
		this.termFrequency = termFrequency;
	}
	public void incTermFrequency() {
		this.termFrequency++;
	}
	
	public int getDocFrequency() {
		return DocFrequency;
	}
	public void setDocFrequency(int docFrequency) {
		this.DocFrequency = docFrequency;
	}
	public void incDocFrequency() {
		this.DocFrequency++;
	}
	public ArrayList<Float> getNormalizedPostions() {
		return normalizedPostions;
	}
	public void setNormalizedPostions(float normalizedPostions) {
		this.normalizedPostions.add( new Float(normalizedPostions));
	}
	public ArrayList<DocumentMetaData> getDocs() {
		return Docs;
	}
	
	public void addDoc(DocumentMetaData doc) {
		this.Docs.add(doc);
	}
	
	public String toString() {
		String output =  term +" "+termFrequency+" "+DocFrequency+" "+tf_idf+'\n';

		return output;    
	}
	
	public void setTerm(String token) {
		this.term=token;
		
	}

	public String getTerm() {
		return this.term;
	}


}