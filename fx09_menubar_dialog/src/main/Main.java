package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Root.fxml"));
			
			// FXML에 정의된 Containder 인스턴스 반환
			Parent root = loader.load();
			Scene scene = new Scene(root);
			//FXML 파일과 함께 load된 Controller 인스턴스 반환
			RootController control = loader.getController();
			// Controller의 FXML 정보가 출력되는 무대 정보 저장
			control.setStage(primaryStage);
						
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
