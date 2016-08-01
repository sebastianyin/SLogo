package gui.init.colorpicker;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;

public class PenColorPicker extends ColorPicker {
	private PenColorPicker penColorPicker = this;

	public PenColorPicker(ColorChangeInterface colorChangeInterface, Properties properties) {
		this.setTooltip(new Tooltip(properties.getProperty("pen_picker")));
		this.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent arg0) {
				colorChangeInterface.changeColor(penColorPicker.getValue());
			}
			
		});
	}
}