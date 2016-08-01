package command.math;

import command.Command;
import parser.ParseFormatException;
/**
 * 
 * @author Mike Ma (ym67)
 *
 */
class Quotient implements Command {

	@Override
	public double evaluate(Command... args) throws ParseFormatException {
		double q = args[0].evaluate();
		for (int i = 1; i < args.length; i++)
			q /= args[i].evaluate();
		return q;
	}

}
