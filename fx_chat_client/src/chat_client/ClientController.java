package chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController implements Initializable {
	
	@FXML private TextArea txtDisplay;
	@FXML private ListView<String> listView;
	@FXML private  Button btnConn, btnSend;
	@FXML private TextField txtIp, txtPort, txtNick, txtInput;
	
	// 연결된 Server의 정보를 저장할 Socket
	Socket server;
	// 연결 요청을 보낼 server ip 주소 
	InetAddress ip;
	// 연결 요청을 보낼 server port번호 
	int port;
	// 서버로 출력할 스트림
	PrintWriter printer;
	// 서버에서 데이터를 입력 받을 스트림
	BufferedReader br;
	// client가 사용중인 닉네임 (id)
	String nickName;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnConn.setOnAction(e->{
			String ip = txtIp.getText().trim();
			
			if(ip.equals("")) {
				txtDisplay.appendText("아이피 주소를 작성해주세요");
				txtIp.requestFocus();
				return;
			}
			try {
				// getByName에 전달된 문자열 형태가 ipv4 주소 값이나 domain 형태가 아니면 UnKnowHostException 발생
				this.ip = InetAddress.getByName(ip);
			} catch (UnknownHostException e1) {
				txtDisplay.appendText("사용할 수 없는 주소입니다.");
				txtIp.clear();
				txtIp.requestFocus();
				return;
			}
			
			// ip 확인 완료
			String textPort = txtPort.getText().trim();
			
			// txtPort TextField에 작성된 값이 없을 경우 
			if(textPort.equals("")) {
				txtDisplay.appendText("포트번호를 작성해주세요");
				txtPort.requestFocus();
				return;
			}
		
			for(char c : textPort.toCharArray()) {
				if(c < 48 || c > 57) {
					txtDisplay.appendText("잘못된 포트번호입니다.");
					txtPort.clear();
					txtPort.requestFocus();
					return;
				}
			}
		
			this.port = Integer.parseInt(textPort);
			
			if(port <= 1023 || 65535 < port) {
				txtDisplay.appendText("잘못된 포트번호입니다.");
				txtPort.clear();
				txtPort.requestFocus();
				return;
			}
			
			// port 번호 확인 완료
			
			try {
				// 객체 생성 시 서버에 연결 요청
				// 서버 연결 요청 수락 시 연결된 socket 정보를 반환
				server = new Socket(this.ip, this.port);
				txtDisplay.appendText("[연결 완료 : " + server.getRemoteSocketAddress()+"]");
			} catch (IOException e1) {
				txtDisplay.appendText("[서버 연결안됨. IP와 Port를 확인해주세요.]");
				e1.printStackTrace();
			}
			
		});	// end btnConn
		
	}	// end initialize
	
	// 자원 해제 후 client 종료
		public void stopClient() {}
		
		// server에 메세지 출력
		// code - 0 : 닉네임 출력
		// code - 1 : 메세지 출력 
		public void send(int code, String msg) {
			
		}
		
		// server에서 출력된 메세지 입력
		// code - 0 : 리스트 목록 갱신
		// code - 1 : 일반 메세지 
		public void receive() {
			
		}
		
		// txtDisplay textArea에 UI thread를 이용하여 텍스트 작성 
		public void displaytext(String text) {
			Platform.runLater(()->{
				txtDisplay.appendText(text+"\n");
			});
			
			
			
		}
		
		
		
		
		
}
