package command.turtle;

import action.Actions;
import command.Command;
/**
 * 
 * @author Mike Ma (ym67)
 *
 */
class HideTurtle implements Command {

	private Actions myActions;
	
	public HideTurtle(Actions actions){
		myActions = actions;
	}
	@Override
	public double evaluate(Command... args) {
		return myActions.hideTurtle();
	}

}
