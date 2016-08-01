// This entire file is part of my masterpiece.
// Mike Ma (ym67)
package parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import util.PropertyLoader;

/**
 * Auxiliary class to assist language delocalization.
 * Friendly class -> not visible out of parser package
 * 
 * @author Mike Ma (ym67)
 *
 */
class SyntaxProcessor {

	// can be shared across all syntax processors
	private static final PropertyLoader LOADER = new PropertyLoader();

	private Map<String, String> mySyntaxRules;

	/**
	 * Construct a syntax processor through a property file
	 * 
	 * @throws IOException
	 */
	public SyntaxProcessor() throws IOException {
		mySyntaxRules = new HashMap<>();
		Properties prop = LOADER.load("Syntax");
		prop.forEach((k, v) -> {
			mySyntaxRules.put(k.toString(), v.toString());
		});
	}

	/**
	 * Get the type of the token
	 * 
	 * @param token
	 * @return type of syntax
	 */
	public String get(String token) {
		for (String s : mySyntaxRules.keySet()) {
			if (token.matches(mySyntaxRules.get(s))) {
				return s;
			}
		}
		return "";
	}
}
