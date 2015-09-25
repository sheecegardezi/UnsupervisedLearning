package clustering;

import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

public class Utilities {

	public static final SimpleTokenizer ST = new SimpleTokenizer();

	public static void ConvertToFeatureMap(String sent, String fileName, Map<String, TermMetaData> termsMap,
			DocumentMetaData doc) {

		ArrayList<String> tokens = ST.extractTokens(sent);

		int DocumentLength = tokens.size();
		int termPositionIndex = 0;
		double normalizedPosition = 0.0;
		for (String token : tokens) {
			termPositionIndex = termPositionIndex + 1;

			token = token.trim().toLowerCase();

			if (!(token.length() == 0)) {

				if (termsMap.containsKey(token)) {

					termsMap.get(token).incTermFrequency();
					termsMap.get(token).incfrequencyInCurrentDocument();

					normalizedPosition = (double) DocumentLength / termPositionIndex;
					termsMap.get(token).addNormalizedTermFrequency(normalizedPosition);

					if (!termsMap.get(token).getDocs().contains(doc)) {

						termsMap.get(token).addDoc(doc);
						termsMap.get(token).incDocFrequency();
					}
				}

				else {
					TermMetaData metaData = new TermMetaData();
					metaData.setTerm(token);

					termsMap.put(token, metaData);

					termsMap.get(token).addDoc(doc);
					termsMap.get(token).incDocFrequency();
					termsMap.get(token).incTermFrequency();
					termsMap.get(token).incfrequencyInCurrentDocument();

				}
				doc.addTerm(termsMap.get(token));

			}

		}

		for (TermMetaData term : doc.terms) {
			term.addNormalizedTermFrequency(tokens.size());
		}

	}

	public static double similarity(double[] docVector1, double[] docVector2) {

		if (Constants.cosineSimilarity) {

			double dotProduct = 0.0;
			double magnitude1 = 0.0;
			double magnitude2 = 0.0;
			double cosineSimilarity = 0.0;

			// System.out.println(Arrays.toString(docVector1));
			// System.out.println(Arrays.toString(docVector2));

			for (int i = 0; i < docVector1.length; i++) // docVector1 and
														// docVector2 must be of
														// same length
			{
				dotProduct += docVector1[i] * docVector2[i]; // a.b
				magnitude1 += Math.pow(docVector1[i], 2); // (a^2)
				magnitude2 += Math.pow(docVector2[i], 2); // (b^2)
			}

			magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
			magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

			if (magnitude1 != 0.0 | magnitude2 != 0.0) {
				cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
			} else {
				return 0.0;
			}
			return cosineSimilarity;

		}

		else {
			double sum = 0;
			double distance = 0;

			for (int i = 0; i < 3; i++) {
				sum = sum + Math.pow((docVector1[i] - docVector2[i]), 2.0);
				distance = Math.sqrt(sum);
			}

			return 1 / distance;

		}
	}

}