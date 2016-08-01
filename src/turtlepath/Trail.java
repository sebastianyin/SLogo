package turtlepath;

import java.io.Serializable;
import java.util.LinkedList;
import gui.init.colorpicker.ColorChangeInterface;
import gui.workspace.IRenewWorkspace;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Turtle;

/**
 * @author Susan Lang (sml59)
 */
public class Trail extends Canvas implements ColorChangeInterface, IRenewWorkspace, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9060832315824592272L;
	private GraphicsContext context = getGraphicsContext2D();
	private LinkedList<Moment> history = new LinkedList<Moment>();
	private static Color penColor = Color.BLACK;
	private static String penColorString = penColor.toString();
	private double myPrevX;
	private double myPrevY;

	/**
	 * @return the penColorString
	 */
	public static String getPenColorString() {
		penColorString = penColor.toString();
		return penColorString;
	}

	/**
	 * @param penColorString
	 *            the penColorString to set
	 */
	
	public static void setPenColorString(String penString) {
		penColor = Color.valueOf(penString);
		penColorString = penColor.toString();
	}

	private Turtle turtle;
	// This looks terrible:
	// private ChangeListener<? super Number> listener = new
	// ChangeListener<Number>() {
	// @Override
	// public void changed(ObservableValue<? extends Number> observable, Number
	// oldVal, Number newVal) {
	// System.out.println(" oldVal " + oldVal + " newVal " + newVal);
	// update();
	// }
	// };

	public Trail(Turtle t) {
		turtle = t;
		// turtle.move.addListener(listener); -> This looks terrible
		myPrevX = turtle.getX() + turtle.screenWidth.get() / 2;
		myPrevY = turtle.getY() + turtle.screenHeight.get() / 2;
		turtle.move.addListener((ob, oV, nV) -> draw()); // This looks fantastic
	}

	public void draw() {
		System.out.println(myPrevX+" "+myPrevY);
		double currX = turtle.getX() + turtle.screenWidth.get() / 2;
		double currY = turtle.getY() + turtle.screenHeight.get() / 2;
		if (turtle.isPenDown()) {
			context.strokeLine(myPrevX, myPrevY, currX, currY);
		}
		myPrevX = currX;
		myPrevY = currY;

		// context.clearRect(0, 0, this.getWidth(), this.getHeight());
		// for (int i = 1; i < history.size(); i++) {
		// Moment current = history.get(i);
		// Moment prev = history.get(i-1);
		// context.setStroke(current.getColor());
		// if (current.getIsPenDown()) {
		// context.strokeLine(prev.getX() + turtle.screenWidth.get()/2,
		// prev.getY() + turtle.screenHeight.get()/2, current.getX() +
		// turtle.screenWidth.get()/2, current.getY() +
		// turtle.screenHeight.get()/2);
		// }
		// }
	}

	
	@Override
	public void changeColor(Color color) {
		penColor = color;
		context.setStroke(color);
	}

	public void setLineWidth(double d) {
		context.setLineWidth(d);
	}

	@Override
	public void setBackgroundColor(String backgroundColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
