// This entire file is part of my masterpiece.
// Mike Ma (ym67)
package parser;

import java.io.IOException;
import java.util.Map;

import util.LanguageLoader;

/**
 * Auxiliary class to assist language delocalization. Friendly class -> not
 * visible out of parser package
 * 
 * @author Mike Ma (ym67)
 *
 */
class LanguageProcessor {

	// can be shared across all language processors
	private static final LanguageLoader LOADER = new LanguageLoader();

	private Map<String, String> myLanguageRules;

	/**
	 * Construct a language processor with all available languages
	 * 
	 * @throws IOException
	 */
	public LanguageProcessor() throws IOException {
		myLanguageRules = LOADER.getAllLocalizedSyntax();
	}

	/**
	 * Construct a language processor with the given language
	 * 
	 * @throws ParseFormatException 
	 */
	public LanguageProcessor(String language) throws ParseFormatException {
		try{
		myLanguageRules = LOADER.getLocalizedSyntax(language);
		}catch(IOException e){
			throw new ParseFormatException(language + " is undefined");
		}
	}

	/**
	 * Check if token matches existing language rule and delocalized the token
	 * if match found
	 * 
	 * @param token
	 * @return delocalized token
	 */
	public String delocalize(String token) {
		for (String s : myLanguageRules.keySet()) {
			if (token.matches(s)) {
				return myLanguageRules.get(s);
			}
		}
		return token;
	}
}
