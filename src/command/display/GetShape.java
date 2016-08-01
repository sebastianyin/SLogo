package command.display;

import action.Actions;
import command.Command;
/**
 * 
 * @author Mike Ma (ym67)
 *
 */
class GetShape implements Command {
	
	private Actions myActions;

	public GetShape(Actions actions) {
		myActions = actions;
	}
	@Override
	public double evaluate(Command... args) {
		return myActions.getShape();
	}

}
