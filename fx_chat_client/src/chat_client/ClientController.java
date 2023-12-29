package chat_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

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
		
		// font 정보 확인
		loadFont();
		
		
		
		Platform.runLater(()->{
			txtIp.requestFocus();
		});
		
		// txtIP에서 Enter 키가 눌려지면 PORT 입력창으로 focus
		txtIp.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) txtPort.requestFocus();
		});
		
		txtPort.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) txtNick.requestFocus();
		});
		
		txtNick.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) {
				btnConn.fire();
			}
		});
		
		txtInput.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) {
				btnSend.fire();
			}
		});
		
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
					txtDisplay.appendText("잘못된 포트번호입니다.-1");
					txtPort.clear();
					txtPort.requestFocus();
					return;
				}
			}
		
			this.port = Integer.parseInt(textPort);
			
			if(port <= 1023 || 65535 < port) {
				txtDisplay.appendText("잘못된 포트번호입니다.-2");
				txtPort.clear();
				txtPort.requestFocus();
				return;
			}	// port 번호 확인 완료
			
			// 닉네임 작성 필드의 이름 정보 
			String nick = txtNick.getText().trim();
			if(nick.equals("")) {
				displaytext("닉네임을 작성해 주세요");
				txtNick.requestFocus();
				return;
			}	
			this.nickName = nick;
			// 닉네임 확인 완료 
			
			
			try {
				// 객체 생성 시 서버에 연결 요청
				// 서버 연결 요청 수락 시 연결된 socket 정보를 반환
				server = new Socket(this.ip, this.port);
				txtDisplay.appendText("[연결 완료 : " + server.getRemoteSocketAddress()+"]");
				
				// server와 통신할 입출력 스트림 초기화 
				// - 출력 스트림 
				printer = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(server.getOutputStream()))
				,true); // auto flush(자동배출)
				
				// - 입력 스트림
				br = new BufferedReader(new InputStreamReader(server.getInputStream()));
				
				send(0,nickName);			
							
			} catch (IOException e1) {
				txtDisplay.appendText("[서버 연결안됨. IP와 Port를 확인해주세요.]");
				stopClient();
				return;
			}
			// 서버에서 발신된 데이터 수신 
			receive();
			
		});	// end btnConn
		
		// send button click시 message 발송
		btnSend.setOnAction(e->{
			String text = txtInput.getText().trim();
			if(text.equals("")) {
				displaytext("메세지를 먼저 작성해 주세요");
				txtInput.requestFocus();
				return;
			}
			// 서버로 메세지전달
			send(1, text);
		});
		
		
		
		
	}	// end initialize
	
	


	// 자원 해제 후 client 종료
		public void stopClient() {
			displaytext("[서버와 연결 종료]");
			
			if(server != null && !server.isClosed()) {
			
				try {
					server.close();
				} catch (IOException e) {}
			} // end if
		} // end stopClient
		
		
		// server에 메세지 출력
		// code - 0 : 닉네임 출력
		// code - 1 : 메세지 출력 
		public void send(int code, String msg) {
			printer.println(code+"|"+msg);	// 출력 완료
			// 0 | nickName
			// 1 | message
			txtInput.clear();
			txtInput.requestFocus();
			
		}
		
		// server에서 출력된 메세지 입력
		// code - 0 : 리스트 목록 갱신
		// code - 1 : 일반 메세지 
		public void receive() {

			Thread t = new Thread(()->{
			
				while(true) {
					try {
						// server에서 발신된 데이터를 한 라인씩 읽기
						String receiveData = br.readLine();
				
						if(receiveData == null) {
							break;
						}
						// 0|닉네임s,닉네임s,닉네임s,닉네임s,닉네임s,
						// 1|message
						String[] datas = receiveData.split("\\|");
						String code = datas[0];
						String text = datas[1];
						
						if(code.equals("0")) {
							// 접속자 목록 갱신
							String[] names = text.split(",");
							ObservableList<String>	nameList = FXCollections.observableArrayList(names);
							// UI thread를 통해서 UI변경
							Platform.runLater(()->{
								listView.setItems(nameList);
							});
						}else if(code.equals("1")) {
							// 일반 메세지 출력
							displaytext(text);
						}

						// displaytext(receiveData);
					} catch (IOException e) {
						// thread run while 종료
						break;
					}
				}// end while
				// thread 종료 전 socket 자원해제
				stopClient();
			});
			t.setDaemon(true);
			t.start();
		}
		
		// txtDisplay textArea에 UI thread를 이용하여 텍스트 작성 
		public void displaytext(String text) {
			Platform.runLater(()->{
				txtDisplay.appendText(text+"\n");
			});		
		}
		
		
		/**
		 * Font 사용 방법
		 */
		private void loadFont() {
			System.out.println(Font.getFamilies());
			System.out.println(Font.getFontNames());
			
			// Maplestory Light
			Font font = Font.loadFont(
				// 읽어올 폰트 경로	
				getClass().getResource("/fonts/MaplestoryLight.ttf").toString()
				,12 // 폰트 크기
			);
			System.out.println(font.getFamily());
			System.out.println(Font.getFamilies());
			System.out.println(Font.getFontNames());
			
			// Maplestory Light font 지정
			txtInput.setFont(font);
			
	
		}
		
		
		
		
}
