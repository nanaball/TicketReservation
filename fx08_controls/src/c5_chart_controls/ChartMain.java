package c5_chart_controls;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Chart.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
