package main;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class CustomController {

	public void handleAlertDialog(ActionEvent e) {
		Button btn = (Button)e.getTarget();
		String text = btn.getText();
		System.out.println(text);
		
		switch(text) {
			case "알림1" : 
					// 제목과 닫기버튼만 있는 utility
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(text);
					alert.setHeaderText("알림창입니다. \n헤더 정보입니다.");
					alert.setContentText("이곳은 컨텐츠 정보입니다. \n내용을 확인하세요");
					alert.show();
				break;
			case "경고2" : 
					alert = new Alert(AlertType.WARNING);
					alert.setTitle(text);
					// alert.setHeaderText(null);
					alert.setHeaderText("경고창 헤더 정보입니다.");
					alert.setContentText(null);
					alert.show();
				 break;
			case "오류3" : 
					alert = new Alert(AlertType.ERROR);
					alert.setTitle(text);
					alert.setHeaderText(null);
					alert.setContentText("오류가 발생하였습니다. \n확인 후 다시 진행해주세요");
					alert.show();
				break;
			case "확인4" :
					alert = new Alert(AlertType.CONFIRMATION);
					// 확인 취소 두가지 버튼으로 결과를 전달 받는 알림창
					alert.setTitle("결제확인");
					alert.setHeaderText("구매하실 상품의 결제 정보입니다.");
					alert.setContentText("확인 시 5,000,000원 결제됩니다. \n결제하시겠습니까?");
					Optional<ButtonType> result = alert.showAndWait();
					
					// ButtonType
					if(result.get() == ButtonType.OK) {
						System.out.println("확인 결제 완료");
					}else if(result.get() == ButtonType.CANCEL) {
						// 취소 버튼 or 닫기 버튼 or alt + f4
						System.out.println("결제 취소");
					}
					System.out.println("확인4 버튼 선택");
				break;
			case "입력5" :
					// 간단한 text를 입력 받을 수 있는 창
					TextInputDialog dialog = new TextInputDialog("최기근");
					dialog.setTitle(text);
					dialog.setHeaderText(null);
					dialog.setContentText("이름을 입력해주세요");
					Optional<String> res = dialog.showAndWait();
					
					// 확인 버튼 선택시 제공된 정보가 존재 true, 취소면 false(CANCEL)
					if(res.isPresent()) {
						String name = res.get();
						System.out.println("name : " + name);
					}else {
						System.out.println("취소");
					}
				break;
			case "Custom6" :
					alert = new Alert(
							AlertType.NONE,
							"이곳은 컨텐츠 정보입니다. \n내용을 확인하세요",
							ButtonType.OK,
							ButtonType.CANCEL,
							ButtonType.NEXT,
							ButtonType.PREVIOUS
					);
					alert.setTitle(text);
					alert.setHeaderText(null);
					
					Optional<ButtonType> result1 = alert.showAndWait();
					
					if(result1.get() == ButtonType.OK) {
						System.out.println("확인");
					}else if(result1.get() == ButtonType.CANCEL) {
						System.out.println("취소");
					}else if(result1.get() == ButtonType.NEXT) {
						System.out.println("다음");
					}
					// ...............
				break;
			case "닫기" :
					Stage stage = (Stage)btn.getScene().getWindow();
					stage.close();
				break;
		}
	}
	
}
