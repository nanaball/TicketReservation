package e02_bundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BundelController implements Initializable {
	
	@FXML private Button btnAccept, btnReload, btnCancel;

	@Override
	public void initialize(URL location, ResourceBundle bundle) {
		System.out.println(location);
		System.out.println(bundle);
		
		// 1번 확인 버튼 실행
		btnAccept.setOnAction(e->{
			handleEvent(e);
		});

	}	// end initialize

	// 2-3번 재실행, 취소 버튼 실행 -> bundle.fxml에서 on action 추가 입력 
	public void handleEvent(ActionEvent e) {
		Button btn = (Button)e.getTarget();
		System.out.println(btn);
		System.out.println(btn.getText());
		System.out.println(e.getEventType());
	}
}
