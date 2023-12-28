package chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController implements Initializable{

	@FXML private TextArea displayText;
	@FXML private TextField txtPort;
	@FXML private Button btnStartStop;
	
	// Client Thread를 관리할 스레드 풀
	ExecutorService serverPool;
	// 운영제제에서 요청한 포트로 프로세스를 할당받아 client socket 연결 관리를 할 class
	ServerSocket server;
	// 연결된 client의 닉네임을 key값으로 서버에서 발신할 정보를 value로 저장하는 map객체 
	// <Client ID, socket 출력 스트림>
	Hashtable<String, PrintWriter> clients;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStartStop.setOnAction(e->{
			String text = btnStartStop.getText();
			
			// _Start
			if(text.equals("_Start")) {
				startServer();
				btnStartStop.setText("S_top");
			}else {
				stopServer();
				btnStartStop.setText("_Start");
			}
		});

	}
	
	
	// 서버 실행 담당
	public void startServer() {
		serverPool = Executors.newFixedThreadPool(50);
		clients = new Hashtable<>();
		
		String port = txtPort.getText().trim();
		
		for(char c : port.toCharArray()) {
		
			if(c < 48 || c > 57) {
				displayText.appendText("사용할 수 없는 port번호 입니다. \n");
				return;
			}
		}
		
		int portNumber = Integer.parseInt(port);
		// 해당 포트 번호를 할당 받아서 client에 연결 요청을 수락할 수 있도록 serverSocket 생성 
		// 1023 < portNumber || 65535 > portNumber
		
		try {
			server = new ServerSocket(portNumber);
			
			
		} catch (IOException e) {
			displayText.appendText("이미 사용중인 포트입니다. \n");
			stopServer();
			return;
		}
		
		// Thread Pool에서 관리되는 Thread에서 필요한 작업을 전달
		Runnable run = new Runnable() {

			@Override
			public void run() {
				// client 연결 요청 수락 후 연결 정보가 저장된 Socket 생성
				printText("[ START SERVER ] \n");
				while(true) {
				
					try {
						// client 연결 대기
						printText("client 연결 대기중.... ");
						
						Socket client = server.accept();
						String address = client.getRemoteSocketAddress().toString();
						
						printText("[연결 수락 : "+address+"]");
						
						serverPool.submit(new ServerTask(client, ServerController.this));
					} catch (IOException e) {
						stopServer();
						break;
					}
				}				
			}			
		};
		serverPool.submit(run);

	}
	
	// 서버 종료 자원해제 담당
	public void stopServer() {
		
		if(server != null && !server.isClosed()) {
			try {
				server.close();
			} catch (IOException e) {}
		}
		
		if(serverPool != null && !serverPool.isShutdown()) {
			serverPool.shutdownNow();
		}
				
		printText("[서버 중지]\n");
	}
	
	
	// 작업 스레드에서 textArea에 출력하는 UI 작업을 처리
	public void printText(String text) {
		Platform.runLater(()->{
			displayText.appendText(text+"\n");
		});
		

	}
	
	
}
