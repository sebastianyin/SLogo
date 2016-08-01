// This entire file is part of my masterpiece.
// Mike Ma (ym67)
package parser;
import java.util.Stack;

import command.Command;
import command.CommandFactory;
import command.CommandList;

/**
 * Auxiliary class to assist parsing and manage the parsing stack
 * 
 * @author Mike Ma (ym67)
 *
 */
class ParseProcessor {

	private final CommandFactory myFactory;
	private Stack<Token> myTokenStack;
	private CommandList myCommandList;

	/**
	 * Construct a language processor with all available languages
	 * 
	 * @throws IOException
	 */
	ParseProcessor(CommandFactory factory){
		myFactory = factory;
		myTokenStack = new Stack<>();
		myCommandList = new CommandList();
	}

	public void parseCommand(String token) throws ParseFormatException {
		if (!myTokenStack.empty()){
			Token t = myTokenStack.peek();
			if(t.myName.equals("(")){
				t.myName += token;
				return;
			}else if(t.myName.equals("MakeUserInstruction")){
				t.addCommand(myFactory.getEmptyCommand(token));
				return;
			}
		}
		myTokenStack.push(new Token(token, myFactory.getNumArgs(token)));
	}

	public void openCluster(String cluster) {
		myTokenStack.push(new Token(cluster, Integer.MAX_VALUE));
	}

	public boolean popStack() throws ParseFormatException {
		boolean flag = false; //flag signaling clean source code history
		while (!myTokenStack.isEmpty() && myTokenStack.peek().satisfied()) {
			Token token = myTokenStack.pop();
			// System.out.println(token.myName);
			Command c = myFactory.getCommand(token.myName, token.myCommands);
			if (myTokenStack.isEmpty()) {
				myCommandList.add(c);
			} else {
				Token t = myTokenStack.peek();
				t.addCommand(c);
				
				//critical for recursion: reserve namespace for user defined instruction
				if(t.myName.equals("MakeUserInstruction") && t.myCommands.size()==2)
					myFactory.reserveNameSpace(t.myCommands.get(0).name(), ((CommandList)t.myCommands.get(1)).size());	
			}
			if (token.myName.equals("MakeUserInstruction")){
				flag = true;
			}	
		}
		return flag;
	}

	public void closeList() throws ParseFormatException {
		if (myTokenStack.isEmpty())
			throw new ParseFormatException("List mismatch");
		Token t = myTokenStack.peek();
		if (!t.myName.equals("List"))
			throw new ParseFormatException(t.myName + " missing arguments");
		t.myNumArgs = t.myCommands.size();
	}

	public void closeGroup() throws ParseFormatException {
		if (myTokenStack.isEmpty())
			throw new ParseFormatException("Group mismatch");
		Token t = myTokenStack.peek();
		if (!t.myName.startsWith("("))
			throw new ParseFormatException("Group mismatch");
		t.myName = t.myName.substring(1);
		t.myNumArgs = t.myCommands.size();
		if (t.myNumArgs < myFactory.getNumArgs(t.myName))
			throw new ParseFormatException("Insufficient args for \"" + t.myName + "\"");
	}

	public void parseVariable(String token) throws ParseFormatException {
		Command c = myFactory.getVarable(token);
		if (myTokenStack.isEmpty())
			myCommandList.add(c);
		else
			myTokenStack.peek().addCommand(c);
	}

	public void parseConstant(String token) throws ParseFormatException {
		try {
			double d = Double.parseDouble(token);
			Command c = myFactory.getConstant(d);
			if (myTokenStack.isEmpty())
				throw new ParseFormatException("Stand-alone constant: " + d);
			myTokenStack.peek().addCommand(c);
		} catch (Exception e) { // There may be more exceptions than
								// ParseFormatException
			throw new ParseFormatException(e.getMessage());
		}
	}

	public CommandList getAllCommands() throws ParseFormatException {
		// Check if there're any remaining commands in the stack, and throw
		// errors accordingly
		if (!myTokenStack.isEmpty()) {
			if (myTokenStack.peek().myName.matches("List|\\(.*"))
				throw new ParseFormatException("List/Group did not close properly");
			else
				throw new ParseFormatException("Insufficient arguments for \"" + myTokenStack.peek().myName + "\"");
		}
		// Clear temporary name space for user defined commands
		// This is critical for recursion
		myFactory.clearTempNameSpace();
		return myCommandList;

	}
}
