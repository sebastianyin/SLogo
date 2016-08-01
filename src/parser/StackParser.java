// This entire file is part of my masterpiece.
// Mike Ma (ym67)
package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;

import command.Command;
import command.CommandList;
import command.CommandFactory;
import action.Actions;
import action.TestActions;
import util.LanguageLoader;

/**
 * 
 * @author Mike Ma (ym67)
 *
 */
public class StackParser implements Parser {

	// these should only be init once
	private final FileLoader myFileLoader;
	private final CommandFactory myFactory;
	private final SyntaxProcessor mySyntaxProcessor;

	// Parsing related, should be init everytime parse() is called
	private LanguageProcessor myLanguageProcessor;
	private ParseProcessor myParseProcessor;
	private Tokenizer myTokenizer;
	private StringBuilder myHistory;

	public StackParser(Actions actions) throws ParseFormatException {
		try {
			myFactory = new CommandFactory(actions);
			myFileLoader = new FileLoader();
			mySyntaxProcessor = new SyntaxProcessor();
		} catch (Exception e) {
			throw new ParseFormatException(e.getMessage());
		}
	}

	@Override
	public CommandList parse(String input, String language) throws ParseFormatException {
		init(input, language);
		myParseProcessor = new ParseProcessor(myFactory);
		while (myTokenizer.hasNext()) {
			String token = myLanguageProcessor.delocalize(myTokenizer.next());
			System.out.println(token);
			myHistory.append(token + " ");
			switch (mySyntaxProcessor.get(token)) {
			case "Command":
				myParseProcessor.parseCommand(token);
				break;
			case "Constant":
				myParseProcessor.parseConstant(token);
				break;
			case "Variable":
				myParseProcessor.parseVariable(token);
				break;
			case "ListStart":
				myParseProcessor.openCluster("List");
				break;
			case "GroupStart":
				myParseProcessor.openCluster("(");
				break;
			case "ListEnd":
				myParseProcessor.closeList();
				break;
			case "GroupEnd":
				myParseProcessor.closeGroup();
				break;
			default:
				throw new ParseFormatException("What is \"" + token + "\"?");
			}
			// If the command popped is user-defined, record the source code (to facilitate saving)
			if(myParseProcessor.popStack()){
				myFileLoader.add(myHistory.toString());
				myHistory = new StringBuilder();
			}
		}
		myTokenizer.close();
		return myParseProcessor.getAllCommands();
	}

	private void init(String input, String language) throws ParseFormatException {
		myLanguageProcessor = new LanguageProcessor(language);
		myTokenizer = new SyntaxTokenizer(input);
		myHistory = new StringBuilder();
	}

	/**
	 * Save all currently made user instructions and global variables to file
	 * 
	 * @param file
	 * @throws IOException
	 */
	@Override
	public void save(File file) throws IOException {
		myFactory.outputVar().forEach((k, v) -> myFileLoader.add("MakeVariable " + k + " " + v + "\n"));
		try {
			myFileLoader.save(file);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Read and load user instructions and global variables from file
	 * 
	 * @param file
	 * @throws IOException
	 */
	@Override
	public void read(File file) throws IOException {
		try {
			parse(myFileLoader.read(file), "English").evaluate();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}

	}

	/**
	 * Get all currently defined user commands
	 * 
	 * @return the name of all currently defined user commands
	 */
	@Override
	public List<String> getUserCommand() {
		// This method does not exist, but I believe it is better to get
		// a list of user defined command from the factory rather than
		// store it in the parser
		//return myFactory.getAllUserCommands();
		return new ArrayList<>();
	}

	/**
	 * Get an editable map of global variables
	 * 
	 * @return a map of currently stored global variable and their values
	 */
	@Override
	public Map<String, Double> getVars() {
		return myFactory.outputVar();
	}

	/**
	 * For test purpose
	 */
	public static void main(String[] args) throws ParseFormatException, IOException {
		StackParser p = new StackParser(new TestActions(new ArrayList<>()));
		p.myFactory.setCaseSensitivite(false);
		Scanner s = new Scanner(new FileInputStream("testcontrol.in"));
		Command c = p.parse(s.useDelimiter("\\Z").next(), "English");
		c.evaluate();
		s.close();
		p.save(new File("tmp.txt"));
		p = new StackParser(new TestActions(new ArrayList<>()));
		p.read(new File("tmp.txt"));
	}

}
