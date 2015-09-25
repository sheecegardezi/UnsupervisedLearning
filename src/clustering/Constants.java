package clustering;

public class Constants {
	
	public static final int NUMBER_OF_TOP_WORDS=1; //heapspace exceds 50000;
//	public static final String DATA_DIRECTORY="data/blog_data/";
//	public final static String DATA_DIRECTORY = "data/newsgroups/";
//	public final static String DATA_DIRECTORY = "data/two_newsgroups/";
//	public final static String DATA_DIRECTORY = "data/test_data/";
	public final static String DATA_DIRECTORY = "data/family/";


	public static final int NUMBER_OF_TOP_DOCUMENTS_IN_CLUSTER=3; 
	public static final String STOP_WORD_LIST_FILE="./src/clustering/stopwords.txt";
	public static final Boolean REMOVE_STOP_WORDS=true;
	public static final int MIN_LENGTH_OF_WORD=1;
	
	public static final Boolean ENABLE_STEMMING=false;   //accuracy reached 88
	public static final Boolean CONVERT_TOKENS_TO_LOWERCASE=false;
	
//	public final static String OUTPUT_ARFF_FILE = "src/ml/classifier/newsgroups_testing.arff"; 
	
	public static final int NUMBER_OF_CLUSTERS=4;
	
	public static final boolean cosineSimilarity =true; //false for eucliadian


}