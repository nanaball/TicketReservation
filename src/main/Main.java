package main;

import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import member.MemberVO;
import post.CastVO;
import reservation.TicketVO;

public class Main extends Application {

	/**
	 * 한명의 client당 연결된 소켓 정보는 유일한 자원
	 */
	public static Socket socket;
	/**
	 * Server와 입출력을 담당하는 스레드 class
	 */
	public static MainRouterThread thread;
	
	/**
	 * 로그인 된 사용자인지 여부 확인
	 */
	public static MemberVO loginMember; 
	
	// 테이블 뷰 에서 선택된 예약 뮤지컬 시간 정보
	public static CastVO castVO;
	
	// 예매할 좌석 정보
	public static TicketVO reservTicket;
	
	/**
	 * 메인에서 사욜할 알림창
	 */
	Alert alert;
	
	@Override
	public void start(Stage primaryStage) {
		showAlert("서버와 연결중입니다. 잠시만 기다려주세요."); 
		// 서버 소켓 연결
		Platform.runLater(()->{
			try {
			
				socket = new Socket("localhost", 5001);
				System.out.println(socket);
				alert.close();
				
				thread = new MainRouterThread();
				thread.setDaemon(true);
				thread.start();
				
				Parent root = FXMLLoader.load(getClass().getResource("/member/Login.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/post/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Login Page");
				primaryStage.show();
			}  catch (Exception e ) {
				e.printStackTrace();
				alert.close();
				showAlert("서버와 연결이 되지 않습니다. 다시 시도해 주세요.");
				primaryStage.close();
			}
		});
	}
	
	public void showAlert(String text) {
		alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
