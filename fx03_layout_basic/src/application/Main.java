package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		// Container - 다른 요소를 담는 그릇
		// vertical box - 수직으로 요소를 배치하는 container
		VBox root = new VBox();
		root.prefWidth(350);	// 폭 크기 설정
		root.prefHeight(150); 	// 높이 크기 설정
		
		// text 출력용 control
		Label label = new Label();
		label.setText("Hello JavaFX!!!");
		
		// Font - 글꼴 또는 크기를 설정하는 class
		label.setFont(new Font(50));
		
		
		// container에 포함할 자식 요소 목록을 저장하는 리스트 
		ObservableList<Node> list = root.getChildren();
		list.add(label);

		Button btn = new Button();	// Button control 생성 
		btn.setText("확인");			// 버튼 타이틀 지정
		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Platform.exit();	// ui 스레드 종료 (프로그램 종료)
				
			}
			
		});
		
		
		
		list.add(btn);				// 자식요소 리스트에 버튼 추가
		
		// VBox를 container로 지정하여 장면(Scene) 생성
		// Node > Parent > 요소 
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
