package clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class kmeans {

	static ArrayList<DocumentMetaData> getDocSameCatagory(ArrayList<DocumentMetaData> docs, int clusterID) {
		ArrayList<DocumentMetaData> temp = new ArrayList<DocumentMetaData>();
		for (DocumentMetaData doc : docs) {
			if (doc.getClusterNo() == clusterID) {
				temp.add(doc);
			}
		}
		return temp;

	}

	public static void main(String args[]) throws Exception {

		System.out.println("Getting Data:");

		// Get MetaData
		MetaDataBuilder MD = new MetaDataBuilder(Constants.DATA_DIRECTORY, Constants.NUMBER_OF_TOP_WORDS,
				Constants.REMOVE_STOP_WORDS);
		ArrayList<DocumentMetaData> docs = MD.getDocs();

		// System.out.println("Feature Vectors:");
		// for(DocumentMetaData doc : docs){
		// doc.printFeatureVector();
		// }
		// System.out.println("Features:");
		// for(DocumentMetaData doc : docs){
		// doc.printTerms();
		// }

		System.out.println("Running K-Means:");

		Random randomGenerator = new Random();
		List<DocumentMetaData> ClusterCenters = Arrays.asList(new DocumentMetaData[Constants.NUMBER_OF_CLUSTERS]);
		Map<Integer, Double> TempClusterCenters = new HashMap<Integer, Double>();

		// Select Random Cluster Centers
		for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {

			int randomIndex = randomGenerator.nextInt(docs.size());
			ClusterCenters.set(i, docs.get(randomIndex));
			ClusterCenters.get(i).setPredictedCatagory(ClusterCenters.get(i).getCatagory());
			docs.get(randomIndex).setClusterNo(i);
			TempClusterCenters.put(i, (double) -1);

			// System.out.println("Cluster Catagory:" +
			// ClusterCenters.get(i).getCatagory() + ":" + "DocID:"
			// + ClusterCenters.get(i).DocID);
			
//			System.out.println(differenceFromOtherClusters(ClusterCenters.get(i),ClusterCenters));
		}

		int changeInCluster = 0;
		int iternation = 0;
		double efficency = 0;
		double changeInEfficency = 1;
		double newEfficency = 0;

		while (!(iternation < 2000) | !(changeInEfficency < 0.000001)) {
			System.out.println("Current Run:" + iternation);
			System.out.println("Current Efficency:" + efficency);
			System.out.println("Change in clusters:" + changeInCluster);
			System.out.println("Change in Efficency:" + changeInEfficency);

			iternation++;
			changeInCluster = 0;
			newEfficency = Evaluate_accuracy(docs);
			changeInEfficency = Math.abs(efficency - newEfficency);
			efficency = newEfficency;

			// Assign Each Document To The Nearest Cluster Center
			double newSimilarityValue = 0;
			double similarityValue = 0;

			for (DocumentMetaData doc : docs) {

				similarityValue = 0;
				for (DocumentMetaData ClusterCenter : ClusterCenters) {
					newSimilarityValue = Utilities.similarity(ClusterCenter.getFeatureVector(),
							doc.getFeatureVector());

					// System.out.println("CATAGORY:"+ClusterCenter.catagory+":"+"DOCcluster:"+doc.catagory);
					// System.out.println("New Value="+newSimilarityValue);
					// System.out.println("Old Value="+similarityValue);

					if (newSimilarityValue > similarityValue) {
						similarityValue = newSimilarityValue;
						doc.setClusterNo(ClusterCenter.getClusterNo());
						doc.setPredictedCatagory(ClusterCenter.getPredictedCatagory());

					}

				}
			}

			// Evaluate accuracy of documents in each class
			Evaluate_accuracy(docs);

			// find the new cluster centers
			double accumulativeCostValue = 0.0;

			for (int i = 0; i < docs.size(); i++) {
				accumulativeCostValue = 0.0;
				DocumentMetaData doc1 = docs.get(i);

				for (int j = i + 1; j < docs.size(); j++) {
					DocumentMetaData doc2 = docs.get(j);
					accumulativeCostValue += Utilities.similarity(doc1.getFeatureVector(),
							doc2.getFeatureVector());
				}

				if (accumulativeCostValue > TempClusterCenters.get(doc1.getClusterNo())) {
					TempClusterCenters.put(doc1.getClusterNo(), accumulativeCostValue);
					ClusterCenters.set(doc1.getClusterNo(), doc1);
					changeInCluster++;
				}
			}

		}

		// Evaluate accuracy of documents in each class
		Evaluate_accuracy(docs);

		for (DocumentMetaData ClusterCenter : ClusterCenters) {
			displayTop5Documents(ClusterCenter, docs);
		}
		
		System.out.println("End!");
		System.exit(0);

	}

		static double differenceFromOtherClusters(DocumentMetaData ClusterCenter, List<DocumentMetaData> clusterCenters) {
	
			double similarityWithOtherCluster=0;
		for(DocumentMetaData otherCluster:clusterCenters){
			if(ClusterCenter != otherCluster)
				similarityWithOtherCluster+=Utilities.similarity(ClusterCenter.featureVector, otherCluster.featureVector);
		}
		return ( (double)similarityWithOtherCluster/clusterCenters.size() );
		
	}
	static void displayTop5Documents(DocumentMetaData ClusterCenter, ArrayList<DocumentMetaData> docs) {

		double cosineSimilarityValue = 0;
		double[] topFileScore = new double[Constants.NUMBER_OF_TOP_DOCUMENTS_IN_CLUSTER];
		String[] topFileNames = new String[Constants.NUMBER_OF_TOP_DOCUMENTS_IN_CLUSTER];
		boolean filled = false;

		for (int i = 0; i < topFileNames.length; i++) {
			topFileNames[i] = "";
		}

		for (DocumentMetaData doc : docs) {
			if (doc.clusterID == ClusterCenter.clusterID) {

				cosineSimilarityValue = Utilities.similarity(ClusterCenter.getFeatureVector(),
						doc.getFeatureVector());

			}
			filled = false;
			for (int i = 0; i < topFileNames.length; i++) {
				if (topFileNames[i] != "") {
					topFileNames[i] = doc.DocID;
					topFileScore[i] = cosineSimilarityValue;
					filled = true;
				}
			}
			if (!filled) {
				for (int i = 0; i < topFileNames.length; i++) {
					if (topFileScore[i] < cosineSimilarityValue) {
						topFileNames[i] = doc.DocID;
						topFileScore[i] = cosineSimilarityValue;
						filled = true;
					}
				}

			}

		}

		System.out.println("Top 5 Documents in Cluster Center:" + ClusterCenter.DocID + " are:");
		for (int i = 0; i < topFileNames.length; i++) {
			System.out.println(topFileNames[i] + " ");
		}
		System.out.println("");
	}

	private static double Evaluate_accuracy(ArrayList<DocumentMetaData> docs) {
		int correctPrediction = 0;
		double efficency = 0;

		for (DocumentMetaData doc : docs) {
			if (doc.isPredictionCorrect()) {
				correctPrediction++;
			}
		}

		efficency = ((double) correctPrediction / docs.size());
		// System.out.println("Accuracy: " + efficency + "%");

		return efficency;

	}
}
