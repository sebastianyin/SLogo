package gui.init.button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Properties;

import gui.workspace.IRenewWorkspace;
import gui.workspace.SerializedWorkspace;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import parser.Parser;
import turtlepath.Trail;

public class SaveButton extends Button implements ButtonInterface {

	public SaveButton(IRenewWorkspace renewCanvasInterface, Parser parser, Properties properties){
		this.setText(properties.getProperty("save"));
		this.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SerializedWorkspace serializedWorkspace = new SerializedWorkspace();
				serializedWorkspace.setBackgroundColor(renewCanvasInterface.getBackgroundColor());
				serializedWorkspace.setTrailColor(Trail.getPenColorString());
				System.out.println("Background Color is set as " + serializedWorkspace.getBackgroundColor());
				System.out.println("Pen Color is set as "+ serializedWorkspace.getTrailColor());
		
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save Workspace to File");
					fileChooser.getExtensionFilters().add(new ExtensionFilter("Serializable Output File",".ser"));
					 File selectedFile = fileChooser.showSaveDialog(new Stage());
					 
					 if (selectedFile != null) {
							try {
								FileOutputStream fileOut =
								         new FileOutputStream(selectedFile);
								         ObjectOutputStream out = new ObjectOutputStream(fileOut);
								         out.writeObject(serializedWorkspace);
								         out.close();
								         fileOut.close();
								         System.out.println("Serialized data is saved.");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
	
