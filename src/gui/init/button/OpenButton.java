package gui.init.button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

import gui.workspace.IRenewWorkspace;
import gui.workspace.SerializedWorkspace;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.Parser;
import turtlepath.Trail;
import javafx.stage.FileChooser.ExtensionFilter;

public class OpenButton extends Button implements ButtonInterface{
	public OpenButton(IRenewWorkspace renewCanvasInterface, Parser parser,Properties properties){
		this.setText(properties.getProperty("open"));
		this.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				SerializedWorkspace workspace = null;
				try
			      {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Load Workspace From File");
					
					 File selectedFile = fileChooser.showOpenDialog(new Stage());
					 if (selectedFile != null) {
						  FileInputStream fileIn = new FileInputStream(selectedFile);
					         ObjectInputStream in = new ObjectInputStream(fileIn);
					         workspace = (SerializedWorkspace) in.readObject();
					         in.close();
					         fileIn.close();
					         System.out.println("Successfully deserial");
					         System.out.println("Background color is " + workspace.getBackgroundColor());
					         renewCanvasInterface.setBackgroundColor(workspace.getBackgroundColor());

					         Trail.setPenColorString(workspace.getTrailColor());
					         System.out.println("PenTrailColor is " + workspace.getTrailColor());
					 }			
			      }catch(IOException i)
			      {
			         i.printStackTrace();
			         return;
			      }catch(ClassNotFoundException c)
			      {
			         System.out.println("class not found");
			         c.printStackTrace();
			         return;
			      }
			}
		});
	}
	@Override
	public void retrieveText() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Button setText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button setAction(EventHandler<?> event) {
		// TODO Auto-generated method stub
		return null;
	}

}
