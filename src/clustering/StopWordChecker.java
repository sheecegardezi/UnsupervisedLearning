package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class StopWordChecker {

	public HashSet<String> _ignoreWords;
	public final static String IGNORE_WORDS_ABS = Constants.STOP_WORD_LIST_FILE;

	public StopWordChecker() {
		_ignoreWords = new HashSet<String>();
		loadStopWords(IGNORE_WORDS_ABS);
	}

	public void loadStopWords(String src) {
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(src));
			while ((line = br.readLine()) != null) {
				_ignoreWords.add(line.trim().toLowerCase());
			}
			br.close();
		} catch (Exception e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

	}

	private boolean malformedWord(String s) {
		// System.out.println(s);
		if (!Character.isLetterOrDigit(s.charAt(0))) {
			return true;
		}
//		if(!s.matches("[a-zA-Z0-9]+")){
//			return true;
//		}
		if ((Character.isDigit(s.charAt(0))) && (s.length() <= 3)) {
			return true;
		}
		if (s.length() <= Constants.MIN_LENGTH_OF_WORD) {
			return true;
		}
		if (hasNumber(s)) {
			return true;
		}
		
		return false;
	}

	public boolean hasNumber(String s) {
		for (int j = 0; j < s.length(); j++) {
			if (Character.isDigit(s.charAt(j))) {
				return true;
			}
		}
		return false;
	}

	public boolean isStopWord(String s) {
		return malformedWord(s) || _ignoreWords.contains(s);
	}

	public boolean isStopWordExcludingNumbers(String s) {
		return _ignoreWords.contains(s);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StopWordChecker swc = new StopWordChecker();
		String word = "This is just of the is test sting of so this a.";

		if (Constants.REMOVE_STOP_WORDS) {
			if (!swc.isStopWord(word)) {
				System.out.println(word);
			}
		}

		System.out.println("Done!");
	}
}
