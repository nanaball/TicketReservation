package pay;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Receivable;

public class PayDoneController implements Initializable, Receivable  {

	@FXML private Label userID, price, date, musical, seat;
	@FXML private Button btnExit, btnCheck;
	
	// PayController Stage
	private Stage payStage;
	// payDoneController Stage
	private Stage payDoneStage;
	
	// 좌석 예매 창 스테이지
	private Stage reservStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.thread.payDoneController = this;
		
		
		// 결제 완료 창에서 예매 확인 버튼을 누르면 PayCheck 으로 넘어감
		btnCheck.setOnAction((e)->{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pay/PayCheck.fxml"));
			Parent root1;
			Stage stage;
			try {
				root1 = (Parent) fxmlLoader.load();
				stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL); // 팝업처럼 화면이 뜸
				stage.initStyle(StageStyle.UTILITY);		
				stage.setTitle("예매 확인");
				stage.setScene(new Scene(root1));
				PayCheckController controller = fxmlLoader.getController();
				controller.setStage(this.payStage, this.payDoneStage, this.reservStage);
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			};
		});
		
		
		// 결제 완료 창의 확인 버튼을 누르면 포스터 화면을 제외한 화면이 꺼짐
		btnExit.setOnAction((e)->{
			// PayController Stage
			payStage.close();
			// payDoneController Stage
			payDoneStage.close();
			// 좌석 예매 창 스테이지
			reservStage.close();
		});
	}

	// 방법을 모름
	@Override
	public void receiveData(String message) {
		
	}


	public void setStage(Stage payStage, Stage payDoneStage, Stage reservStage) {
		this.payStage = payStage;
		this.payDoneStage = payDoneStage;
		this.reservStage = reservStage;
	}
	

}
