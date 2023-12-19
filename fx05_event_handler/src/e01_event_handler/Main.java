package e01_event_handler;
	
import java.io.InputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = new HBox();
			root.setAlignment(Pos.BOTTOM_CENTER);
			root.setSpacing(10.0);
			root.setPadding(new Insets(15));
			
			Button btn1 = new Button("버튼1");	// 생성자의 매개변수로 text지정
			btn1.setId("btn1");					// 노드에 접근하기 위한 구별 값 ID 지정
			btn1.setPrefSize(200, 100);			// width, height size(width, height)
			
			ButtonActionEventHandler handler = new ButtonActionEventHandler();
			btn1.setOnAction(handler);
			
			
			Button btn2 = new Button("버튼2");
			EventHandler<ActionEvent> handler2 = new EventHandler<>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("버튼 2 click!@!@!@!@");
					primaryStage.close();		// 프로그램 종료
				}
			};
			btn2.setOnAction(handler2);
			
			Button btn3 = new Button("버튼3");
			
			btn3.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("버튼3 clickkkkkkk");
				}
			});
			
			btn3.setOnAction((e)->{
				System.out.println("버튼3 clickkkkkkk!!! lambda");
				Platform.exit();
			});
			
			
			// root container에 btn1 자식 요소 추가
			/*
			root.getChildren().add(btn1);
			root.getChildren().add(btn2);
			root.getChildren().add(btn3);
			*/
			ObservableList<Node> childs = root.getChildren();
			childs.addAll(btn1, btn2, btn3);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {	// 창닫기
					event.consume();
					System.out.println("기존 이벤트가 처리되기 전에 소비하여 삭제");	
				}
			});
			
			// window창 - icon 추가
			// 1. 입력 스트림
			ObservableList<Image> icons = primaryStage.getIcons();
			InputStream is = getClass().getResourceAsStream("dogr.png");
			Image image = new Image(is);
			icons.add(image);
			
			// 2. URL
			URL url = getClass().getResource("/e01_event_handler/dogr.png");
			System.out.println(url.getPath());	// src부터 찾음
			System.out.println(url.toString());	// 내컴퓨터 local 루트
			
			icons.add(new Image(url.toString()));
			
			primaryStage.setWidth(500);
			primaryStage.setHeight(300);
			primaryStage.setX(0);
			primaryStage.setY(0);
			primaryStage.setTitle("Event Handler Test");
			primaryStage.setAlwaysOnTop(true);
			primaryStage.setResizable(false);
			
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
