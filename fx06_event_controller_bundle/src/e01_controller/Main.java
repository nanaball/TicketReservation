package e01_controller;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("START 호출");
		
		try {
			HBox root = (HBox)FXMLLoader.load(getClass().getResource("Root.fxml"));
			System.out.println("FXML 로드 완료!");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("START 종료");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
