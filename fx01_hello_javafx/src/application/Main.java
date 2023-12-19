package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane(); // 무대장면 구성, 요소처리 
			Scene scene = new Scene(root,400,400); // 무대장면 생성
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // 장면 목록, = list
			primaryStage.setScene(scene); // 무대장면 추가
			primaryStage.show(); // 무대 노출 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
