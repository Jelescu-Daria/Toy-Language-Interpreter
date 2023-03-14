package com.example.a7;
	
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage firstStage) {
		try {
			FXMLLoader programListLoader = new FXMLLoader();
			programListLoader.setLocation(getClass().getResource("ProgramList.fxml"));
			Parent programListRoot = programListLoader.load();
			ProgramListController programListController = programListLoader.getController();
			firstStage.setTitle("Toy language interpreter");
			firstStage.setScene(new Scene(programListRoot));
			firstStage.setX(0);
			firstStage.setY(50);
			firstStage.show();

			FXMLLoader programLoader = new FXMLLoader();
			programLoader.setLocation(getClass().getResource("Program.fxml"));
			Parent programRoot = programLoader.load();
			ProgramController programController = programLoader.getController();
			programListController.setProgramController(programController);
			Stage secondStage = new Stage();
			secondStage.setTitle("Execution of the selected program");
			secondStage.setScene(new Scene(programRoot));
			secondStage.setX(440);
			secondStage.setY(50);
			secondStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
