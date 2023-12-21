package e01_controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class RootController implements Initializable{
	
	@FXML
	private Button btn1;
	
	@FXML 
	private Button btn2;
	
	@FXML 
	private Button btn3;
	
	
	public RootController() {
		System.out.println("RootController 생성");
		System.out.println(btn1);	
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {	// Bundle : 묶음
		System.out.println("Initialize 호출");
		System.out.println(btn1);
		System.out.println("FXML 위치 정보 : " + location);
		System.out.println(resources);
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("버튼 1 click");
				Button btn1 = (Button)event.getTarget();
				System.out.println(btn1.getId());
				System.out.println(btn1.getStyleClass());
			}
		});
		
		btn2.setOnAction(event->{
			System.out.println("버튼 2 클릭ㄱㄱㄱㄱ");
			Button btn2 = (Button)event.getTarget();
			System.out.println(btn2.getId());
		});
		
		System.out.println("Initialize 종료");	
	} // end initialize
	
	public void closeEvent() {
		System.out.println("closeEvent");
		Platform.exit();
	}
	
}
