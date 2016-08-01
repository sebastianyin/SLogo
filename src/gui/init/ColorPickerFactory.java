package gui.init;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import gui.init.colorpicker.BackgroundColorPicker;
import gui.init.colorpicker.ColorChangeInterface;
import gui.init.colorpicker.PenColorPicker;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;

public class ColorPickerFactory extends Factory{
	private ColorChangeInterface colorChangeInterface;
	private Properties properties;
	private Class[] params;
	public ColorPickerFactory(ColorChangeInterface colorChange, Properties prop){
		colorChangeInterface = colorChange;
		properties = prop;
		params = new Class[2];
		params[0] = ColorChangeInterface.class;
		params[1] = Properties.class;
	}
	@Override
	public Control createObject(String id) {
			try {
				Class cls = Class.forName("gui.init.ColorPickerFactory");
				Method method = this.getClass().getDeclaredMethod(id,params);
				return (Control)  method.invoke(this, colorChangeInterface,properties);
			} catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException |  SecurityException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return null;
	}
	
	@SuppressWarnings("unused")
	private ColorPicker backgroundColorPicker(ColorChangeInterface colorChangeInterface, Properties properties){
		return new BackgroundColorPicker(colorChangeInterface, properties);
	}
	@SuppressWarnings("unused")
	private ColorPicker penColorPicker(ColorChangeInterface colorChangeInterface, Properties properties){
		return new PenColorPicker(colorChangeInterface, properties);
	}	
}