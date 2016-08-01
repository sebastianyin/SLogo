package command.turtle;

import action.Actions;
import command.Command;
/**
 * 
 * @author Mike Ma (ym67)
 *
 */
class PenDown implements Command {

	private Actions myActions;
	
	public PenDown(Actions actions){
		myActions = actions;
	}
	@Override
	public double evaluate(Command... args) {
		return myActions.penDown();
	}

}
