package clustering;

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Locale;
//TODO remoce nita header later on
public class SimpleTokenizer {

	BreakIterator _iter = null;
	SnowballStemmer sbs = new SnowballStemmer();

	String replace_character(String token) {
		StringBuilder s = new StringBuilder(token.length());

		CharacterIterator it = new StringCharacterIterator(token);
		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			switch (ch) {
			case '\'':
				s.append("");
				break;
			case '\"':
				s.append("");
				break;
			default:
				s.append(ch);
				break;
			}
		}

		token = s.toString();

		return token;
	}

	public SimpleTokenizer() {
		Locale currentLocale = new Locale("en", "US");
		_iter = BreakIterator.getWordInstance(currentLocale);
	}

	public SimpleTokenizer(Locale l) {
		_iter = BreakIterator.getWordInstance(l);
	}

	public ArrayList<String> extractTokens(String text) {

		ArrayList<String> tokens = new ArrayList<String>();
		StopWordChecker _swc = new StopWordChecker();

		_iter.setText(text);
		int start = _iter.first();
		int end = _iter.next();

		while (end != BreakIterator.DONE) {

			String word = text.substring(start, end);

			if (Constants.REMOVE_STOP_WORDS) {
				if ( !_swc.isStopWord(word) ) {
					
					word = replace_character(word);

					if (Constants.CONVERT_TOKENS_TO_LOWERCASE){
						word = word.toLowerCase();
							
					}
					if (Constants.ENABLE_STEMMING){
						word = sbs.stem(word);
						
					}

//					System.out.println(":" + word + ":"+_swc.isStopWord(word));
					if ( !_swc.isStopWord(word) ) {
						tokens.add(word);
	
					}
				}
			} 
			else {
				if (Character.isLetterOrDigit(word.charAt(0))) {
					word = replace_character(word);

					if (Constants.CONVERT_TOKENS_TO_LOWERCASE)
						word = word.toLowerCase();
					if (Constants.ENABLE_STEMMING)
						word = sbs.stem(word);
					if ( !_swc.isStopWord(word) ) {

						tokens.add(word);
	
					}
				}
			}
			start = end;
			end = _iter.next();
		}

		return tokens;
	}
}