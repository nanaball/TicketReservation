package chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTask implements Runnable{

	// 현재 task에 연결된 client socket 정보
	Socket client;
	// display UI 정보가 있는 controller instance 정보 저장
	ServerController sc;
	
	// 연결된 Client에 출력할 스트림
	PrintWriter printer;
	// client에서 출력된 데이터를 입력받을 스트림
	BufferedReader reader;
	// 연결된 client의 nickName정보
	String nickName;
	// 연결된 client task end flag
	boolean isRun = true;
	
	public ServerTask(Socket client, ServerController sc) {
		this.client = client;
		this.sc = sc;
		
		try {
			OutputStream os = client.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter writer = new BufferedWriter(osw);
			printer = new PrintWriter(writer,true);	// auto flush = 자동 배출
			
			reader = new BufferedReader(
				new InputStreamReader(client.getInputStream())
			); 
		} catch (IOException e) {
			sc.printText("Client 연결 오류 : " + e.getMessage());
		}
		
	}

	// client 에서 출력된  발신 메세지를 수신 - receive
	@Override
	public void run() {
		sc.printText(client.getRemoteSocketAddress()+" recieve 시작");
		// receive Data 
		// code - 0 : 닉네임 전달
		// code - 1 : 전체 메세지 - 귓속말 
	}

	// 연결된 모든 client에게 메세지 전달
	// code - 0 : 접속자 목록 갱신
	// code - 1 : 메세지 출력 
	public void broadCast(int code, String message) {
		
	}
}
